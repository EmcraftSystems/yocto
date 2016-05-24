require ../../../meta/recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb \
                    file://README;beginline=1;endline=22;md5=78b195c11cb6ef63e6985140db7d7bab"

PV = "${UBOOT_PV}"
PR = "r0"

inherit emcraft-utils

UBOOT_LOCALVERSION ?= "${@subst_git_rev("-${FT-VERSION}", "${UBOOT_SRCPV}")}"
UBOOT_IMAGE = "u-boot-${PRJNAME}-${PV}-${PR}.nand"
UBOOT_BINARY = "u-boot.nand"
UBOOT_SYMLINK = "u-boot-${PRJNAME}.nand"
UBOOT_MAKE_TARGET = "u-boot.nand"

SRC_URI = "${UBOOT_GIT_REPO}"

S = "${UBOOT_S}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(vf6-som)"
