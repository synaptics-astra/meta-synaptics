SUMMARY = "Synaptics dolphin Pinctrl Driver Kernel Module"
DESCRIPTION = "${SUMMARY}"

LICENSE = "CLOSED"

COMPATIBLE_MACHINE = "dolphin"

KERNEL_MODULE_AUTOLOAD:append = " pinctrl-dolphin"
KERNEL_MODULE_PROBECONF:append = " pinctrl-dolphin"

inherit module

SRC_URI = "${SYNA_SRC_LINUX_5_15_MODULES}"

SRCREV = "${SYNA_SRCREV_LINUX_5_15_MODULES}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SDK_PATH}/linux_5_15/modules/drivers/pinctrl/berlin/pinctrl-dolphin"

do_install:append() {
    rm -f ${D}/lib/modules/${KERNEL_VERSION}/extra/modules.order.*
}

RPROVIDES:${PN} += "kernel-module-pinctrl-dolphin"
