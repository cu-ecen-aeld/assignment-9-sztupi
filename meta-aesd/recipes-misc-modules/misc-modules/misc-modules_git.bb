LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-sztupi;protocol=https;branch=main \
           file://0001-patch-Makefile.patch \
           file://module_load \
           file://module_unload \
           file://misc-modules-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "8a3a4ec21e420feb4353912ee90cf5e5eec055bc"

S = "${WORKDIR}/git"


inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-start-stop"

FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"
FILES:${PN} += "${sysconfdir}/init.d/misc-modules-start-stop"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0775 ${WORKDIR}/module_load ${D}${bindir}/
    install -m 0775 ${WORKDIR}/module_unload ${D}${bindir}/

    install -d ${D}${sysconfdir}/init.d
    install -m 0775 ${WORKDIR}/misc-modules-start-stop ${D}${sysconfdir}/init.d
}
