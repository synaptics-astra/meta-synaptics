#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*             Copyright 2021 - 2022, Synaptics Incorporated.
#*
#*******************************************************************************/

inherit image_types synaimg_common

# This variable is available to request which values are suitable for IMAGE_FSTYPES
IMAGE_TYPES:append:syna = " \
    synaimg \
"

SYNAREALMACH:platypus = "sl1640"
SYNAREALMACH:myna2 = "sl1620"
SYNAREALMACH:sl1680 = "sl1680"

EXTRA_FW_DEPENDS = ""
EXTRA_FW_DEPENDS:dolphin = "synasdk-fw-enc:do_deploy"
EXTRA_FW_DEPENDS:platypus = "synasdk-fw-enc:do_deploy"
DEPENDS += "android-tools-native"


# the emmc_part_list that is required by some ROOTFS_POSTPROCESS_COMMAND functions is installed
# by the do_deploy step of the bootloader package
do_rootfs[depends] += " \
    virtual/bootloader:do_deploy \
"

do_image_synaimg[depends] += " \
    e2fsprogs-native:do_populate_sysroot \
    vim-native:do_populate_sysroot \
    bc-native:do_populate_sysroot \
    synasdk-tools-native:do_populate_sysroot \
    virtual/bootloader:do_deploy \
    synasdk-fastlogo:do_deploy \
    synasdk-preboot:do_deploy \
    syna-trusted-app:do_deploy \
    synasdk-security:do_deploy \
    synasdk-tzk:do_deploy \
    ${EXTRA_FW_DEPENDS} \
"

