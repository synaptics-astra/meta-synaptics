DESCRIPTION = "Synaptics Trusted Execution Environment EL1 image"
SECTION = "devtools"
LICENSE = "CLOSED"
PR = "r2"

inherit nopackages deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS += "synasdk-security-native synasdk-tools-native vim-native bc-native"

COMPATIBLE_MACHINE = "syna"

SRC_URI = "${SYNA_SRC_TEE}"
SRCREV_tee = "${SYNA_SRCREV_TEE}"

SRCREV_FORMAT = "tee"

require synasdk-build.inc

PV = "${ASTRA_VERSION}+git${SRCPV}"

do_compile:append () {
    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    . build/module/tee/build.sh
}

do_deploy() {
    install -m 0644 ${B}/target/tee/tzk/tee_en.bin ${DEPLOYDIR}
    install -m 0644 ${B}/target/tee/tzk/tee_recovery_en.bin ${DEPLOYDIR}
    prepend_image_info.sh ${B}/target/tee/tzk/tee_en.bin ${DEPLOYDIR}/tee.subimg
    prepend_image_info.sh ${B}/target/tee/tzk/tee_recovery_en.bin ${DEPLOYDIR}/tee_recovery.subimg
}

addtask deploy after do_compile
