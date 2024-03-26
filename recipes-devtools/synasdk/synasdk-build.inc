require synasdk-config.inc

DEPENDS += " rsync-native"

SRC_URI:append = " ${SYNA_SRC_BUILD}"

SRCREV_build = "${SYNA_SRCREV_BUILD}"

SRCREV_FORMAT:append = "_build"

S = "${WORKDIR}/${SYNA_SOURCE_PREFIX}"
B = "${WORKDIR}/${BP}"

do_compile:prepend () {
    cd ${S}

    topdir=${S}
    TOPDIR=${S}

    . ${CONFIG_FILE}

    CONFIG_SYNA_SDK_PATH=${S}
    CONFIG_SYNA_SDK_OUT_TARGET_PATH=${B}/target
    CONFIG_SYNA_SDK_OUT_HOST_OBJ_PATH=${B}/host/obj/EXECUTABLES
    CONFIG_SYNA_SDK_OUT_HOST_REL_PATH=${B}/host/bin
    CONFIG_SYNA_SDK_BUILD_PATH=${B}/build

    CONFIG_RDK_SYS=y

    . ${CHIP_RC_FILE}

}

do_install:prepend () {
    topdir=${S}
    TOPDIR=${S}

    . ${CONFIG_FILE}

    CONFIG_SYNA_SDK_PATH=${S}
    CONFIG_SYNA_SDK_OUT_TARGET_PATH=${B}/target
    CONFIG_SYNA_SDK_OUT_HOST_OBJ_PATH=${B}/host/obj/EXECUTABLES
    CONFIG_SYNA_SDK_OUT_HOST_REL_PATH=${B}/host/bin
    CONFIG_SYNA_SDK_BUILD_PATH=${B}/build

    CONFIG_RDK_SYS=y

    . ${CHIP_RC_FILE}

}

do_deploy:prepend () {
    topdir=${S}
    TOPDIR=${S}

    . ${CONFIG_FILE}

    CONFIG_SYNA_SDK_PATH=${S}
    CONFIG_SYNA_SDK_OUT_TARGET_PATH=${B}/target
    CONFIG_SYNA_SDK_OUT_HOST_OBJ_PATH=${B}/host/obj/EXECUTABLES
    CONFIG_SYNA_SDK_OUT_HOST_REL_PATH=${B}/host/bin
    CONFIG_SYNA_SDK_BUILD_PATH=${B}/build

    CONFIG_RDK_SYS=y

    . ${CHIP_RC_FILE}

}
