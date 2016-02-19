include u-boot-emcraft.bb

PROVIDES = "u-boot-emcraft-manufacture"

do_add_manufacture_options () {
	cfg_file="${S}/include/configs/${@d.getVar('UBOOT_MACHINE').replace('_config', '.h')}"
	echo "#define CONFIG_CMD_SET_BOOT_MEDIA" >> ${cfg_file}
	echo "#define CONFIG_BOOT_MEDIA_NAND" >> ${cfg_file}
}
addtask add_manufacture_options after do_patch before do_compile

do_deploy () {
	cp ${S}/${UBOOT_BINARY} ${DEPLOYDIR}/u-boot-manufacture-${PRJNAME}-${PV}-${PR}.nand
	dd if=${S}/${UBOOT_BINARY} of=${DEPLOYDIR}/u-boot-manufacture-${PRJNAME}-${PV}-${PR}.ram bs=1k skip=16
	cd ${DEPLOYDIR}
	ln -sf u-boot-manufacture-${PRJNAME}-${PV}-${PR}.ram u-boot-manufacture-${PRJNAME}.ram
	ln -sf u-boot-manufacture-${PRJNAME}-${PV}-${PR}.nand u-boot-manufacture-${PRJNAME}.nand
}

