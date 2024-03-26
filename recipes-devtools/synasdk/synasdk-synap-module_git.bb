SUMMARY = "SyNAP kernel module"
DESCRIPTION = "${SUMMARY}"

LICENSE = "CLOSED"

COMPATIBLE_MACHINE = "platypus|dolphin"

KERNEL_MODULE_AUTOLOAD:append = " synap"
KERNEL_MODULE_PROBECONF:append = " synap"

module_conf_synap = "options synap ta_path=/lib/ta/"

inherit module

SRC_URI = "${SYNA_SRC_SYNAP_DRIVER}"

SRCREV = "${SYNA_SRCREV_SYNAP_DRIVER}"

PV = "${SYNAP_VERSION}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/synap/vsi_npu_driver/kernel"
B = "${WORKDIR}/${BPN}-${PV}"

EXTRA_OEMAKE = "-C ${STAGING_KERNEL_DIR} src=${S} M=${B}"

do_compile:prepend() {
    cp ${S}/Kbuild ${B}
}

do_install:append() {
    rm -f ${D}/lib/modules/${KERNEL_VERSION}/extra/modules.order.*
}

RPROVIDES:${PN} += "kernel-module-synap"
