#!/bin/bash
if ! git diff --quiet; then
    echo "You have uncommited changes, please commit them before running deploy."
    exit 1
fi

lein t
lein lint
lein jar

echo "Deploying to Clojars"
lein deploy clojars
