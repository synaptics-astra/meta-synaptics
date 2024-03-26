FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:syna = " \
    file://qt-syna.patch \
"

PACKAGECONFIG_GL = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', 'no-opengl', d)}"

