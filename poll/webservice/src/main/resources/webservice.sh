#!/bin/bash

APPNAME=${project.artifactId}
JARFILE=${project.build.finalName}.one-jar.jar
PID=""

start() {
      echo "Starting ${APPNAME}: "
      exec java -jar ${JARFILE} > ${APPNAME}.log 2>&1 &
      PID=$!
      touch ${APPNAME}.pid
      echo ${PID} > ${APPNAME}.pid
      echo "started (pid: ${PID})"
}
stop() {
      echo "Shutting down ${APPNAME}: "
      if [[ -f ${APPNAME}.pid && -s ${APPNAME}.pid ]] ; then
        PID=`cat ${APPNAME}.pid`
        kill ${PID}
        echo "stopped (pid: ${PID})"
      else
        echo "PID file not found or empty!"
      fi ;
      rm ${APPNAME}.pid
}

# See how we were called
case "$1" in
start)
      start
      ;;
stop)
      stop
      ;;
restart)
      stop
      sleep 10
      start
      ;;
*)
      echo "Usage: $0 {start|stop|restart}"
esac

