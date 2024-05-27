DESCRIPTION = "V4L2 ISP"
SECTION = "multimedia"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"
PR = "r0"

SRC_URI = "${SYNA_SRC_V4L2ISP}"

SRCREV = "${SYNA_SRCREV_V4L2ISP}"

PV = "git${SRCPV}"

COMPATIBLE_MACHINE = "dolphin"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/v4l2isp/sensor_drv"

inherit pkgconfig cmake

EXTRA_OECMAKE = " \
                -DCMAKE_BUILD_TYPE=RELEASE  \
"

FILES:${PN} = " \
    ${libdir}/*.so \
    ${libdir}/*.a \
    ${bindir}/* \
"

INSANE_SKIP:${PN} += "dev-so"
FILES_SOLIBSDEV = ""
