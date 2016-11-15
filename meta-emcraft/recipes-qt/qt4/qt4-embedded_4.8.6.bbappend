SRC_URI += "file://qt4_set_blank_cursor.patch \
	    file://qt4_no_blank_on_init.patch \
	    file://qt4_no_tslib_filtering.patch \
	    file://qt4_mt.patch \
	    file://qt4_fix_tearing.patch "

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
