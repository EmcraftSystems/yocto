SUMMARY = "Apache Thrift"
DESCRIPTION = "The Apache Thrift software framework, for scalable cross-language services development, combines a software stack with a code generation engine to build services that work efficiently and seamlessly between C++, Java, Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, OCaml and Delphi and other languages."
HOMEPAGE = "https://thrift.apache.org"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e4ed21f679b2aafef26eac82ab0c2cbf"

DEPENDS = "thrift-native boost openssl"

SRCREV = "0.9.3"

PV = "git${SRCPV}"
PR = "r0"

SRC_URI = "git://github.com/apache/thrift.git;branch=0.9.3;protocol=git \
	file://thrift-0.9.3-fix-native-thrift-paths.patch"
S = "${WORKDIR}/git"

# Thrift does not build with the only javac (EJC) available in Yocto, so use the host ant and javac
ANT = "/usr/bin/ant"

EXTRA_OECONF = "--disable-Werror --without-tests \
	     --without-zlib --without-libevent --without-ruby \
	     --without-python ANT=${ANT}"

LDFLAGS_append = " -lssl"

inherit autotools pkgconfig gettext

do_configure() {
	./bootstrap.sh
	oe_runconf
}

do_compile() {
	oe_runmake -C lib/c_glib
	oe_runmake -C tutorial/c_glib
	oe_runmake -C lib/cpp
	oe_runmake -C tutorial/cpp
	oe_runmake -C lib/java
	oe_runmake -C tutorial/java tutorial
}

do_install() {
	oe_runmake -C lib/c_glib install DESTDIR=${D}
	oe_runmake -C lib/cpp install DESTDIR=${D}
	#(cd lib/java; ant install -Dinstall.path=${D}/usr/local/lib -Dinstall.javadoc.path=${D}${docdir}/java)
	install -d ${D}/usr/local/lib
	cp -a ${S}/lib/java/build/libthrift-${SRCREV}.jar ${D}/usr/local/lib
	cp -a ${S}/lib/java/build/lib/* ${D}/usr/local/lib
	install -d ${D}/thrift
	install ${S}/tutorial/cpp/.libs/TutorialClient ${D}/thrift
	install ${S}/tutorial/cpp/.libs/TutorialServer ${D}/thrift
	install ${S}/tutorial/c_glib/.libs/tutorial_client ${D}/thrift
	install ${S}/tutorial/c_glib/.libs/tutorial_server ${D}/thrift
	install ${S}/tutorial/java/tutorial.jar ${D}/thrift
	install ${S}/lib/java/test/.keystore ${D}/thrift
	install ${S}/lib/java/test/.truststore ${D}/thrift
}

PACKAGES += "${PN}-javalibs ${PN}-tutorial ${PN}-tutorial-dbg"
FILES_${PN}-javalibs = "/usr/local/lib/*"
FILES_${PN}-tutorial = "/thrift/* /thrift/.keystore /thrift/.truststore"
FILES_${PN}-tutorial-dbg = "/thrift/.debug/*"
