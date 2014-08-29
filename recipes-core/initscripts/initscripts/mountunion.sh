#!/bin/sh
### BEGIN INIT INFO
# Provides:          mountunion
# Required-Start:    $local_fs
# Required-Stop: 
# Default-Start:     S
# Default-Stop:
# Short-Description: Mount all filesystems.
# Description:
### END INIT INFO

. /etc/default/rcS

PATH=/sbin:/bin:/usr/sbin:/usr/bin

#
# Mount local filesystems in /etc/fstab. For some reason, people
# might want to mount "proc" several times, and mount -v complains
# about this. So we mount "proc" filesystems without -v.
#
test "$VERBOSE" != no && echo "Mounting overlay filesystems..."
test -f /etc/fstab && (

#
#	Read through fstab line by line and nount union file systems
#
cat /etc/fstab | sed -E 's/#.*//g' | while read device mountpt fstype options
do
	if test "$fstype" = unionfs
	then
		if [ "`mount | grep "$fstype" | grep "$mountpt" | wc -l`" -eq 0 ]
		then
			mount "$mountpt"
		fi
	fi
done
)


: exit 0
