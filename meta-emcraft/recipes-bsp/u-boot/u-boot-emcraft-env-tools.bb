SUMMARY = "U-Boot enviroment tools"
DESCRIPTION = "Install fw_setenv, fw_printenv and fw_env.config"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb \
                    file://README;beginline=1;endline=22;md5=78b195c11cb6ef63e6985140db7d7bab"

PV = "git${SRCPV}"
PR = "r0"

SRC_URI = "${UBOOT_GIT_REPO} \
	   file://fw_env.config"

inherit autotools

S = "${WORKDIR}/git"
B = "${S}"

do_compile () {
  	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	oe_runmake ${UBOOT_MACHINE}
        oe_runmake HOSTCC="${CC}" env
}

do_install () {
	install -d ${D}${sysconfdir} ${D}/bin
        install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}
        install ${S}/tools/env/fw_printenv ${D}/bin
        ln -sf fw_printenv ${D}/bin/fw_setenv
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(vf6-som)"
