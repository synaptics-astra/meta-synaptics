DESCRIPTION = "Synaptics fastlogo"
SECTION = "devtools"
LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

PR = "r2"

inherit nopackages deploy

DEPENDS = " \
    synasdk-security-native \
    synasdk-tools-native \
    vim-native \
    bc-native \
"

COMPATIBLE_MACHINE = "syna"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "${SYNA_SRC_CONFIGS}"

SRCREV_configs = "${SYNA_SRCREV_CONFIGS}"

SRCREV_FORMAT = "configs"

require synasdk-build.inc

PV = "${ASTRA_VERSION}+git${SRCPV}"

do_compile:append () {
    security_tools_path="${STAGING_DIR_NATIVE}${prefix}/libexec/syna/"
    security_keys_path="${STAGING_DATADIR_NATIVE}/syna/keys/${syna_chip_name}/${syna_chip_rev}"
    product_dir="${CONFIG_SYNA_SDK_PRODUCT_PATH}/${CONFIG_PRODUCT_NAME}"

    # Copy fastlogo.subimg.gz, if it exists
    if [ -f ${product_dir}/fastlogo.subimg.gz ]; then
        install -m 0644 ${product_dir}/fastlogo.subimg.gz ${B}/fastlogo.subimg.gz
    fi

    if [ "is${CONFIG_GENX_ENABLE}" = "isy" ]; then
        outdir_subimg_intermediate="${B}"
        imagedir="${topdir}/build/module/image"
        basedir_tools="${STAGING_BINDIR_NATIVE}"
        basedir_script_subimg="${imagedir}/lib/subimage"
        script_dir="${imagedir}/lib/boot_type/emmc"

        source ${basedir_script_subimg}/fastlogo/common.bashrc
        source ${basedir_script_subimg}/fastlogo/emmc.bashrc
        if [ -f ${outdir_subimg_intermediate}/fastlogo.subimg ]; then
            cat ${outdir_subimg_intermediate}/fastlogo.subimg | gzip -c > ${outdir_subimg_intermediate}/fastlogo.subimg.gz
        fi
    fi
}

do_install() {
    :
}

do_deploy() {
    install -m 0644 fastlogo.subimg.gz ${DEPLOYDIR}
}

addtask deploy before do_package after do_install
