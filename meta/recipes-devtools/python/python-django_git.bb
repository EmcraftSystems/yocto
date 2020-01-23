DESCRIPTION = "A high-level Python Web framework"
HOMEPAGE = "http://www.djangoproject.com/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f09eb47206614a4954c51db8a94840fa"

SRCNAME = "Django"

PV = "1.11.28"
SRCREV = "7fd1ca3ef63e5e834205a8208f4dc17d80f9a417"

SRC_URI = " \
    git://github.com/django/django.git;branch=stable/1.11.x \
    "

S = "${WORKDIR}/git"

inherit setuptools

FILES_${PN} += "${datadir}/django/*"

