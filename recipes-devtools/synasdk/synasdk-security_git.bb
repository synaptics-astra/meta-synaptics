DESCRIPTION = "Synaptics platform signatures"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

PR = "r2"

DEPENDS += " synasdk-security-native"

inherit deploy nopackages

require synasdk-build.inc

SRCREV_FORMAT = "build"

PV = "${ASTRA_VERSION}+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile:append () {
    if [ "x${CONFIG_GENX_ENABLE}" = "x" ]; then

        set -x
        # Keys
        oemcustkey_ct5=`find ${STAGING_DIR_NATIVE}/usr/share/syna/keys/${syna_chip_name}/${syna_chip_rev}/ -name custk.keystore | grep codetype_5`
        oemextrarsakey_ct5=`find ${STAGING_DIR_NATIVE}/usr/share/syna/keys/${syna_chip_name}/${syna_chip_rev}/ -name extrsa.keystore | grep codetype_5`
        cp $oemcustkey_ct5 ${B}/oemcustkey
        cp $oemextrarsakey_ct5 ${B}/oemextrsakey

        # Align the key files to 1024
        key_size=`stat -c %s ${B}/oemcustkey`
        append_size=`expr 1024 - ${key_size} % 1024`
        if [ ${append_size} -lt 1024 ]; then
            dd if=/dev/zero of=${B}/oemcustkey bs=1 seek=${key_size} count=${append_size} conv=notrunc
        fi
        key_size=`stat -c %s ${B}/oemextrsakey`
        append_size=`expr 1024 - ${key_size} % 1024`
        if [ ${append_size} -lt 1024 ]; then
            dd if=/dev/zero of=${B}/oemextrsakey bs=1 seek=${key_size} count=${append_size} conv=notrunc
        fi

        # Concatenate the 2 key files
        cat ${B}/oemcustkey ${B}/oemextrsakey > ${B}/key.subimg

        # Align the key file to 16384
        key_size=`stat -c %s ${B}/key.subimg`
        append_size=`expr 1024 - ${key_size} % 1024`
        if [ ${append_size} -lt 1024 ]; then
            dd if=/dev/zero of=${B}/key.subimg bs=1 seek=${key_size} count=${append_size} conv=notrunc
        fi

        # Cleanup
        rm -f ${B}/oemcustkey ${B}/oemextrsakey
    else
        # No custk.keystore or extrsa.keystore for GenX
        echo "00000000" > ${B}/key.subimg
    fi
}

do_install() {
    :
}

do_deploy() {
    install -m 0644 ${B}/key.subimg ${DEPLOYDIR}/key.subimg
}

addtask deploy before do_package after do_install
