require xbmc.inc
SRC_URI = "git://github.com/xbmc/xbmc.git;rev=${SRCREV};branch=Helix \
	file://rss.patch \
"
SRCREV = "90a75f0adfa458f7c60b575d667d927d369217b4"
PR = "${INC_PR}.1"