DESCRIPTION = "Synaptics TEE client library"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"
PR = "r3"

inherit meson systemd

COMPATIBLE_MACHINE = "syna"

SRC_URI = " \
   ${SYNA_SRC_TEE} \
   file://tee-supplicant.service \
   file://tee-supplicant-init \
"

SRCREV = "${SYNA_SRCREV_TEE}"

PV = "${ASTRA_VERSION}+git${SRCPV}"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}/tee/tee"

B = "${WORKDIR}/${BP}"

do_install:append() {
    mkdir -p ${D}${localstatedir}/lib/tee

    install -Dm644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_system_unitdir}/tee-supplicant.service
    install -Dm755 ${WORKDIR}/tee-supplicant-init ${D}/${sysconfdir}/init.d/tee-supplicant
    sed -i -e 's,@LOCALSTATEDIR@,${localstatedir},g' ${D}${systemd_system_unitdir}/tee-supplicant.service
    sed -i -e 's,@LOCALSTATEDIR@,${localstatedir},g' ${D}/${sysconfdir}/init.d/tee-supplicant
}

PACKAGES:prepend = "tee-supplicant "

FILES:tee-supplicant = " \
    ${sbindir}/tee_daemon \
    ${localstatedir} \
    ${systemd_system_unitdir}/tee-supplicant.service \
"

SYSTEMD_PACKAGES = "tee-supplicant"
SYSTEMD_SERVICE:tee-supplicant = "tee-supplicant.service"

INITSCRIPT_NAME = "tee-supplicant"
INITSCRIPT_PACKAGES = "tee-supplicant"
INITSCRIPT_PARAMS = "start 59 5 2 . stop 40 0 1 6 ."
