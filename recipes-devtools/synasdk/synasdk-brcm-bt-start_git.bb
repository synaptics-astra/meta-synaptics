DESCRIPTION = "Synaptics brcm bt start service"
SECTION = "devtools"
LICENSE = "CLOSED"
PR = "r0"

inherit systemd

COMPATIBLE_MACHINE = "platypus|dolphin"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

RDEPENDS:${PN} += " \
    brcm-patchram-plus \
    linux-firmware-syna \
"

SRC_URI = " \
    file://brcm_bt_start.service \
"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/brcm_bt_start.service ${D}${systemd_system_unitdir}
}

SYSTEMD_SERVICE:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'brcm_bt_start.service', '', d)}"
FILES:${PN} += " \
    ${systemd_system_unitdir} \
"

