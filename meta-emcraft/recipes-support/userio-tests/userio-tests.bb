DESCRIPTION = "User IO test apps"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${S}/fui.c;beginline=4;endline=15;md5=b5a59150a33658cc1ffc31b1a4ffb9f2 \
		    file://${S}/i2c-example.c;beginline=4;endline=15;md5=b5a59150a33658cc1ffc31b1a4ffb9f2 \
		    file://${S}/test_gpio_irq.c;beginline=4;endline=15;md5=b5a59150a33658cc1ffc31b1a4ffb9f2 \
		    file://${S}/uinput-test.c;beginline=4;endline=15;md5=b5a59150a33658cc1ffc31b1a4ffb9f2"

#INSANE_SKIP_${PN} = "already-stripped"

PR = "r0"

SRC_URI = "file://userio-tests/uinput-test.c \
	   file://userio-tests/i2c-dev.h \
	   file://userio-tests/i2c-example.c \
	   file://userio-tests/test_gpio_irq.c \
	   file://userio-tests/rfid-example.sh \
	   file://userio-tests/Makefile \
	   file://userio-tests/fui.c \
	   file://userio-tests/rtctest.c"

S = "${WORKDIR}/userio-tests"

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/test_gpio_irq ${D}${bindir}/
	install -m 0755 ${S}/i2c-example ${D}${bindir}/
	install -m 0755 ${S}/rtctest ${D}${bindir}/
	install -m 0755 ${S}/fui ${D}${bindir}/
	install -m 0755 ${S}/uinput-test ${D}${bindir}/
}

