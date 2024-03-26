#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

SOC_FAMILY = "syna"
include conf/machine/include/soc-family.inc

SERIAL_CONSOLE = "115200 ttyS0"

IMAGE_CLASSES += "image_synaimg"
IMAGE_FSTYPES ?= "synaimg"

KERNEL_IMAGETYPE ?= "Image"

MACHINE_FEATURES += "apm usbhost keyboard vfat alsa"

MACHINE_EXTRA_RRECOMMENDS += " \
    kernel-modules \
    kernel-firmware-sdsd8997-combo-v4 \
    linux-firmware-syna \
"

# Synaptics BSP default providers
PREFERRED_PROVIDER_virtual/bootloader ?= "syna-bootloader"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-syna"
PREFERRED_PROVIDER_virtual/egl ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles1 ?= "mesa"
PREFERRED_PROVIDER_virtual/libgles2 ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"
PREFERRED_PROVIDER_virtual/libpvr-dri-support ?= "synasdk-gpu-pvr"
PREFERRED_PROVIDER_virtual/libgles1-pvr ?= "synasdk-gpu-pvr"
PREFERRED_PROVIDER_virtual/libgles2-pvr ?= "synasdk-gpu-pvr"
PREFERRED_PROVIDER_virtual/libpvr-scope-srv ?= "synasdk-gpu-pvr"
PREFERRED_PROVIDER_virtual/pvr-fw ?= "synasdk-gpu-pvr"
MACHINE_EXTRA_RRECOMMENDS += "synasdk-gpu-pvr"
