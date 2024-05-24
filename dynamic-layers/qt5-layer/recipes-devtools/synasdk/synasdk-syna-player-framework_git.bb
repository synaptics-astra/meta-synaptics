SUMMARY = "Synaptics video playback framework"
SECTION = "multimedia"
LICENSE = "Apache-2.0"

DEPENDS += "qtbase qtdeclarative glib-2.0 gstreamer1.0 \
            gstreamer1.0-plugins-base gstreamer1.0-plugins-bad udev \
            synasdk-demos"
RDEPENDS_${PN} += "gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
        gstreamer1.0-plugins-bad udev"

SRC_URI = "${SYNA_SRC_DEMOS}"

SRCREV = "${SYNA_SRCREV_DEMOS}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/demos/framework/syna-player-framework"

inherit pkgconfig
inherit qmake5

do_install () {
    install -d ${D}${libdir}
    install -m 0644 ${WORKDIR}/build/libsyna-player-framework.so.1.0.0  ${D}${libdir}/libsyna-player-framework.so

    cd ${D}${libdir}
    ln -s libsyna-player-framework.so libsyna-player-framework.so.1

    install -d ${D}${includedir}
    install -m 644 ${S}/*.h ${D}${includedir}

    install -d ${D}${libdir}/pkgconfig
    sed 's/@@PV@@/${PV}/g' ${S}/syna-player-framework.pc.in > ${S}/syna-player-framework.pc
    install -m 0644 ${S}/syna-player-framework.pc ${D}${libdir}/pkgconfig/syna-player-framework.pc
}

FILES:${PN} = " \
    ${libdir}/libsyna-player-framework.so \
    ${libdir}/libsyna-player-framework.so.1 \
"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN}-dev = " \
    ${includedir} \
    ${libdir}/pkgconfig/syna-player-framework.pc \
"
PACKAGES = "${PN} ${PN}-dbg ${PN}-dev"
