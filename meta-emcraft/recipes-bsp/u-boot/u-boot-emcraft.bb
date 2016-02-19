require ../../../meta/recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb \
                    file://README;beginline=1;endline=22;md5=78b195c11cb6ef63e6985140db7d7bab"

PV = "git${SRCPV}"
PR = "r0"

UBOOT_IMAGE = "u-boot-${PRJNAME}-${PV}-${PR}.nand"
UBOOT_BINARY = "u-boot.nand"
UBOOT_SYMLINK = "u-boot-${PRJNAME}.nand"
UBOOT_MAKE_TARGET = "u-boot.nand"

SRC_URI = "${UBOOT_GIT_REPO}"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(vf6-som)"
