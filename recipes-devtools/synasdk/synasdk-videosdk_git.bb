DESCRIPTION = "VIDEOSDK"
SECTION = "multimedia"
LICENSE = "CLOSED"

DEPENDS = " \
    gstreamer1.0-meta-base \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-ugly \
    ffmpeg \
    libdrm \
"

SRC_URI = "${SYNA_SRC_VIDEOSDK}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/application/videosdk"

SRCREV = "${SYNA_SRCREV_VIDEOSDK}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

rootdir = "/home/root"

COMPATIBLE_MACHINE = "syna"

CC:remove = "-Werror=format-security"
CXX:remove = "-Werror=format-security"

V4L2_ENABLE = "1"
V4L2_ENABLE:myna2 = "0"

CLEANBROKEN = "1"

do_compile() {
    topdir="${WORKDIR}/${SYNA_SOURCE_PREFIX}"
    outdir_intermediate="${B}/videosdk"
    mkdir -p "${outdir_intermediate}"

    DESTDIR="${outdir_intermediate}" \
    SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" \
    TOPDIR="${WORKDIR}/${SYNA_SOURCE_PREFIX}" \
    CURR_MACH="${syna_chip_name}" \
    V4L2_ENABLE="${V4L2_ENABLE}" \
    ${MAKE} -C ${S}

    DESTDIR="${outdir_intermediate}" \
    SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" \
    TOPDIR="${WORKDIR}/${SYNA_SOURCE_PREFIX}" \
    CURR_MACH="${syna_chip_name}" \
    V4L2_ENABLE="${V4L2_ENABLE}" \
    ${MAKE} -C ${S} install
}

do_install () {
    install -d ${D}${bindir}
    for i in \
        codec-demo;
    do
        install -m 0755 ${B}/videosdk/usr/bin/$i ${D}${bindir}
    done

    install -d ${D}${rootdir}
    install ${THISDIR}/files/.profile ${D}${rootdir}/.profile
}

PACKAGES = " \
    synasdk-videosdk \
    synasdk-videosdk-dev \
    synasdk-videosdk-dbg \
"

FILES:${PN} = " \
    ${bindir}/* \
    ${libdir}/*.so \
    ${rootdir}/.profile \
"

FILES:${PN}-dev = " \
    ${includedir}/syna/* \
"

FILES:{$PN}-dbg = " \
    ${bindir}/.debug \
    ${libdir}/.debug \
"
INSANE_SKIP:${PN} += "ldflags"

