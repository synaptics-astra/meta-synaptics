SUMMARY = "Synaptics Berlin IR Driver Kernel Module"
DESCRIPTION = "${SUMMARY}"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS = "kernel-module-sm"

COMPATIBLE_MACHINE = "platypus|dolphin"

KERNEL_MODULE_AUTOLOAD:append = " berlin-ir"
KERNEL_MODULE_PROBECONF:append = " berlin-ir"

inherit module

SRC_URI = "file://COPYING \
           ${SYNA_SRC_LINUX_5_15_MODULES}"

SRCREV = "${SYNA_SRCREV_LINUX_5_15_MODULES}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_MODULES_SOURCE_PREFIX}/drivers/input/keyboard/berlin-ir"

do_install:append() {
    rm -f ${D}/lib/modules/${KERNEL_VERSION}/extra/modules.order.*
}

RPROVIDES:${PN} += "kernel-module-berlin-ir"
