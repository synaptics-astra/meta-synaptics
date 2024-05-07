# Copyright 2020, Beechwoods Software, Inc.
# Copyright 2021, Synaptics Incorporated

DESCRIPTION = "Linux Kernel for Synaptics STB SoCs"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

require recipes-kernel/linux/linux-yocto.inc

EXTRA_KERNEL_CONFIGME_DEPENDS = ""
do_kernel_configme[depends] += "${EXTRA_KERNEL_CONFIGME_DEPENDS}"

# No extensions to the linux kernel version
LINUX_VERSION_EXTENSION = ""

KERNEL_DEVICETREE = "${SYNA_KERNEL_DTS_FILE}"

COMPATIBLE_MACHINE = "syna"

DEPENDS:append = " lz4-native"
DEPENDS:append:syna = " \
    synasdk-tools-native \
    synasdk-security-native \
    synasdk-config-native \
    vim-native \
"

CMDLINE_EMMC_BOOT = "console=ttyS0,115200 rootfstype=ext4"
CMDLINE_EMMC_BOOT:append = "${@bb.utils.contains('KGDB_ENABLE', '1', ' kgdboc=ttyS0,115200', '', d)}"
CMDLINE_EMMC_BOOT:append = "${@bb.utils.contains('KGDB_WAIT', '1', ' kgdbwait', '', d)}"

# Set the verbosity of kernel messages during runtime
# You can define CMDLINE_DEBUG in your local.conf or distro.conf to override this behaviour
# Possible values: "quiet", "debug" or ""
# Defaults to "" because "debug" is very noisy with systemd
CMDLINE_DEBUG ?= ""
CMDLINE_EMMC_BOOT:append = " ${CMDLINE_DEBUG}"

CMDLINE:syna = "${CMDLINE_EMMC_BOOT}"

require recipes-devtools/synasdk/synasdk-config.inc

genx_secure_image() {
  v_image_type=$1; shift
  in_extras=$1; shift
  in_length=$1; shift
  f_input=$1; shift
  f_output=$1; shift

  ### Check input file ###
  [ -f $f_input ]

  ### Exectuable for generating secure image ###
  exec_cmd=gen_x_secure_image

  ### Prepare arguments ###
  unset exec_args

  # Other parameters
  exec_args="${exec_args} --chip-name=${syna_chip_name}"
  exec_args="${exec_args} --chip-rev=${syna_chip_rev}"
  exec_args="${exec_args} --img_type=$v_image_type"
  exec_args="${exec_args} --key_type=ree"

  #exec_args="${exec_args} --seg_id=0x00000000"
  #exec_args="${exec_args} --seg_id_mask=0xFFFFFFFF"
  #exec_args="${exec_args} --version=0x00000001"
  #exec_args="${exec_args} --version_mask=0xFFFFFFFF"
  exec_args="${exec_args} --length=$in_length"
  exec_args="${exec_args} --extras=$in_extras"

  exec_args="${exec_args} --workdir-security-tools=${security_tools_path}"
  exec_args="${exec_args} --workdir-security-keys=${security_keys_path}"
  # Input and output
  exec_args="${exec_args} --in_payload=${f_input} --out_store=${f_output}"

  ### Generate secure image ###
  eval ${exec_cmd} "${exec_args}"
}

do_deploy:append() {

    dtb_name=${SYNA_KERNEL_DTS_FILE}
    dtb_name=${dtb_name##*/}
    dtb_name=${dtb_name%%.*}
    echo "dtb_name = $dtb_name"

    . ${CONFIG_FILE}

    . ${CHIP_RC_FILE}

    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    # Compress the kernel with lz4
    cat ${D}/boot/${KERNEL_IMAGETYPE}-${PV} | lz4 -f -9 -B4 > ${D}/boot/${KERNEL_IMAGETYPE}-${PV}.lz4
    # Combine Kernel Image and Device Tree in a single image
    genimg -n boot -V 0 -v 0 -i LNXB -d ${D}/boot/${KERNEL_IMAGETYPE}-${PV}.lz4 -i LDTB -d ${D}/boot/${dtb_name}.dtb -o ${DEPLOYDIR}/lz4Image-dtb.${dtb_name}
    # Pack with mkbootimg
    mkbootimg --kernel ${DEPLOYDIR}/lz4Image-dtb.${dtb_name} --cmdline "${CMDLINE}" --base 0x05000000 --output ${DEPLOYDIR}/linux_bootimgs_raw.bin
    # Generate Secure Image
    if [ "is${CONFIG_GENX_ENABLE}" = "isy" ]; then
        ${security_tools_path}in_extras.py "LINUX_KERNEL" ${DEPLOYDIR}/in_linux_kernel.bin 0x00000001
        #call internal function
        genx_secure_image "LINUX_KERNEL" ${DEPLOYDIR}/in_linux_kernel.bin 0x0 ${DEPLOYDIR}/linux_bootimgs_raw.bin ${DEPLOYDIR}/linux_bootimgs_en.bin
    else
        gen_secure_image --code-type=5 --add-custk-store=0 --add-ersak-store=0 --chip-name=${syna_chip_name} --chip-rev=${syna_chip_rev} --market-id=`printf 0x%x ${syna_chip_mid}` --workdir-security-tools=${security_tools_path} --workdir-security-keys=${security_keys_path}  --input-file=${DEPLOYDIR}/linux_bootimgs_raw.bin --output-file=${DEPLOYDIR}/linux_bootimgs_en.bin
    fi
    # Generate subimg
    if [ "is${CONFIG_NO_PREPEND_IMG_INFO}" = "isy" ]; then
        cp ${DEPLOYDIR}/linux_bootimgs_en.bin ${DEPLOYDIR}/linux_bootimgs.subimg
    else
        prepend_image_info.sh ${DEPLOYDIR}/linux_bootimgs_en.bin ${DEPLOYDIR}/linux_bootimgs.subimg
    fi
    # Cleanup intermediate files
    rm -f ${DEPLOYDIR}/lz4Image-dtb.${dtb_name} ${DEPLOYDIR}/lz4Image-dtb.${dtb_name}.header ${DEPLOYDIR}/linux_bootimgs_raw.bin ${DEPLOYDIR}/linux_bootimgs_en.bin
}

# Kernel modules for Platypus and Dolphin
KERNEL_MODULE_AUTOLOAD:append = " \
    amp_bm \
    bcmdhd \
    pvrsrvkm \
    vpu \
"

KERNEL_MODULE_AUTOLOAD:append:dolphin = " \
    isp \
"

KERNEL_MODULE_PROBECONF:append = "amp_bm "
KERNEL_MODULE_PROBECONF:append = "pvrsrvkm "
KERNEL_MODULE_PROBECONF:append = "syna_drm "
KERNEL_MODULE_PROBECONF:append = "bcmdhd "
KERNEL_MODULE_PROBECONF:append = "syna-vpu "
KERNEL_MODULE_PROBECONF:append = "vpu "

module_conf_syna_drm = "softdep syna_drm pre: amp_bm fxl6408"
module_conf_pvrsrvkm = "softdep pvrsrvkm pre: syna_drm"

module_conf_syna-vpu = "softdep syna-vpu pre: amp_bm"
module_conf_vpu = "softdep vpu pre: amp_bm"

# module_conf_bcmdhd = "options bcmdhd firmware_path=bcm/fw_bcm43752.bin nvram_path=bcm/bcmdhd_43752.cal clm_path=bcm/bcmdhd_clm_43752.blob"
