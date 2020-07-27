IMAGE_FEATURES += "tools-debug \
			tools-sdk dev-pkgs package-management splash \
			ssh-server-openssh"

# CORE_IMAGE_EXTRA_INSTALL = "packagegroup-qte-toolchain-target qt4-embedded-tools \
# 			qt4-embedded-demos qt4-embedded-examples \
# 			qt4-embedded-plugin-mousedriver-tslib \
# 			tslib tslib-calibrate tslib-tests qt-demo-init \
# 			packagegroup-base-nfs				\
# 			ethtool						\
#			"

CORE_IMAGE_EXTRA_INSTALL = "packagegroup-qt5-toolchain-target"

LICENSE = "MIT"

KERNELDEPMODDEPEND = ""

inherit core-image

DEPENDS_remove = "linux-imx"

# do_rootfs[noexec] = "1"
# do_image[noexec] = "1"
# do_image_ext4[noexec] = "1"
# do_image_sdcard[noexec] = "1"
# do_image_tar[noexec] = "1"

IMAGE_INSTALL += "\
	      libstdc++ \
	      libpng \
	      zlib \	      
	      icu			\
	      freetype \
		glib-2.0 \
		tslib \
		tslib-conf \
		tslib-calibrate \
		libcrypto \
		libffi \
		libasound \
		alsa-conf-base \
		alsa-utils-amixer \
		libcrypto \
		libusb1 \
		ncurses \
		ncurses-terminfo-base \
		udev \
		curl \
		libcurl \
		gnutls \
		libnl \
		libnl-genl \
		dbus-lib \
		libxml2 \
		bzip2 \
		uuid \
		tzdata \
		openssh-sshd \
		mtd-utils \
		mtd-utils-ubifs \
		openssh-sftp \
		openssh-sftp-server \
		openssh-scp \
		libudev \
		lzo \
		fbset \
		opkg \
		strace \
		gdbserver \
	      openssl-misc		\
	      openssl-engines		\
	      wireless-tools		\
	      wpa-supplicant		\
	      wpa-supplicant-cli	\
	      wpa-supplicant-passphrase	\
	      libnl			\
	      gst-meta-video \
	      gst-plugins-base \
	      gst-plugins-good \
	      gst-plugins-good-video4linux2 \
	      libgudev \
	      gst-plugins-bad \
	      gst-plugins-bad-fbdevsink \
	      gst-plugins-base \
	      gst-plugins-base-alsa \
	      gst-plugins-base-audioconvert \
	      gst-plugins-base-audiorate \
	      gst-plugins-base-audioresample \
	      gst-plugins-base-audiotestsrc \
	      gst-plugins-base-decodebin \
	      gst-plugins-base-decodebin2 \
	      gst-plugins-base-ffmpegcolorspace \
	      gst-plugins-base-playbin \
	      gst-plugins-base-typefindfunctions \
	      gst-plugins-base-volume \
	      gst-plugins-good \
	      gst-plugins-good-audiofx \
	      gst-plugins-good-audioparsers \
	      gst-plugins-good-auparse \
	      gst-plugins-good-autodetect \
	      gst-plugins-good-flac \
	      gst-plugins-good-id3demux \
	      gst-plugins-good-isomp4 \
	      gst-plugins-good-oss4audio \
	      gst-plugins-good-ossaudio \
	      gst-plugins-good-video4linux2 \
	      gst-plugins-good-wavenc \
	      gst-plugins-good-wavparse \
	      sysklogd			\
	      cronie			\
	      tzdata			\
	      tzdata-africa		\
	      tzdata-americas		\
	      tzdata-antarctica		\
	      tzdata-arctic		\
	      tzdata-asia		\
	      tzdata-atlantic		\
	      tzdata-australia		\
	      tzdata-europe		\
	      tzdata-misc		\
	      tzdata-pacific		\
	      strace			\
	      expect			\
	      minicom			\
	      i2c-tools			\
	      fuse			\
	      libmtp			\
	      simple-mtpfs		\
	      iptables			\
	      iproute2			\
	      can-utils			\
	      libsocketcan		\
	      python-django		\
	      nginx			\
	      gunicorn			\
	      gsl			\
	      libftdi			\
	      "

# require recipes-fsl/images/fsl-image-qt5-validation-imx.bb

inherit populate_sdk_qt5


# Install Freescale QT demo applications
QT5_IMAGE_INSTALL_APPS = ""
QT5_IMAGE_INSTALL_APPS_imxgpu3d = "${@bb.utils.contains("MACHINE_GSTREAMER_1_0_PLUGIN", "imx-gst1.0-plugin", "imx-qtapplications", "", d)}"

# Install fonts
QT5_FONTS = "ttf-dejavu-common ttf-dejavu-sans ttf-dejavu-sans-mono ttf-dejavu-serif "

# Install Freescale QT demo applications for X11 backend only
MACHINE_QT5_MULTIMEDIA_APPS = ""
QT5_IMAGE_INSTALL = ""
QT5_IMAGE_INSTALL_common = " \
    packagegroup-qt5-demos \
    ${QT5_FONTS} \
    ${QT5_IMAGE_INSTALL_APPS} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'libxkbcommon', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland qtwayland-plugins', '', d)}\
    "

QT5_IMAGE_INSTALL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'x11','${QT5_IMAGE_INSTALL_common}', \
    'qtbase qtbase-plugins', d)}"

QT5_IMAGE_INSTALL_imxpxp = "${@bb.utils.contains('DISTRO_FEATURES', 'x11','${QT5_IMAGE_INSTALL_common}', \
    'qtbase qtbase-examples qtbase-plugins', d)}"

QT5_IMAGE_INSTALL_imxgpu3d = " \
    ${QT5_IMAGE_INSTALL_common} \
    gstreamer1.0-plugins-good-qt"

# Add packagegroup-qt5-webengine to QT5_IMAGE_INSTALL_mx6 and comment out the line below to install qtwebengine to the rootfs.
QT5_IMAGE_INSTALL_remove = " packagegroup-qt5-webengine"

IMAGE_INSTALL += " \
${QT5_IMAGE_INSTALL} \
"

export IMAGE_BASENAME = "core-image-qte-sdk"
