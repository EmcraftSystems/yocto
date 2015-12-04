SUMMARY = "libcmsis"
DESCRIPTION = "The libcmsis library simplifies creating Linux application using the MCC communications"

LICENSE = "CLOSED"

DEPENDS = "libmcc"

PR = "r0"

SRC_URI = "file://mcc/libcmsis/include/cmsis_api.h \
	   file://mcc/libcmsis/include/arm_math.h \
	   file://mcc/libcmsis/Makefile \
	   file://mcc/libcmsis/.gitignore \
	   file://mcc/libcmsis/LICENSE \
	   file://mcc/libcmsis/src/libcmsis.h \
	   file://mcc/libcmsis/src/cmsis.c \
	   file://mcc/libcmsis/src/cmsis.h \
	   file://mcc/libcmsis/src/mem.c \
	   file://mcc/libcmsis/src/mcc.c \
	   file://mcc/Makefile \
	   "

S = "${WORKDIR}/mcc"

do_compile() {
	oe_runmake INSTALL_ROOT=${WORKDIR}
}

do_install() {
	install -d ${D}${base_libdir} ${D}${includedir}

	install -m 755 ${S}/libcmsis/build/libcmsis.so ${D}${base_libdir}
	install -m 644 ${S}/libcmsis/src/cmsis.h ${D}${includedir}
	install -m 644 ${S}/libcmsis/src/libcmsis.h ${D}${includedir}
	install -m 644 ${S}/libcmsis/include/cmsis_api.h ${D}${includedir}
	install -m 644 ${S}/libcmsis/include/arm_math.h ${D}${includedir}
}

FILES_${PN} = "/lib/*.so"
FILES_${PN}-dev = "/usr/include/*"

PACKAGE_ARCH = "${MACHINE_ARCH}"
