DESCRIPTION = "Synaptics eth0mac service"
SECTION = "devtools"
LICENSE = "CLOSED"
PR = "r0"

inherit base systemd

SRC_URI = " \
   file://eth0mac \
   file://eth0mac.service \
"

do_install() {
    install -d ${D}${sbindir}
    install -d ${D}${systemd_system_unitdir}
    install -m 0755 ${WORKDIR}/eth0mac ${D}${sbindir}/eth0mac
    install -m 0644 ${WORKDIR}/eth0mac.service ${D}/${systemd_system_unitdir}/eth0mac.service
}

SYSTEMD_PACKAGES = "synasdk-macaddr"
SYSTEMD_SERVICE:synasdk-macaddr = "eth0mac.service"
INITSCRIPT_NAME = "eth0mac"
INITSCRIPT_PACKAGES = "synasdk-macaddr"
INITSCRIPT_PARAMS = "start 8 5 2 . stop 21 0 1 6 ."

FILES:${PN} = " \
    ${sbindir}/eth0mac \
    ${systemd_system_unitdir}/eth0mac.service \
"
