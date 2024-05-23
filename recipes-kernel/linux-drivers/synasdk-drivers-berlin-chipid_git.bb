SUMMARY = "Synaptics Chip ID Driver Kernel Module"
DESCRIPTION = "${SUMMARY}"

LICENSE = "CLOSED"

COMPATIBLE_MACHINE = "syna"

KERNEL_MODULE_AUTOLOAD:append = " berlin-chipid"
KERNEL_MODULE_PROBECONF:append = " berlin-chipid"

inherit module

SRC_URI = "${SYNA_SRC_LINUX_5_15_MODULES}"

SRCREV = "${AUTOREV}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_MODULES_SOURCE_PREFIX}/drivers/soc/berlin/berlin-chipid"

do_install:append() {
    rm -f ${D}/lib/modules/${KERNEL_VERSION}/extra/modules.order.*
}

RPROVIDES:${PN} += "kernel-module-berlin-chipid"
