#!/bin/bash

CONTAINER_NAME="betclic-test-mongo"

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "MongoDB est déjà en cours d'exécution."
else
    echo "Lancement de MongoDB sur Docker..."
    docker run --rm -d --name $CONTAINER_NAME -p 27017:27017 mongo
fi

sleep 5

echo "Lancement de l'application..."
java -jar build/libs/api-all.jar