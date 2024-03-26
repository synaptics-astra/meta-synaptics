DESCRIPTION = "SyNAP prebuilt libraries"
SECTION = "devtools"
LICENSE = "CLOSED"
PR = "r2"

COMPATIBLE_MACHINE = "platypus|dolphin"

SRC_URI = "${SYNA_SRC_SYNAP_RELEASE}"

SRCREV = "${SYNA_SRCREV_SYNAP_RELEASE}"

PV = "${SYNAP_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/synap/release"

ARCH_DIR:aarch64 = "aarch64-oe"
ARCH_DIR:armv7a = "armv7a-oe"

INSANE_SKIP:${PN} = "ldflags"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

RDEPENDS:${PN} = "zlib"

do_install () {
    install -m 0755 -d ${D}${libdir}
    install ${S}/lib/${ARCH_DIR}/libebg_utils.so ${D}${libdir}
    install ${S}/lib/${ARCH_DIR}/libovxlib.so ${D}${libdir}
    install -d ${D}${includedir}

    for subdir in VX ovxlib synap_op
    do
        install -d ${D}${includedir}/${subdir}
        cp -R --no-dereference --preserve=mode,links -v ${S}/include/${subdir}/* ${D}${includedir}/${subdir}
    done

    install -d ${D}${includedir}/synap
    cp ${S}/include/synap/ebg_utils.h ${D}${includedir}/synap
}

RDEPENDS:${PN}:append:aarch64 = " tensorflow-lite tim-vx"
do_install:append:aarch64 () {
    install ${S}/lib/${ARCH_DIR}/libvx_delegate.so ${D}${libdir}
}
