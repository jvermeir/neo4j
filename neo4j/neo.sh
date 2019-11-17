#!/bin/bash

docker run \
    --publish=7474:7474 --publish=7687:7687 \
    --volume=$HOME/dev/neo4j/data:/data \
    --volume=$HOME/dev/neo4j/logs:/logs \
    neo4j:3.5