# fix behavior of base do_install_prepend - its overwrite ready to use uImage by uncompresses Image
inherit kernel
require ../../../meta/recipes-kernel/linux/linux-dtb.inc

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"

LINUX_VERSION ?= "4.2"

RDEPENDS_kernel-base = ""

PR = "r1"

S = "${WORKDIR}/git"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRCREV = ""

SRC_URI = "${LINUX_GIT_REPO} \
	   file://${MACHINE}.dts \
	   file://defconfig"

KERNEL_EXTRA_ARGS += "LOADADDR=0x80008000"

do_subst_cfg() {
	cp -a ${WORKDIR}/${MACHINE}.dts ${WORKDIR}/git/arch/arm/boot/dts
}

addtask subst_cfg after do_unpack before do_patch

COMPATIBLE_MACHINE = "(vf6-som)"

# fix error in kernel.bbclass fails to create symlink due to searching the image in wrong path
kernel_do_deploy_append() {
	if [ -e "${DEPLOYDIR}/${initramfs_base_name}.bin" ]; then
		ln -sf ${initramfs_base_name}.bin ${initramfs_symlink_name}.bin
	fi
}
