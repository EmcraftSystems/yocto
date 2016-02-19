FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = "file://busybox-handle-tty-hangups.patch \
           file://busybox-flash-eraseall-fix.patch \
	   file://defconfig \
	   file://stty_1mbit.patch \
	   "
