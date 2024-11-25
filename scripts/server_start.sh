#!/usr/bin/env bash
echo "server start"
cd /home/elijah/server
sudo java -jar my-app.jar > /dev/null 2> /dev/null < /dev/null &