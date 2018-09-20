#!/bin/sh
echo "start task on background"
nohup  java -Xms128m -Xmx512m -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=512M -cp "lib/*:conf/" com.hillyuen.Application %* &
echo "success started"
echo $! > pid
