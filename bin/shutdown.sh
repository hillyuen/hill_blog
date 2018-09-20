#!/bin/sh
if [ -s pid ] ; then
    kill `cat pid`
    rm -f pid
    rm -r nohup.out
fi
