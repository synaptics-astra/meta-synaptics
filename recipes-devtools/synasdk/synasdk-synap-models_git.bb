DESCRIPTION = "SyNAP models"
SECTION = "devtools"
LICENSE = "CLOSED"
PR = "r2"

inherit allarch

COMPATIBLE_MACHINE = "syna"

SRC_URI = "${SYNA_SRC_SYNAP_RELEASE}"

SRCREV = "${SYNA_SRCREV_SYNAP_RELEASE}"

PV = "${SYNAP_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/synap/release"

MODELS_DIR:dolphin = "dolphin"
MODELS_DIR:platypus = "platypus"
MODELS_DIR:myna2 = "myna2"

FILES:${PN} = "${datadir}/synap/models"
FILES:${PN} += "${datadir}/synap/vxk"

do_install () {

    install -m 0755 -d ${D}${datadir}/synap/models

    if [ -d ${S}/models/${MODELS_DIR}/ ]; then
        cp -R --no-dereference --preserve=mode,links -v ${S}/models/${MODELS_DIR}/* ${D}${datadir}/synap/models
    else
        touch ${D}${datadir}/synap/models/placeholder
    fi

    if [ -d ${S}/vxk/${MODELS_DIR} ]; then
        install -m 0755 -d ${D}${datadir}/synap/vxk
        cp -R --no-dereference --preserve=mode,links -v ${S}/vxk/${MODELS_DIR}/* ${D}${datadir}/synap/vxk
    fi
}
