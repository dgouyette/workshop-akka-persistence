#!/bin/bash

set -e
wd=$(pwd)

  node=$1
   sbt \
    -Dhttp.port=900$node -Dakka.remote.netty.tcp.port=255$node \
    -Dakka.actor.provider=akka.cluster.ClusterActorRefProvider \
    -Dakka.cluster.seed-nodes.0=akka.tcp://application@127.0.0.1:2551 \
    -Dakka.cluster.seed-nodes.1=akka.tcp://application@127.0.0.1:2552 \
    -Dakka.cluster.seed-nodes.2=akka.tcp://application@127.0.0.1:2553 \
    run