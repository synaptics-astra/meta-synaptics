CONFIG_FILE = "${STAGING_DIR_NATIVE}/usr/share/syna/build/.config"
CONFIG_FILE:aarch64 = "${STAGING_DIR_NATIVE}/usr/share/syna/build/.config_alt"

EMMC_PT_FILE = "${STAGING_DIR_NATIVE}/usr/share/syna/build/emmc.pt"

CHIP_RC_FILE = "${STAGING_DIR_NATIVE}/usr/share/syna/build/chip.rc"

DEPENDS += " synasdk-config-native"