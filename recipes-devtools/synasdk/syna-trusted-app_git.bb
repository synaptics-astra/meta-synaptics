DESCRIPTION = "Synaptics Trusted Application"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

PR = "r2"

inherit deploy

DEPENDS += " synasdk-tools-native"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = " \
    ${SYNA_SRC_TA_ENC} \
"

SRCREV_taenc = "${SYNA_SRCREV_TA_ENC}"

SRCREV_FORMAT = "taenc"

require synasdk-build.inc

PV = "${ASTRA_VERSION}+git${SRCPV}"

do_compile () {
    # We may not need them from the syna-release package
    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    . ${S}/build/install.rc
    . ${S}/build/module/ta_enc/ta_list.rc
    . ${S}/build/module/ta_enc/common.rc
}

do_install () {
    input_ta_path="${B}/target/ta/enc/${syna_chip_name}/${syna_chip_rev}"
    find "${input_ta_path}" -type f -regex '.*\.ta$' -exec sh -c \
        'install -Dm0644 {} ${D}${nonarch_base_libdir}/ta/$(basename {})' \;

    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/ta

    KERNEL_LOAD_TA=""
    if [ "is${CONFIG_TA_GFX_IMG_LINUX}" = "isy" ]; then
        KERNEL_LOAD_TA+="libgfx_img_linux.ta"
    fi
    if [ "is${CONFIG_AMP_IOMMU}" = "isy" ]; then
        KERNEL_LOAD_TA+=" libptm.ta"
        KERNEL_LOAD_TA+=" libvmeta.ta"
    fi
    if [ "is${CONFIG_BL_TA_FASTLOGO}" = "isy" ]; then
        KERNEL_LOAD_TA+=" libfastlogo.ta"
    fi
    if [ "is${CONFIG_BL_TA_DHUB}" = "isy" ]; then
        KERNEL_LOAD_TA+=" libdhub.ta"
    fi

    for i in ${KERNEL_LOAD_TA}; do \
        mv "${D}${nonarch_base_libdir}/ta/${i}" \
             "${D}${nonarch_base_libdir}/firmware/ta/${i}"
    done;
}

FILES:${PN} = " \
    ${nonarch_base_libdir}/ta \
    ${nonarch_base_libdir}/firmware/ta \
"

do_deploy () {

    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"

    input_ta_path="target/${CONFIG_TA_IMAGE_PATH}/${syna_chip_name}/${syna_chip_rev}"
    # Use genimg to pack all preload TAs
    params=""

    if [ "is${CONFIG_BL_TA_FASTLOGO}" = "isy" ]; then
        if [ -f ${input_ta_path}/libfastlogo.ta/libfastlogo.ta ]; then
            params="$params -i LOGO -d ${input_ta_path}/libfastlogo.ta/libfastlogo.ta"
        else
            echo "no libfastlogo.ta under ${input_ta_path}"
            exit 1
        fi
    fi

    if [ "is${CONFIG_BL_TA_KEYMASTER}" = "isy" ]; then
        if [ -f ${input_ta_path}/libgencrypto.ta/libgencrypto.ta ]; then
            params="$params -i CYPT -d ${input_ta_path}/libgencrypto.ta/libgencrypto.ta"
        else
            echo "no libgencrypto.ta under ${input_ta_path}"
            exit 1
        fi
    fi

    if [ "is${CONFIG_BL_TA_DHUB}" = "isy" ]; then
        if [ -f ${input_ta_path}/libdhub.ta/libdhub.ta ]; then
            params="$params -i DHUB -d ${input_ta_path}/libdhub.ta/libdhub.ta"
        else
            echo "no libdhub.ta under ${input_ta_path}!!!"
            exit 1
        fi
    fi

    genimg -n preload_ta $params -o ${DEPLOYDIR}/preload_ta.subimg
    rm ${DEPLOYDIR}/*.header
}

addtask deploy before do_package after do_install
