DESCRIPTION = "A C++ Web Toolkit"
PRIORITY = "optional"
SECTION = "devel"
LICENSE = "GPL"
HOMEPAGE = "http://www.webtoolkit.eu/wt"
DEPENDS = "boost zlib openssl"

LIC_FILES_CHKSUM = "file://LICENSE;md5=6edc791d513e1627ab7b1e0d47769dc2"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=35b7527e053cc3586c91a877183dacad"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=6565235fddf41c29afe574c96e32358c"

#FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/emweb/wt.git;protocol=git;branch=master"

# tag 4.4.0
SRCREV = "9c6b7807b4fdaefe659ff9ab8ff4ba3937e62b2f"

# tag 3.3.8
#SRCREV = "9cce1a64adfa2cbeff9540cd5f9a88c0d098a204"
#SRCREV = "45db7588c2e9b8b472f1e193fe1d2ce0a1bfc334"
#SRCREV = "54f546cb3134439f50b43c3a276a0410238eff89"

#EXTRA_OECMAKE += "-DWT_NO_BOOST_RANDOM=ONWt"

S = "${WORKDIR}/git"

inherit cmake

FILES_${PN} += "/usr/share/Wt/resources/*"

PACKAGES =+ "${PN}-info"
