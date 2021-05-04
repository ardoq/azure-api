#!/bin/bash
if ! [[ $1 =~ ^(major|minor|patch)$ ]]; then
    echo "Valid arguments are major|minor|patch"
    exit 1
fi
if ! git diff --quiet; then
    echo "You have uncommited changes, please commit them before running release."
    exit 1
fi

echo "Generating pom file"


lein ver bump ":$1"
VERSION=`head -n 1 project.clj | awk '{gsub(/"/, "", $3); print $3}'`

lein pom

git add pom.xml


git commit -am "Release $VERSION

[ci deploy]"
git tag "v$VERSION"
echo "New release $VERSION ready to be pushed"
