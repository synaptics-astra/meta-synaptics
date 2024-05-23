DESCRIPTION = "Synaptics Mount root device partitions"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

PR = "r1"

SRC_URI = "file://syna-mount-generator"

do_install () {
     install -d ${D}/${systemd_unitdir}/system-generators
     install -m 0755 ${WORKDIR}/syna-mount-generator ${D}/${systemd_unitdir}/system-generators
}

FILES:${PN} = "${systemd_unitdir}/system-generators/syna-mount-generator"
