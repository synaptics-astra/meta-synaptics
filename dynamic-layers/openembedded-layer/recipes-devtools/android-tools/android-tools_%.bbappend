FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://core/0015-adb-getenv-adbd-port.patch;patchdir=system/core \
    file://android-tools-adbd.service \
"

RDEPENDS:${PN}-adbd = "${PN}-conf-configfs"
