SUMMARY = "QT renderingserver Recipe"
SECTION = "multimedia"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

DEPENDS += "qtbase qtdeclarative qtmultimedia qtxmlpatterns libpng jpeg udev python3"
RDEPENDS_${PN} += "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins"

SRC_URI = "${SYNA_SRC_VIDEOSDK}"

SRCREV = "${SYNA_SRCREV_VIDEOSDK}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/videosdk/qtrenderingserver"

inherit qmake5


do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${B}/qtrenderingserver ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo.qml ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo_lcd.qml ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo_445x250.qml ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo_dvf_800_1280.qml ${D}${bindir}
    install -m 0755 ${S}/res/Animation1.qml ${D}${bindir}
    install -m 0755 ${S}/res/Animation2.qml ${D}${bindir}
    install -m 0755 ${S}/res/SynaAVeda.jpg ${D}${bindir}
    install -m 0755 ${S}/res/680_secondary.qml ${D}${bindir}
    install -m 0755 ${S}/res/680_primary.qml ${D}${bindir}
    install -m 0755 ${S}/res/120_secondary.qml ${D}${bindir}
    install -m 0755 ${S}/res/640_primary.qml ${D}${bindir}
    install -m 0755 ${S}/res/120_primary.qml ${D}${bindir}
    install -m 0755 ${S}/res/640_primary_44.qml ${D}${bindir}
    install -m 0755 ${S}/res/680_primary_44.qml ${D}${bindir}
    install -m 0755 ${S}/res/Statistics.qml ${D}${bindir}
}
