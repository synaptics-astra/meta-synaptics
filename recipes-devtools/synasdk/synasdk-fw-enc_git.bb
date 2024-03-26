DESCRIPTION = "Synaptics platform firmware images"
SECTION = "devtools"
LICENSE = "CLOSED"

PR = "r3"

inherit deploy nopackages

DEPENDS = " \
    synasdk-security-native \
    synasdk-tools-native \
"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " \
    ${SYNA_SRC_FW_ENC} \
"

SRCREV_fwenc = "${SYNA_SRCREV_FW_ENC}"

SRCREV_FORMAT = "fwenc"

require synasdk-build.inc

PV = "${ASTRA_VERSION}+git${SRCPV}"


do_compile() {

    CONFIG_RDK_SYS=y

    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    . build/install.rc
    . build/module/fw_enc/fw_list.rc
    . build/module/fw_enc/common.rc

}

do_install() {
    :
}

do_deploy () {

    for i in target/${CONFIG_FW_IMAGE_PATH}/*.fw; do
        [ -f "$i" ] || continue
        install -m 0644 ${i} ${DEPLOYDIR}
    done
}

addtask deploy before do_package after do_install
