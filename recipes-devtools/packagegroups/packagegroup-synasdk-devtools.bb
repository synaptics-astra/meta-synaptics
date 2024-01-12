#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

SUMMARY = "Package Group for Synaptics specific development tools"

LICENSE = "CLOSED"
LICENSE_FLAGS = "Synaptics-EULA"

inherit packagegroup

PACKAGES = " \
    packagegroup-synasdk-devtools \
"

# Generic RDK components
RDEPENDS:packagegroup-synasdk-devtools = " \
    synasdk-ampbase-dev \
    synasdk-ampdrm-dev \
    synasdk-ampddl-dev \
    synasdk-mw-dev \
"
