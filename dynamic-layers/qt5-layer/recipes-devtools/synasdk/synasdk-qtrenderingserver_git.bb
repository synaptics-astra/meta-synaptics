SUMMARY = "QT renderingserver Recipe"
SECTION = "multimedia"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

DEPENDS += "qtbase qtdeclarative qtmultimedia qtxmlpatterns libpng jpeg udev python3"
RDEPENDS_${PN} += "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins"

SRC_URI = "${SYNA_SRC_DEMOS}"

SRCREV = "${SYNA_SRCREV_DEMOS}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/demos/qtrenderingserver"

inherit qmake5


do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${B}/qtrenderingserver ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo.qml ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo_lcd.qml ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo_445x250.qml ${D}${bindir}
    install -m 0755 ${S}/res/DemoVideo_myna2_800_1280.qml ${D}${bindir}
    install -m 0755 ${S}/res/Animation1.qml ${D}${bindir}
    install -m 0755 ${S}/res/Animation2.qml ${D}${bindir}
    install -m 0755 ${S}/res/SynaAVeda.jpg ${D}${bindir}
    install -m 0755 ${S}/res/dolphin_secondary.qml ${D}${bindir}
    install -m 0755 ${S}/res/dolphin_primary.qml ${D}${bindir}
    install -m 0755 ${S}/res/myna2_secondary.qml ${D}${bindir}
    install -m 0755 ${S}/res/platypus_primary.qml ${D}${bindir}
    install -m 0755 ${S}/res/myna2_primary.qml ${D}${bindir}
    install -m 0755 ${S}/res/platypus_primary_44.qml ${D}${bindir}
    install -m 0755 ${S}/res/dolphin_primary_44.qml ${D}${bindir}
    install -m 0755 ${S}/res/Statistics.qml ${D}${bindir}
}
