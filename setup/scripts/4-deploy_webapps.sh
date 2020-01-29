#!/bin/sh

. ./app.conf

echo "Deploying webapps to $DEPLOY_DIR/webapps ..."

if [ -d "$REPO_DIR/app/webapps" ]; then
  [ ! -d "$DEPLOY_DIR/webapps" ] && mkdir "$DEPLOY_DIR/webapps"
  rm -rf "${DEPLOY_DIR:?}"/webapps/*
  cp -pR "$REPO_DIR"/app/webapps/* "$DEPLOY_DIR/webapps"
fi