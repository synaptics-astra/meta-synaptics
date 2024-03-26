DESCRIPTION = "Synaptics SDK tools"
SECTION = "devtools"
LICENSE = "CLOSED"

PR = "r1"

inherit native

require synasdk-build.inc

SRCREV_FORMAT = "build"

PV = "${ASTRA_VERSION}+git${SRCPV}"

DEPENDS += " openssl-native synasdk-config-native"

TOOL_NAMES = "parse_pt crc mkbootimg mkyaffs2img parse_pt_emmc gen_uniimg gen_subimg_info genimg"

do_compile() {
    cd ${S}/build/tools/src/executables

    for i in ${TOOL_NAMES}; do
        make -C $i all OBJDIR=${B}/$i
        if [ $? -ne 0 ]; then
            echo 'make failed for tools (${i})!'
            exit 1
        fi
    done
}

do_install () {

    # Tools
    install -d ${D}${bindir}

    install -m 0755 ${S}/build/tools/bin/gen_secure_image ${D}${bindir}/gen_secure_image
    install -m 0755 ${S}/build/tools/bin/gen_x_secure_image ${D}${bindir}/gen_x_secure_image

    install -d ${D}${prefix}/libexec
    install -d ${D}${prefix}/libexec/syna

    install -m 0755 ${S}/build/tools/bin/in_extras.py ${D}${prefix}/libexec/syna/in_extras.py
    install -m 0755 ${S}/build/tools/bin/crc_calc.py ${D}${prefix}/libexec/syna/crc_calc.py
    install -m 0755 ${S}/build/tools/bin/gen_bg_crc64 ${D}${prefix}/libexec/syna/gen_bg_crc64

    install -m 0755 ${S}/build/tools/lib/sec_tools/bin/sign_image_v4 ${D}${prefix}/libexec/syna/sign_image_v4
    install -m 0755 ${S}/build/tools/lib/sec_tools/bin/genx_img ${D}${prefix}/libexec/syna/genx_img

    for file in ${TOOL_NAMES}
    do
        install -m 0755 ${B}/$file/$file ${D}${bindir}
    done

    install -m 0755 ${S}/build/tools/bin/prepend_image_info.sh ${D}${bindir}
}

PACKAGES = " \
    ${PN} \
"

FILES:${PN} = " \
    ${bindir}/* \
    ${prefix}/libexec/* \
"
