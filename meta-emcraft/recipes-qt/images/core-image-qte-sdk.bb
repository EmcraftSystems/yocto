IMAGE_FEATURES += "tools-debug \
			tools-sdk dev-pkgs package-management splash \
			ssh-server-openssh"

CORE_IMAGE_EXTRA_INSTALL = "packagegroup-qte-toolchain-target qt4-embedded-tools \
			qt4-embedded-demos qt4-embedded-examples \
			qt4-embedded-plugin-mousedriver-tslib \
			tslib tslib-calibrate tslib-tests qt-demo-init \
			packagegroup-base-nfs				\
			ethtool						\
			"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += "\
	      libstdc++ \
	      libpng12 \
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
		x264 \
		mplayer \
		strace \
		gdbserver \
		spidev-test \
		maliit-framework \
		maliit-plugins \
		libqt-embeddedcore4 \
		libqt-embeddedgui4 \
		libqt-embeddednetwork4 \
		qt4-embedded-qml-plugins \
		pincharea \
		experimental-gestures \
		qt4-embedded-fonts \
		qt4-embedded-fonts-qpf \
		qt4-embedded-fonts-pfa \
		qt4-embedded-fonts-pfb \
		qt4-embedded-fonts-qpf \
		qt4-embedded-fonts-ttf-dejavu \
		qt4-embedded-fonts-ttf-vera \
		qt4-embedded-plugin-mousedriver-tslib \
		qt4-embedded-plugin-imageformat-jpeg \
		qt4-embedded-plugin-gfxdriver-gfxtransformed	\
		qt4serialport \
		libqt-embeddeddeclarative4 \
		libqt-embeddedxmlpatterns4 \
		libqt-embeddedscript4 \
		libqt-embeddedsql4 \
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
	      log4cxx		\
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
	      lcdtest			\
	      fuse			\
	      libmtp			\
	      simple-mtpfs		\
	      "

export IMAGE_BASENAME = "core-image-qte-sdk"
