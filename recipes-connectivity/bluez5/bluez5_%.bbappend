#/*******************************************************************************
#*
#*             Copyright 2021, Beechwoods Software, Inc.
#*
#*******************************************************************************/

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://0001-change-service-type-to-notify-for-syna.patch \
"
