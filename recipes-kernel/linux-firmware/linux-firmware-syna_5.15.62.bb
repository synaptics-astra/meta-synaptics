# Copyright 2021 - 2022, Synaptics Incorporated
include linux-firmware-syna.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"
