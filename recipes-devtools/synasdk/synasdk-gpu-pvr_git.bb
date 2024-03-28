DESCRIPTION = "Synaptics ImaginationTech PowerVR GPU binary files"
SECTION = "libs"
LICENSE = "CLOSED"

PR = "r1"

PROVIDES = " \
    virtual/libpvr-dri-support virtual/libgles1-pvr virtual/libgles2-pvr \
    virtual/libpvr-scope-srv \
    virtual/pvr-fw \
"

RDEPENDS:${PN} = " \
    libffi \
    libdrm \
    wayland \
    imgtec-pvr-firmware \
    libglapi libgles1-mesa libegl-mesa libgbm \
"

COMPATIBLE_MACHINE = "syna"
SYNAMACH:platypus = "sl1640"
SYNAMACH:dolphin = "sl1680"
SYNAMACH:myna2 = "sl1620"

PREBUILT_PATH = "sysroot/linux-baseline/data/gfx_prebuilt/imagination/${SYNAMACH}"

SRC_URI = " \
   ${SYNA_SRC_LINUX_SYSROOT} \
"

SRCREV = "${SYNA_SRCREV_LINUX_SYSROOT}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}"

do_install () {
    # install header files
    for i in ${S}/${PREBUILT_PATH}/include/*\.h; do
        file_name=`basename "${i}"`
        install -Dm0644 ${i} ${D}${includedir}/powervr/${file_name}
    done

    # install PowerVR Firmware
    install -d ${D}${nonarch_base_libdir}/firmware
    for i in ${S}/${PREBUILT_PATH}/fw/*; do
        install -m 0644 ${i} ${D}${nonarch_base_libdir}/firmware
    done
}

do_install:append:aarch64 () {
    for i in ${S}/${PREBUILT_PATH}/${HOST_SYS}/lib/*\.so*; do
        file_name=`basename "${i}"`
        install -Dm0644 ${i} ${D}${libdir}/${file_name}
    done

    for i in ${S}/${PREBUILT_PATH}/${HOST_SYS}/bin/*; do
        file_name=`basename "${i}"`
        install -Dm0755 ${i} ${D}${bindir}/${file_name}
    done
}

do_install:append:arm () {
    pushd ${S}/${PREBUILT_PATH}

    # install MESA backend
    for i in \
        libsrv_um.so libsutu_display.so libusc.so \
        libglslcompiler.so \
        libpvr_dri_support.so libGLESv1_CM_PVR_MESA.so libGLESv2_PVR_MESA.so \
        ;
    do
        [ -f lib/${i} ] || continue
        install -Dm0644 lib/${i} ${D}${libdir}/${i}
    done

    # PVRScope performance monitor
    for i in \
        libPVRScopeServices.so \
        ;
    do
        [ -f lib/${i} ] || continue
        install -Dm0644 lib/${i} ${D}${libdir}/${i}
    done

    popd
}

INSANE_SKIP:${PN} = "ldflags"
INSANE_SKIP:${MLPREFIX}imgtec-pvr-firmware = "arch"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

PACKAGES:prepend = " \
    pvrscope \
    imgtec-pvr-firmware \
"

FILES:pvrscope = " \
    ${libdir}/libPVRScopeServices${SOLIBS} \
"

# The firmware is MIPS, not ARM!
FILES:imgtec-pvr-firmware = " \
    ${nonarch_base_libdir}/firmware/* \
"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
