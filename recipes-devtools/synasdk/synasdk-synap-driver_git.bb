DESCRIPTION = "SyNAP NPU Driver user space tools"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"
PR = "r2"

SRC_URI = "${SYNA_SRC_SYNAP_DRIVER}"

SRCREV = "${SYNA_SRCREV_SYNAP_DRIVER}"

PV = "${SYNAP_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "platypus|dolphin"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/synap/vsi_npu_driver"

inherit cmake

EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Release"

PACKAGES = "${PN} ${PN}-dev ${PN}-staticdev"

