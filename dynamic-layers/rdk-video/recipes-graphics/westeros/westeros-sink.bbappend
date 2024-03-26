#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

DEPENDS += " \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
"
DEPENDS:append:syna = " \
    synasdk-ampbase \
"
RDEPENDS:${PN} = " \
    synasdk-ampsvc \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTERNALSRC:pn-westeros-sink = ""

SRC_URI:append:syna = " \
    file://westeros_sink_syna.tgz;subdir=git \
"

S:syna = "${WORKDIR}/git/syna/westeros-sink"
LICENSE_LOCATION:syna = "${WORKDIR}/git/LICENSE"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGECONFIG[gst1] = "--enable-gstreamer1=yes,--enable-gstreamer1=no,gstreamer1.0"
PACKAGECONFIG = "gst1"

SRC_URI:append:syna = " \
    file://westeros_sink_syna.tgz \
"

# Add both the multilib-prefixed and the non-prefixed paths, just in case
CPPFLAGS:append:syna = " \
    -I${STAGING_INCDIR}/syna \
    -I${STAGING_DIR}/${MLPREFIX}${MACHINE}/usr/include/syna \
"
# Add flags needed for AMPSDK
CPPFLAGS:append:platypus = " -D__VS640__"
CPPFLAGS:append:dolphin = " -D__VS680__"
CPPFLAGS:append:syna = " \
    -D__LINUX__ \
"

FILES:${PN} += "${libdir}/gstreamer-*/*.so"
FILES:${PN}-dev += "${libdir}/gstreamer-*/*.la"
FILES:${PN}-dbg += "${libdir}/gstreamer-*/.debug/*"
FILES:${PN}-staticdev += "${libdir}/gstreamer-*/*.a "

