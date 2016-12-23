COMPATIBLE_MACHINE = "(vf6-som)"

SRC_URI_append = " file://kernel-module-mcc-deadlock-fix.patch "

DEPENDS_append = " linux-emcraft "
