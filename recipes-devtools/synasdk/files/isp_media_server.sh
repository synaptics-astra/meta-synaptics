#!/bin/sh


NAME=isp_media_server
PATH=/sbin:/bin:/usr/sbin:/usr/bin
DESC="ISP Media Server"

DAEMON=/usr/bin/$NAME

mkdir -p /run/$NAME
RUN=/run/$NAME


PIDFILE="$RUN/$NAME.pid"

test -f $DAEMON || exit 0

SSD_OPTIONS="--oknodo --quiet --background --make-pidfile --pidfile $PIDFILE  --exec $DAEMON"
SSD_KILL="--signal KILL --retry 5"

set -e

case $1 in
    start)
        echo "sensor=imx258 xml=/usr/share/IMX258.xml manu_json=/usr/share/ISP_Manual.json \
        auto_json=/usr/share/ISP_Auto.json i2c_bus_id=3 mipi_id=0 mode=0" > /proc/vsi/isp_subdev0
        echo -n "Starting $DESC: "
        start-stop-daemon --start $SSD_OPTIONS
        echo "${DAEMON##*/}."
        ;;
    stop)
        echo -n "Stopping $DESC: "
        start-stop-daemon  --stop $SSD_OPTIONS $SSD_KILL
        echo "${DAEMON##*/}."
        ;;
    restart|force-reload)
        $0 stop
        sleep 1
        $0 start
        exit 0
        ;;
    status)
        status ${DAEMON} || exit $?
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|force-reload|status}" >&2
        exit 1
        ;;
esac

exit 0
