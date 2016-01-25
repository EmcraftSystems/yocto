COMPATIBLE_MACHINE = "(vf6-som)"

do_install_append () {
	ln -sf libmcc.so.1.0 ${D}/usr/lib/libmcc.so
}
