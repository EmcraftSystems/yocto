SUMMARY = "QtQuick sample"
DESCRIPTION = "A sample recipe of a Qt application"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/main.cpp;beginline=8;endline=37;md5=5e3e594c4495be589c89f499487df3a9"

inherit qt4e

PR = "r0"

SRC_URI = "file://qtquick/dynamiclist.png \
	   file://qtquick/main.cpp \
	   file://qtquick/qml/dynamic/highlightranges.qml \
	   file://qtquick/qml/dynamic/highlight.qml \
	   file://qtquick/qml/dynamic/dynamiclist.qml \
	   file://qtquick/qml/dynamic/expandingdelegates.qml \
	   file://qtquick/qml/dynamic/sections.qml \
	   file://qtquick/qml/dynamic/content/RecipesModel.qml \
	   file://qtquick/qml/dynamic/content/TextButton.qml \
	   file://qtquick/qml/dynamic/content/PetsModel.qml \
	   file://qtquick/qml/dynamic/content/pics/hamburger.jpg \
	   file://qtquick/qml/dynamic/content/pics/arrow-down.png \
	   file://qtquick/qml/dynamic/content/pics/list-delete.png \
	   file://qtquick/qml/dynamic/content/pics/arrow-up.png \
	   file://qtquick/qml/dynamic/content/pics/pancakes.jpg \
	   file://qtquick/qml/dynamic/content/pics/moreUp.png \
	   file://qtquick/qml/dynamic/content/pics/minus-sign.png \
	   file://qtquick/qml/dynamic/content/pics/plus-sign.png \
	   file://qtquick/qml/dynamic/content/pics/fruit-salad.jpg \
	   file://qtquick/qml/dynamic/content/pics/lemonade.jpg \
	   file://qtquick/qml/dynamic/content/pics/moreDown.png \
	   file://qtquick/qml/dynamic/content/pics/vegetable-soup.jpg \
	   file://qtquick/qml/dynamic/content/PressAndHoldButton.qml \
	   file://qtquick/qmlapplicationviewer/qmlapplicationviewer.pri \
	   file://qtquick/qmlapplicationviewer/qmlapplicationviewer.cpp \
	   file://qtquick/qmlapplicationviewer/qmlapplicationviewer.h \
	   file://qtquick/dynamiclist.svg \
	   file://qtquick/dynamiclist.pro \
	   file://qtquick/dynamiclist.qmlproject \
	   file://start_qtquick \
	   file://start_qtquick.mouse \
	   file://start_qtquick.touch"

S = "${WORKDIR}/qtquick"

#do_compile() {
#}

do_install() {
	install -d ${D}/qtquick
	install ${WORKDIR}/qtquick/dynamiclist ${D}/qtquick
	cp -a ${S}/qml ${D}/qtquick
	install ${WORKDIR}/start_qtquick ${D}/
	install ${WORKDIR}/start_qtquick.mouse ${D}/
	install ${WORKDIR}/start_qtquick.touch ${D}/
}

FILES_${PN} = "/qtquick/* /qtquick/qml/* /start*"
FILES_${PN}-dbg += "/qtquick/.debug/* /qtquick/qml/.debug/*"
