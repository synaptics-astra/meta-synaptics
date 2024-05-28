DESCRIPTION = "V4L2ISP prebuilt libraries"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"
PR = "r0"

COMPATIBLE_MACHINE = "dolphin"

SRC_URI = "${SYNA_SRC_V4L2ISP}"

SRCREV = "${SYNA_SRCREV_V4L2ISP}"

PV = "git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/v4l2isp"

DEPENDS = "libmxml synasdk-v4l2isp-sensordrv"

FILES:${PN} = " \
    ${libdir}/*.so* \
"

INSANE_SKIP:${PN} = "ldflags"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install () {
    install -m 0755 -d ${D}${libdir}
    install ${S}/lib/libisp_driver.so ${D}${libdir}
    install ${S}/lib/libmc_media_device.so ${D}${libdir}
    install ${S}/lib/libt_common_c.so* ${D}${libdir}
    install ${S}/lib/libt_json_c.so* ${D}${libdir}
    install ${S}/lib/libt_database_c.so* ${D}${libdir}
}
