#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

do_compile:append:syna() {
    # Build static library
    ${AR} -r \
    ${BPN}.a \
    tinyxml.o \
    tinyxmlparser.o \
    tinyxmlerror.o \
    tinystr.o
}

do_install:append:syna() {
    install -d ${D}${libdir}
    install -m 0755 ${S}/libtinyxml.a ${D}${libdir}
}