synaimg_mkext234fs () {
    rootfs_dir=$1
    image_name=$2
    fstype=$3
    rootfs_size=$4
    extra_imagecmd=""
    echo "rootfs_dir=$rootfs_dir, image_name=$image_name, fstype=$fstype, rootfs_size=$rootfs_size" >&2

    if [ $# -gt 4 ]; then
        shift;shift;shift;shift
        extra_imagecmd=$@
    fi
    echo "extra_imagecmd=$extra_imagecmd" >&2

    # If generating an empty image the size of the sparse block should be large
    # enough to allocate an ext4 filesystem using 4096 bytes per inode, this is
    # about 60K, so dd needs a minimum count of 60, with bs=1024 (bytes per IO)
    eval local COUNT=\"0\"
    eval local MIN_COUNT=\"60\"
    if [ $rootfs_size -lt $MIN_COUNT ]; then
        eval COUNT=\"$MIN_COUNT\"
    fi

    # Create a sparse image block
    dd if=/dev/zero of=${DEPLOY_DIR_IMAGE}/$image_name.subimg seek=$rootfs_size count=$COUNT bs=1024
    if [ "$rootfs_dir" = "none" ]; then
        mkfs.$fstype -F $extra_imagecmd ${DEPLOY_DIR_IMAGE}/$image_name.subimg
    else
        mkfs.$fstype -F $extra_imagecmd ${DEPLOY_DIR_IMAGE}/$image_name.subimg -d $rootfs_dir
    fi
}

def compute_rootfs_size(d, rootfs_dir):

    rootfs_alignment = eval(d.getVar('IMAGE_ROOTFS_ALIGNMENT'))
    overhead_factor = eval(d.getVar('IMAGE_OVERHEAD_FACTOR'))
    rootfs_req_size = eval(d.getVar('IMAGE_ROOTFS_SIZE'))
    rootfs_extra_space = eval(d.getVar('IMAGE_ROOTFS_EXTRA_SPACE'))

    root_path = os.path.join(d.getVar('WORKDIR'), rootfs_dir)

    from pathlib import Path

    # this counts the size of the files and directories in blocks used on disk (like
    # du -sk but without the root directory)
    size_kb = sum(x.lstat().st_blocks * 512 for x in Path(root_path).rglob('*')) / 1024

    base_size_kb = max(size_kb * overhead_factor, rootfs_req_size) + rootfs_extra_space

    from math import ceil

    base_size_kb = ceil(base_size_kb)

    extra_kb = base_size_kb % rootfs_alignment

    if extra_kb != 0:
        base_size_kb += rootfs_alignment - extra_kb

    return base_size_kb


IMAGE_CMD:synaimg () {

# Check that the needed files are available
    [ -f ${DEPLOY_DIR_IMAGE}/key.subimg ]
    [ -f ${DEPLOY_DIR_IMAGE}/preboot.subimg ]
    [ -f ${DEPLOY_DIR_IMAGE}/preload_ta.subimg ]
    [ -f ${DEPLOY_DIR_IMAGE}/bootloader_nopreload.subimg ]
    [ -f ${DEPLOY_DIR_IMAGE}/emmc_part_list ]
    [ -f ${DEPLOY_DIR_IMAGE}/emmc_image_list ]
    [ -f ${DEPLOY_DIR_IMAGE}/tee.subimg ]
    [ -f ${DEPLOY_DIR_IMAGE}/tee_recovery.subimg ]
    [ -f ${DEPLOY_DIR_IMAGE}/linux_bootimgs.subimg ]

# Check files required for firmware.subimg, and create it (GenX only!)
# From synasdk-fw-enc
    if [[ ${SYNAREALMACH} =~ sl16[2|4|8]0 ]]; then
        firmware_sub_args=""
        [ -f ${DEPLOY_DIR_IMAGE}/tsp.fw ] && firmware_sub_args="${firmware_sub_args} -i TSPF -d ${DEPLOY_DIR_IMAGE}/tsp.fw"
        [ -f ${DEPLOY_DIR_IMAGE}/dsp.fw ] && firmware_sub_args="${firmware_sub_args} -i DSPF -d ${DEPLOY_DIR_IMAGE}/dsp.fw"
        [ -f ${DEPLOY_DIR_IMAGE}/gpu.fw ] && firmware_sub_args="${firmware_sub_args} -i GPUF -d ${DEPLOY_DIR_IMAGE}/gpu.fw"
        [ -f ${DEPLOY_DIR_IMAGE}/sm_fw_en.bin ] && firmware_sub_args="${firmware_sub_args} -i SMFW -d ${DEPLOY_DIR_IMAGE}/sm_fw_en.bin"

        (cd ${WORKDIR} && genimg -n firmware ${firmware_sub_args} -o ${DEPLOY_DIR_IMAGE}/firmware_pack.bin )

        prepend_image_info.sh ${DEPLOY_DIR_IMAGE}/firmware_pack.bin ${DEPLOY_DIR_IMAGE}/firmware.subimg
        rm ${DEPLOY_DIR_IMAGE}/firmware_pack.bin*
    fi

# Append the preload_ta to the bootloader
    # Align bootloader.subimg to 512B
    bootloader_subimg_size=`stat -c %s ${DEPLOY_DIR_IMAGE}/bootloader_nopreload.subimg`
    bootloader_append_size=`expr 512 - ${bootloader_subimg_size} % 512`

    cp ${DEPLOY_DIR_IMAGE}/bootloader_nopreload.subimg ${DEPLOY_DIR_IMAGE}/bootloader.subimg

    if [ ${bootloader_append_size} -lt 512 ]; then
        dd if=/dev/zero of=${DEPLOY_DIR_IMAGE}/bootloader.subimg bs=1 seek=${bootloader_subimg_size} count=${bootloader_append_size} conv=notrunc
    fi
    cat ${DEPLOY_DIR_IMAGE}/preload_ta.subimg >> ${DEPLOY_DIR_IMAGE}/bootloader.subimg

# Split the rootfs between the ro part, and the rw part (/opt)
    if [ -d ${WORKDIR}/rootfs_ro ]; then
        rm -rf ${WORKDIR}/rootfs_ro
    fi
    rsync -a ${IMAGE_ROOTFS}/ ${WORKDIR}/rootfs_ro
# Add the /factory_setting mount point
    mkdir -p ${WORKDIR}/rootfs_ro/factory_setting

    if [ -d ${WORKDIR}/rootfs_rw_opt ]; then
        rm -rf ${WORKDIR}/rootfs_rw_opt
    fi

    enable_opt_part=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^opt" | cut -d , -f3`
    if [ ${enable_opt_part} ]; then
    # Create /opt/secure directories/files
        mkdir -p ${IMAGE_ROOTFS}/opt/secure/corefiles_back
        mkdir -p ${IMAGE_ROOTFS}/opt/secure/corefiles
        mkdir -p ${IMAGE_ROOTFS}/opt/secure/minidumps
        mkdir -p ${IMAGE_ROOTFS}/opt/secure/reboot
        mkdir -p ${IMAGE_ROOTFS}/opt/secure/RFC
        mkdir -p ${IMAGE_ROOTFS}/opt/secure/tr69agent-db
    # Add the /opt/tee/hdcp directory (for hdcp tee)
        mkdir -p ${IMAGE_ROOTFS}/opt/tee/hdcp
        if [ -d ${WORKDIR}/rootfs_rw_opt ]; then
            rm -rf ${WORKDIR}/rootfs_rw_opt
        fi
        rsync -a ${IMAGE_ROOTFS}/opt ${WORKDIR}/rootfs_rw_opt
        rm -rf ${WORKDIR}/rootfs_ro/opt/*
    fi

    if [ -d ${WORKDIR}/rootfs_rw_home ]; then
        rm -rf ${WORKDIR}/rootfs_rw_home
    fi

    enable_userdata_part=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^home" | cut -d , -f3`
        if [ ${enable_userdata_part} ]; then
        rsync -a ${IMAGE_ROOTFS}/home ${WORKDIR}/rootfs_rw_home

        if [ ! -d ${WORKDIR}/rootfs_rw_home/home ]; then
            mkdir -p ${WORKDIR}/rootfs_rw_home/home
        fi
        rm -rf ${WORKDIR}/rootfs_ro/home/*
    fi

# Create the SYNAIMG sub-directory
    if [ -d ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR} ]; then
        rm -rf ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}
    fi
    mkdir -p ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}

# Add a "tag"
    touch ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/TAG--${IMAGE_NAME}--TAG

# Generate the image for the ro part of rootfs
    MntPoint="/"
    RootfsSize=${@compute_rootfs_size(d, 'rootfs_ro/')}
    PartitionSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^rootfs\|^rootfs_a" | cut -d , -f3`
    PartitionSize=`echo "scale=0;${PartitionSizeM} * 1024" | bc`
    echo "ROOTFS Size = $RootfsSize, Partition size = $PartitionSize"
    if [ $RootfsSize -gt $PartitionSize ]; then
        PartitionSize=$RootfsSize
        RootfsSizeM=`echo "scale=0;(${RootfsSize} / 1024) + 1" | bc`
        awk -v size="$RootfsSizeM" 'BEGIN{FS=OFS=","} $1~/^rootfs/{$3=size"\t"} 1' ${DEPLOY_DIR_IMAGE}/emmc_part_list > ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list && mv ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list ${DEPLOY_DIR_IMAGE}/emmc_part_list
        DeltaSizeM=`echo "scale=0;${RootfsSizeM} - ${PartitionSizeM}" | bc`
        UserdataSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^userdata" | cut -d , -f3`
        UserdataSizeM=`echo "scale=0;${UserdataSizeM} - ${DeltaSizeM}" | bc`
        awk -v size="$UserdataSizeM" 'BEGIN{FS=OFS=","} $1~/^userdata/{$3=size"\t"} 1' ${DEPLOY_DIR_IMAGE}/emmc_part_list > ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list && mv ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list ${DEPLOY_DIR_IMAGE}/emmc_part_list
    fi
    synaimg_mkext234fs ${WORKDIR}/rootfs_ro${MntPoint} rootfs ext4 $PartitionSize ${EXTRA_IMAGECMD}

# Generate the image for the rw (/opt) part of rootfs
    if [ ${enable_opt_part} ]; then
        MntPoint="/opt"
        OptfsSize=${@compute_rootfs_size(d, 'rootfs_rw_opt/opt')}
        PartitionSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^opt" | cut -d , -f3`
        PartitionSize=`echo "scale=0;${PartitionSizeM} * 1024" | bc`
        echo "OPTFS Size = $OptfsSize, Partition size = $PartitionSize"
        if [ $OptfsSize -gt $PartitionSize ]; then
            PartitionSize=$OptfsSize
            OptfsSizeM=`echo "scale=0;(${OptfsSize} / 1024) + 1" | bc`
            awk -v size="$OptfsSizeM" 'BEGIN{FS=OFS=","} $1~/^opt/{$3=size"\t"} 1' ${DEPLOY_DIR_IMAGE}/emmc_part_list > ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list && mv ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list ${DEPLOY_DIR_IMAGE}/emmc_part_list
            DeltaSizeM=`echo "scale=0;${OptfsSizeM} - ${PartitionSizeM}" | bc`
            UserdataSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^userdata" | cut -d , -f3`
            UserdataSizeM=`echo "scale=0;${UserdataSizeM} - ${DeltaSizeM}" | bc`
            awk -v size="$UserdataSizeM" 'BEGIN{FS=OFS=","} $1~/^userdata/{$3=size"\t"} 1' ${DEPLOY_DIR_IMAGE}/emmc_part_list > ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list && mv ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list ${DEPLOY_DIR_IMAGE}/emmc_part_list
        fi
        synaimg_mkext234fs ${WORKDIR}/rootfs_rw_opt${MntPoint} opt ext4 $PartitionSize ${EXTRA_IMAGECMD}
    fi

# Generate the image for the rw (/home) part of rootfs
    if [ ${enable_userdata_part} ]; then
        MntPoint="/home"
        HomefsSize=${@compute_rootfs_size(d, 'rootfs_rw_home/home')}
        PartitionSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^home" | cut -d , -f3`
        PartitionSize=`echo "scale=0;${PartitionSizeM} * 1024" | bc`
        echo "HOMEFS Size = $HomefsSize, Partition size = $PartitionSize"
        if [ $HomefsSize -gt $PartitionSize ]; then
            PartitionSize=$HomefsSize
            HomefsSizeM=`echo "scale=0;(${HomefsSize} / 1024) + 1" | bc`
            awk -v size="$HomefsSizeM" 'BEGIN{FS=OFS=","} $1~/^home/{$3=size"\t"} 1' ${DEPLOY_DIR_IMAGE}/emmc_part_list > ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list && mv ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list ${DEPLOY_DIR_IMAGE}/emmc_part_list
            DeltaSizeM=`echo "scale=0;${HomefsSizeM} - ${PartitionSizeM}" | bc`
            UserdataSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^userdata" | cut -d , -f3`
            UserdataSizeM=`echo "scale=0;${UserdataSizeM} - ${DeltaSizeM}" | bc`
            awk -v size="$UserdataSizeM" 'BEGIN{FS=OFS=","} $1~/^userdata/{$3=size"\t"} 1' ${DEPLOY_DIR_IMAGE}/emmc_part_list > ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list && mv ${DEPLOY_DIR_IMAGE}/tmp_emmc_part_list ${DEPLOY_DIR_IMAGE}/emmc_part_list
        fi
        synaimg_mkext234fs ${WORKDIR}/rootfs_rw_home${MntPoint} home ext4 $PartitionSize ${EXTRA_IMAGECMD}
    fi

# Generate the image for the tsb (rw)
    enable_tsb_part=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^tsb" | cut -d , -f3`
    if [ ${enable_tsb_part} ]; then
        MntPoint="/media/tsb"
        PartitionSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^tsb" | cut -d , -f3`
        PartitionSize=`echo "scale=0;${PartitionSizeM} * 1024" | bc`
        echo "Partition size = $PartitionSize"
        synaimg_mkext234fs "none" tsb ext4 $PartitionSize ${EXTRA_IMAGECMD}
    fi

# Generate the image for the app (rw)
    enable_app_part=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^app" | cut -d , -f3`
    if [ ${enable_app_part} ]; then
        MntPoint="/media/app"
        PartitionSizeM=`cat ${DEPLOY_DIR_IMAGE}/emmc_part_list | grep -w "^app" | cut -d , -f3`
        PartitionSize=`echo "scale=0;${PartitionSizeM} * 1024" | bc`
        echo "Partition size = $PartitionSize"
        synaimg_mkext234fs "none" app ext4 $PartitionSize ${EXTRA_IMAGECMD}
    fi

# Generate the deployed image
    subimg_list=`cat ${DEPLOY_DIR_IMAGE}/emmc_image_list | grep 'subimg' | cut -d . -f1`
    subimg_list=`echo ${subimg_list} | sed -e 's:\([a-zA-Z0-9]\+\)\(_[a|b]\?\):\1:g' | xargs -n1 | sort -u | xargs`
    mapping_list="/bootloader/preboot /preboot/preboot \
                  /key_a/key /key_b/key \
                  /tzk_a/tee /tzk_b/tee \
                  /bl_a/bootloader /bl_b/bootloader \
                  /boot_a/linux_bootimgs /boot_b/linux_bootimgs \
                  /rootfs_a/rootfs /rootfs_b/rootfs\
                  /opt/opt /home/home /tsb/tsb /app/app \
                  /firmware_a/firmware /firmware_b/firmware \
                  /fastlogo/fastlogo /fastlogo_a/fastlogo /fastlogo_b/fastlogo"

# Add emmc_part_list and emmc_image_list to the deployed image
    cp ${DEPLOY_DIR_IMAGE}/emmc_image_list ${DEPLOY_DIR_IMAGE}/emmc_part_list ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}

    for i in ${subimg_list}; do
        subimg_name=`echo $mapping_list | grep -o "/${i}\(_[a|b]\)\?/[[:alnum:]_-]*" | head -1 | cut -d / -f3`
        if [ -n "${subimg_name}" ] && [ -f ${DEPLOY_DIR_IMAGE}/${subimg_name}.subimg ]; then
            cat ${DEPLOY_DIR_IMAGE}/$subimg_name.subimg | gzip -1 > ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/$i.subimg.gz

            rm -f ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg
            rm -f ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg.[0-9]*
            # Sparse subimg larger than 4G or gzip subimg larger than 300M
            if [  $(stat -c %s ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/${i}.subimg.gz) -gt 314572800 ] || \
               [  $(stat -c %s ${DEPLOY_DIR_IMAGE}/${subimg_name}.subimg) -ge 4294967296 ]; then
                img2simg ${DEPLOY_DIR_IMAGE}/${subimg_name}.subimg ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg 4096
                if [  $(stat -c %s ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg) -gt 314572800 ]; then
                    simg2simg ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg 314572800
                fi
            fi
            if [ -f ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg.0 ]; then
                # Copy split images ang update emmc_image_list
                split_num=$(ls ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg.[0-9]*|wc -l)
                j=0
                last_j=0
                while [ $j -lt $split_num ]
                do
                    cp ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg.$j ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/${i}_s.subimg.$j
                    if [ $j -eq 0 ]; then
                        sed -i 's/^'$i'.subimg.gz/'$i'_s.subimg.'$j'/' ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/emmc_image_list
                    else
                        #workaround to avoid last two lines obsoleted
                        echo -e "\n\n\n\n\n\n" >> ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/emmc_image_list
                        sed -i '/./{/^'$i'_s.subimg.'$last_j'/H};x; s/^'$i'_s.subimg.'$last_j'/'$i'_s.subimg.'$j'/' ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/emmc_image_list
                    fi
                    last_j=$j
                    j=$(expr $j + 1)
                done
                sed -i '/^$/d' ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/emmc_image_list
            elif [ -f ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg ]; then
                cp ${DEPLOY_DIR_IMAGE}/${subimg_name}_s.subimg ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/${i}_s.subimg
                sed -i 's/^'$i'.subimg.gz/'$i'_s.subimg/' ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/emmc_image_list
            fi
        elif [ "$i" = "fastlogo" -o "$i" = "fastlogo_a" -o "$i" = "fastlogo_b" ]; then
        # Add fastlogo to the deployed image
            [ -f ${DEPLOY_DIR_IMAGE}/fastlogo.subimg.gz ] && cp ${DEPLOY_DIR_IMAGE}/fastlogo.subimg.gz ${DEPLOY_DIR_IMAGE}/${SYNAIMG_DEPLOY_SUBDIR}/$i.subimg.gz
        else
            echo "emmc image is $i, mapping name is $subimg_name, ERROR!"
            exit 1
        fi
    done
}

fstab_mount_opt () {
    awk '/^#/ { c++}; /^opt/ {print "/dev/mmcblk0p"NR-c"\t/opt\tauto\tdefaults 0 2" }' \
        ${DEPLOY_DIR_IMAGE}/emmc_part_list \
        >> ${IMAGE_ROOTFS}/etc/fstab
}

fstab_mount_tsb () {
    awk '/^#/ { c++}; /^tsb/ {print "/dev/mmcblk0p"NR-c"\t/media/tsp\tauto\tdefaults 0 2" }' \
        ${DEPLOY_DIR_IMAGE}/emmc_part_list \
        >> ${IMAGE_ROOTFS}/etc/fstab
}

fstab_mount_app () {
    awk '/^#/ { c++}; /^app/ {print "/dev/mmcblk0p"NR-c"\t/media/app\tauto\tdefaults 0 2" }' \
        ${DEPLOY_DIR_IMAGE}/emmc_part_list \
        >> ${IMAGE_ROOTFS}/etc/fstab
}

ROOTFS_POSTPROCESS_COMMAND += "fstab_mount_opt; "
# Change these if you want default mkfs behavior (i.e. create minimal inode number)
EXTRA_IMAGECMD:synaimg ?= "-i 4096"
