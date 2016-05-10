DESCRIPTION = "SPI testing utility (using spidev driver)"
LICENSE = "CLOSED"

SRC_URI = "file://${BP}.tar.bz2"

do_install() {
	install -d ${D}${bindir}
	install ${S}/spidev_test ${D}${bindir}
}
