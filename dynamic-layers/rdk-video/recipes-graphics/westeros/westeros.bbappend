#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTERNALSRC:pn-westeros = ""

SRC_URI:append:syna = " \
    file://0001-change-resolution-to-1080p.patch \
"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"

#FIXME: choose xdgv4 or 5
#PACKAGECONFIG:syna = "incapp inctest increndergl incsbprotocol xdgv4"
PACKAGECONFIG:syna = "incapp inctest increndergl incsbprotocol xdgv5"

#FIXME: add more flags
CPPFLAGS:append:syna = " \
    -DWESTEROS_PLATFORM_DRM \
    -DUSE_MESA \
"

inherit systemd update-rc.d

do_configure:prepend:syna() {
    sed -i -e 's/-lwesteros_simplebuffer_client/-lwesteros_compositor -lwesteros_simplebuffer_client/g' ${S}/drm/westeros-sink/Makefile.am
}

do_compile:prepend:syna () {
    export WESTEROS_COMPOSITOR_EXTRA_LIBS="-lEGL -lGLESv2"
}

do_install:append:syna () {
    # Appending required environment variable for westeros
    install -D -m 0644 ${S}/systemd/westeros-env ${D}${sysconfdir}/default/westeros-env
    # defining environment variable for westeros
    echo "XDG_RUNTIME_DIR=/run/user/0/" >> ${D}${sysconfdir}/default/westeros-env
    echo "WAYLAND_DISPLAY=WPE" >> ${D}${sysconfdir}/default/westeros-env
    # Card for DRM on Synaptics is card0, after inverting modules load order
    echo "WESTEROS_DRM_CARD=/dev/dri/card0" >> ${D}${sysconfdir}/default/westeros-env
}

INITSCRIPT_NAME = "westeros"
INITSCRIPT_PARAMS = "defaults"

