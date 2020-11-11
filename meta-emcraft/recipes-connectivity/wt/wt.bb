DESCRIPTION = "A C++ Web Toolkit"
PRIORITY = "optional"
SECTION = "devel"
LICENSE = "GPL"
HOMEPAGE = "http://www.webtoolkit.eu/wt"
DEPENDS = "boost zlib openssl"

LIC_FILES_CHKSUM = "file://LICENSE;md5=6edc791d513e1627ab7b1e0d47769dc2"

SRC_URI = "git://github.com/emweb/wt.git;protocol=https;branch=master"

# tag 4.4.0
SRCREV= "9c6b7807b4fdaefe659ff9ab8ff4ba3937e62b2f"

S = "${WORKDIR}/git"

inherit cmake

FILES_${PN} += "/usr/share/Wt/resources/*"

PACKAGES =+ "${PN}-info"
