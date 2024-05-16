#!/bin/bash

docker run -d -it \
  --name meetup-elasticsearch \
  -p 9200:9200 \
  -p 9300:9300 \
  -e "discovery.type=single-node" \
  -v /Users/david.sapo/Development/docker/elasticsearch-meetup:/usr/share/elasticsearch/data \
  elasticsearch:7.10.1
