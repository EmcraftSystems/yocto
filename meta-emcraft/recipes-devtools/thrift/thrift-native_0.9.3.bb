SUMMARY = "Apache Thrift (Native SDK binary)"
DESCRIPTION = "The Apache Thrift software framework, for scalable cross-language services development, combines a software stack with a code generation engine to build services that work efficiently and seamlessly between C++, Java, Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, OCaml and Delphi and other languages."
HOMEPAGE = "https://thrift.apache.org"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e4ed21f679b2aafef26eac82ab0c2cbf"

DEPENDS = "bison-native boost"

BBCLASSEXTEND = "nativesdk"

SRCREV = "0.9.3"

PV = "git${SRCPV}"
PR = "r0"

SRC_URI = "git://github.com/apache/thrift.git;branch=0.9.3;protocol=git"

EXTRA_OECONF = "--disable-Werror --without-tests \
	     --without-zlib --without-libevent --without-ruby \
	     --without-python"

S = "${WORKDIR}/git"

inherit autotools native gettext

do_configure() {
	./bootstrap.sh
	oe_runconf
}

do_compile() {
	oe_runmake -C compiler/cpp
}

do_install() {
	oe_runmake -C compiler/cpp install DESTDIR=${D}
}

