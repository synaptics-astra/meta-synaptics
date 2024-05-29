SUMMARY = "Synaptics QT AI Video Player"
SECTION = "multimedia"
LICENSE = "Apache-2.0"

DEPENDS += "qtbase qtdeclarative glib-2.0 gstreamer1.0 \
            gstreamer1.0-plugins-base gstreamer1.0-plugins-bad udev \
            synasdk-syna-player-framework"
RDEPENDS_${PN} += "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins \
        gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
        gstreamer1.0-plugins-bad udev"

qmldir = "/home/root/demos/qmls/"

SRC_URI = "${SYNA_SRC_DEMOS}"

SRCREV = "${SYNA_SRCREV_DEMOS}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/demos/syna-ai-player"

LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

inherit pkgconfig
inherit qmake5

do_install () {
    install -d ${D}${qmldir}
    install -d ${D}${qmldir}/panels
    install -d ${D}${qmldir}/customviews

    for i in ${S}/qmls/panels/*\.qml; do
        file_name=`basename "${i}"`
        install -m 0644 ${i} ${D}${qmldir}/panels/${file_name}
    done

    for i in ${S}/qmls/customviews/*\.qml; do
        file_name=`basename "${i}"`
        install -m 0644 ${i} ${D}${qmldir}/customviews/${file_name}
    done

    if [ "${MACHINE}" = "sl1640" ]; then
        install -m 0644 ${S}/qmls/sl1640-ai.qml  ${D}${qmldir}/
    fi

    if [ "${MACHINE}" = "sl1680" ]; then
        install -m 0644 ${S}/qmls/sl1680-ai.qml  ${D}${qmldir}/
    fi

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/build/syna-ai-player ${D}${bindir}/
}

FILES:${PN} = " \
    ${bindir}/syna-ai-player \
    ${qmldir} \
"
