DESCRIPTION = "Bootloader for Synaptics platform."
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"
SECTION = "bootloaders"

PR = "r3"

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
    bison-native \
    flex-native \
"

# TODO: we should use the yocto toolchain
DEPENDS += "gcc-arm-aarch64-linux-gnu-native gcc-arm-arm-linux-gnueabihf-native"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"


SRC_URI = " \
    ${SYNA_SRC_BOOT} \
    ${SYNA_SRC_UBOOT} \
    ${SYNA_SRC_EXTERNAL} \
    file://0001-Force-gcc-as-HOSTCC.patch \
"

SRCREV_uboot = "${SYNA_SRCREV_UBOOT}"
SRCREV_boot = "${SYNA_SRCREV_BOOT}"
SRCREV_external = "${SYNA_SRCREV_EXTERNAL}"

SRCREV_FORMAT = "uboot_boot_external"

require recipes-devtools/synasdk/synasdk-build.inc

PV = "2019.10+git${SRCPV}"

do_compile () {
    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    clean=0 . build/module/uboot/build.sh ${CONFIG_FILE}

    if [ $? -ne 0 ]; then
        echo 'bootloader build failed!'
        exit 1
    fi
}

do_deploy () {
    # sm_fw_en.bin (only for GenX, i.e PLATYPUS for now)
    if [ -f "${B}/target/release/uboot/sm_fw_en.bin" ]; then
        install -m 0644 "${B}/target/release/uboot/sm_fw_en.bin" ${DEPLOYDIR}
    fi

    # bootloader.subimg
    prepend_image_info.sh ${B}/target/release/uboot/uboot_en.bin ${DEPLOYDIR}/bootloader_nopreload.subimg

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
