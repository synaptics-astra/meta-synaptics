DESCRIPTION = "Synaptics SDK"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

PR = "r1"

inherit deploy

DEPENDS = " \
    synasdk-tools-native \
"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " \
    ${SYNA_SRC_BOOT} \
    ${SYNA_SRC_PREBOOT} \
"

SRCREV_boot = "${SYNA_SRCREV_BOOT}"
SRCREV_preboot = "${SYNA_SRCREV_PREBOOT}"

SRCREV_FORMAT = "boot_preboot"

require synasdk-build.inc

PV = "${ASTRA_VERSION}+git${SRCPV}"

do_compile () {
    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    clean=0 preboot_module_dir="${S}/build/module/preboot" . build/module/preboot/build.sh
    if [ $? -ne 0 ]; then
        echo 'preboot build failed!'
        exit 1
    fi

}

do_deploy() {
    install -m 0644 target/preboot/preboot_esmt.bin ${DEPLOYDIR}/preboot.subimg
}

addtask deploy before do_package after do_install
