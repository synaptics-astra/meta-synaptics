#/*******************************************************************************
#*
#*             Copyright 2020, Beechwoods Software, Inc.
#*
#*******************************************************************************/

# Version 6 of the Berkeley DB from Oracle (formerly Sleepycat)
#
# At present this package only installs the DB code
# itself (shared libraries, .a in the dev package),
# documentation and headers.
#
# The headers have the same names as those as v3
# of the DB, only one version can be used *for dev*
# at once - DB3 and DB6 can both be installed on the
# same system at the same time if really necessary.
SECTION = "libs"
SUMMARY = "Berkeley Database v6"
HOMEPAGE = "http://www.oracle.com/technetwork/database/database-technologies/berkeleydb/overview/index.html"
LICENSE = "AGPL-3.0"
VIRTUAL_NAME ?= "virtual/db"
RCONFLICTS:${PN} = "db3"

SRC_URI = "http://download.oracle.com/berkeley-db/db-${PV}.tar.gz"
SRC_URI += "file://arm-thumb-mutex_db5.patch;patchdir=.. \
            file://Makefile-let-libso_target-depend-on-bt_rec.patch \
            file://Makefile-let-libdb-6.0.la-depend-os_map.l.patch;patchdir=.. \
           "

SRC_URI[md5sum] = "33491b4756cb44b91c3318b727e71023"
SRC_URI[sha256sum] = "a9c5e2b004a5777aa03510cfe5cd766a4a3b777713406b02809c17c8e0e7a8fb"

# Exclude NC versions which lack AES encryption
UPSTREAM_CHECK_REGEX = "db-(?P<pver>\d+\.\d+(\.\d+)?).tar"
UPSTREAM_CHECK_URI = "http://www.oracle.com/technetwork/products/berkeleydb/downloads/index-082944.html"

LIC_FILES_CHKSUM = "file://../LICENSE;md5=1ec8b0b17cc31513fe35ab10716f8490"

inherit autotools

# Put virtual/db in any appropriate provider of a
# relational database, use it as a dependency in
# place of a specific db and use:
#
# PREFERRED_PROVIDER_virtual/db
#
# to select the correct db in the build (distro) .conf
PROVIDES += "${VIRTUAL_NAME}"

# bitbake isn't quite clever enough to deal with sleepycat,
# the distribution sits in the expected directory, but all
# the builds must occur from a sub-directory.  The following
# persuades bitbake to go to the right place
S = "${WORKDIR}/db-${PV}/dist"
B = "${WORKDIR}/db-${PV}/build_unix"
SPDX_S = "${WORKDIR}/db-${PV}"

# The executables go in a separate package - typically there
# is no need to install these unless doing real database
# management on the system.
inherit lib_package

PACKAGES =+ "${PN}-cxx"
FILES:${PN}-cxx = "${libdir}/*cxx*so"


# The dev package has the .so link (as in db3) and the .a's -
# it is therefore incompatible (cannot be installed at the
# same time) as the db3 package
# sort out the .so since they do version prior to the .so
SOLIBS = "-6*.so"
FILES_SOLIBSDEV = "${libdir}/libdb.so ${libdir}/libdb_cxx.so"

#configuration - set in local.conf to override
# All the --disable-* options replace --enable-smallbuild, which breaks a bunch of stuff (eg. postfix)
DB6_CONFIG ?= "--enable-o_direct --disable-cryptography --disable-queue --disable-replication --disable-verify --disable-compat185 --disable-sql --disable-atomicsupport"

EXTRA_OECONF = "${DB6_CONFIG} --enable-shared --enable-cxx --with-sysroot"

# Override the MUTEX setting here, the POSIX library is
# the default - "POSIX/pthreads/library".
# Don't ignore the nice SWP instruction on the ARM:
# These enable the ARM assembler mutex code
ARM_MUTEX = "--with-mutex=ARM/gcc-assembly"
MUTEX = ""
MUTEX:arm = "${ARM_MUTEX}"
MUTEX:armeb = "${ARM_MUTEX}"
EXTRA_OECONF += "${MUTEX}"
EXTRA_OEMAKE:class-target = "LIBTOOL=${STAGING_BINDIR_CROSS}/${HOST_SYS}-libtool"

# Cancel the site stuff - it's set for db3 and destroys the
# configure.
CONFIG_SITE = ""
do_configure() {
	gnu-configize --force ${S}
	export STRIP="true"
	oe_runconf
}

do_compile:prepend() {
	sed -i -e 's|hardcode_into_libs=yes|hardcode_into_libs=no|' \
		${B}/libtool
}

do_install:append() {
	mkdir -p ${D}/${includedir}/db60
	mv ${D}/${includedir}/db.h ${D}/${includedir}/db60/.
	mv ${D}/${includedir}/db_cxx.h ${D}/${includedir}/db60/.
	ln -s db60/db.h ${D}/${includedir}/db.h
	ln -s db60/db_cxx.h ${D}/${includedir}/db_cxx.h

	# The docs end up in /usr/docs - not right.
	if test -d "${D}/${prefix}/docs"
	then
		mkdir -p "${D}/${datadir}"
		test ! -d "${D}/${docdir}" || rm -rf "${D}/${docdir}"
		mv "${D}/${prefix}/docs" "${D}/${docdir}"
	fi

	chown -R root:root ${D}
}

INSANE_SKIP:${PN} = "dev-so"
INSANE_SKIP:${PN}-cxx = "dev-so"

BBCLASSEXTEND = "native nativesdk"

