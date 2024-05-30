DESCRIPTION = "Bootloader for Synaptics platform."
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"
SECTION = "bootloaders"

PR = "r2"

inherit deploy nopackages

PROVIDES = " \
    virtual/bootloader \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SECURITY_STACK_PROTECTOR = ""

DEPENDS = " \
    synasdk-security-native \
    synasdk-tools-native \
    vim-native \
    bc-native \
"
# FIXME: we should use the yocto toolchain instead
DEPENDS += "gcc-arm-arm-linux-gnueabihf-native"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"


SRC_URI = " \
    ${SYNA_SRC_BOOT} \
"
SRCREV_boot = "${SYNA_SRCREV_BOOT}"

SRCREV_FORMAT = "boot"


require recipes-devtools/synasdk/synasdk-build.inc

PV = "${ASTRA_VERSION}+git${SRCPV}"

do_compile () {
    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    clean=0 . build/module/bootloader/build_sm.sh
    clean=0 . build/module/bootloader/build.sh

    if [ $? -ne 0 ]; then
        echo 'bootloader build failed!'
        exit 1
    fi
}

do_deploy () {
    # sm_fw_en.bin (only for GenX, i.e PLATYPUS for now)
    if [ -f "${B}/target/bootloader/sm_fw_en.bin" ]; then
        install -m 0644 "${B}/target/bootloader/sm_fw_en.bin" ${DEPLOYDIR}
    fi

    # bootloader.subimg
    prepend_image_info.sh ${B}/target/bootloader/bootloader_en.bin ${DEPLOYDIR}/bootloader_nopreload.subimg

    # Generate Partition tables
    parse_pt_emmc 101 101 \
                  ${CONFIG_EMMC_BLOCK_SIZE} ${CONFIG_EMMC_TOTAL_SIZE} \
                  ${EMMC_PT_FILE} \
                  ${DEPLOYDIR}/linux_params_mtdparts \
                  ${DEPLOYDIR}/version_table \
                  ${DEPLOYDIR}/subimglayout \
                  ${DEPLOYDIR}/emmc_part_table \
                  ${DEPLOYDIR}/emmc_part_list \
                  ${DEPLOYDIR}/emmc_image_list

    # Update the CRC of the version table
    crc -a ${DEPLOYDIR}/version_table
    # Change the subimage files to .gz
    sed -i -e 's:\([a-zA-Z0-9]\+\)\(_[a|b]\)\?\.subimg,:\1.subimg.gz,:' ${DEPLOYDIR}/emmc_image_list
}

addtask deploy before do_package after do_install

do_install[noexec] = "1"
