SUMMARY = "Synaptics USB PHY Driver Kernel Module"
DESCRIPTION = "${SUMMARY}"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

COMPATIBLE_MACHINE = "syna"

KERNEL_MODULE_AUTOLOAD:append = " phy-syna-usb"
KERNEL_MODULE_PROBECONF:append = " phy-syna-usb"

inherit module

SRC_URI = "file://COPYING \
           ${SYNA_SRC_LINUX_5_15_MODULES}"

SRCREV = "${SYNA_SRCREV_LINUX_5_15_MODULES}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_MODULES_SOURCE_PREFIX}/drivers/phy/synaptics/phy-syna-usb"


do_install:append() {
    rm -f ${D}/lib/modules/${KERNEL_VERSION}/extra/modules.order.*
}

RPROVIDES:${PN} += "kernel-module-phy-syna-usb"
