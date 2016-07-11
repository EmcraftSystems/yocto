COMPATIBLE_MACHINE = "(vf6-som)"

SRC_URI_append = " file://001-kernel-module-mcc-deadlock-fix.patch \
	           file://002-fix-irq-num-kernel4.2.patch;striplevel=0 \
	           file://003-fix-cpu2cpu-irqs-disabling.patch \
		   file://004-fix-waking-up-from-mqx.patch"

DEPENDS_append = " linux-emcraft "
