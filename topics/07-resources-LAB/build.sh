#!/usr/bin/env bash

REGISTRY=localhost:5000
APPLICATION_NAME=resources
IMAGE_NAME="$REGISTRY/$APPLICATION_NAME"

echo "building jar"
../../gradlew clean build

echo "creating docker image $IMAGE_NAME"
docker build -t $IMAGE_NAME .

echo "pushing docker image $IMAGE_NAME"
docker push $IMAGE_NAME








