# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-sztupi;protocol=ssh;branch=master \
           file://aesdchar_load \
           file://aesdchar_unload \
           file://aesdchar-start-stop"

PV = "1.0+git${SRCPV}"
SRCREV = "898bc9c304b7e41246dbdba154f5b5c7026c9a9e"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-start-stop"

FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"
FILES:${PN} += "${sysconfdir}/init.d/aesdchar-start-stop"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0775 ${WORKDIR}/aesdchar_load ${D}${bindir}/
    install -m 0775 ${WORKDIR}/aesdchar_unload ${D}${bindir}/

    install -d ${D}${sysconfdir}/init.d
    install -m 0775 ${WORKDIR}/aesdchar-start-stop ${D}${sysconfdir}/init.d
}
