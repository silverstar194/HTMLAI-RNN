#!/bin/bash
         COUNTER=2250
         while [  $COUNTER -lt 2510 ]; do
             ls -t | tail -$COUNTER | head -10 | xargs -n 1 open
             sleep 9
             killall 'Google Chrome Helper'
             let COUNTER=COUNTER+10
         done
