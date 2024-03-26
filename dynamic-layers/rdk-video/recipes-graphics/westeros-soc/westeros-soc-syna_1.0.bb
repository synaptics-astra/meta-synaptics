#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

require recipes-graphics/westeros/westeros.inc

SUMMARY = "This recipe compiles the westeros gl component for the Synaptics platform"
LICENSE_LOCATION = "${S}/../LICENSE"

S = "${WORKDIR}/git/drm"

COMPATIBLE_MACHINE = "dolphin|platypus"

DEPENDS = " \
    wayland \
    virtual/egl \
    glib-2.0 \
    libdrm \
"

PROVIDES = "westeros-soc virtual/westeros-soc"
RPROVIDES:${PN} = "westeros-soc virtual/westeros-soc"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:syna = " \
    file://0001-add-refcnt-to-WstGLInit.patch \
"

CPPFLAGS:append = " \
    -I${STAGING_INCDIR}/libdrm \
    -DDRM_NO_NATIVE_FENCE \
"

SECURITY_CFLAGS:remove = "-fpie"
SECURITY_CFLAGS:remove = "-pie"

DEBIAN_NOAUTONAME:${PN} = "1"
DEBIAN_NOAUTONAME:${PN}-dbg = "1"
DEBIAN_NOAUTONAME:${PN}-dev = "1"
DEBIAN_NOAUTONAME:${PN}-staticdev = "1"

inherit autotools pkgconfig

FILES:${PN} = "${libdir}/*"

