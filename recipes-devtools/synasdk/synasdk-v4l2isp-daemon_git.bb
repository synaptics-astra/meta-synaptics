DESCRIPTION = "V4L2 ISP"
SECTION = "multimedia"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"
PR = "r0"

SRC_URI = "${SYNA_SRC_V4L2ISP}"

SRCREV = "${SYNA_SRCREV_V4L2ISP}"

PV = "git${SRCPV}"

COMPATIBLE_MACHINE = "dolphin"

DEPENDS = "libmxml synasdk-v4l2isp-sensordrv synasdk-v4l2isp-prebuilts"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/v4l2isp/daemon"

inherit pkgconfig cmake systemd

SYSTEMD_SERVICE:${PN} = "isp_media_server.service"
SYSTEMD_AUTO_ENABLE = "enable"

SRC_URI:append:dolphin = " \
    file://ISP_Manual.json \
    file://ISP_Auto.json \
    file://IMX258.xml \
    file://isp_media_server.service \
    file://isp_media_server.sh \
"

EXTRA_OECMAKE = " \
                -DCMAKE_BUILD_TYPE=RELEASE  \
"

FILES:${PN} = " \
    ${libdir}/*.so \
    ${bindir}/* \
    ${sbindir}/* \
    ${systemd_system_unitdir}/* \
    ${datadir}/* \
"

INSANE_SKIP:${PN} += "dev-so"
FILES_SOLIBSDEV = ""

do_install:append() {

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/isp_media_server.service ${D}${systemd_system_unitdir}
        install -d ${D}${sbindir}
        install -m 0755 ${WORKDIR}/isp_media_server.sh ${D}${sbindir}
    fi

    install -d ${D}${datadir}
    install -m 0644 ${WORKDIR}/ISP_Manual.json ${D}${datadir}
    install -m 0644 ${WORKDIR}/ISP_Auto.json ${D}${datadir}
    install -m 0644 ${WORKDIR}/IMX258.xml ${D}${datadir}
}
