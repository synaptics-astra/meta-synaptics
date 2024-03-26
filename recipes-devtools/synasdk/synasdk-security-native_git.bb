DESCRIPTION = "Synaptics platform signature keys"
SECTION = "devtools"
LICENSE = "CLOSED"

PR = "r1"

inherit allarch native

SRC_URI = "${SYNA_SRC_SECURITY}"

SRCREV_security = "${SYNA_SRCREV_SECURITY}"

SRCREV_FORMAT = "security"

require synasdk-build.inc

PV = "${ASTRA_VERSION}+git${SRCPV}"

DEPENDS += " synasdk-config-native"

do_compile:append () {
    clean=0 . build/module/security/build.sh
    if [ $? -ne 0 ]; then
        echo 'security build failed!'
        exit 1
    fi
}

do_install:append () {
    # Keys
    install -d ${D}${datadir}/syna
    install -d ${D}${datadir}/syna/keys
    cp -r ${B}/target/${CONFIG_SECURITY_KEY_PATH}/* ${D}${datadir}/syna/keys
}

FILES:${PN} = " \
    ${datadir}/syna/keys \
"
