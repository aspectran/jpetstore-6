#!/bin/sh

. ./app.conf

echo "Deploying web application to $DEPLOY_DIR/webroot ..."
if [ -d "$REPO_DIR/app/webroot" ]; then
  [ ! -d "$DEPLOY_DIR/webroot" ] && mkdir "$DEPLOY_DIR/webroot"
  rm -rf "${DEPLOY_DIR:?}"/webroot/*
  cp -pR "$REPO_DIR"/app/webroot/* "$DEPLOY_DIR/webroot"
fi

echo "Restore specific web application files after deployment ..."
[ -d "$RESTORE_DIR/webroot" ] && cp -pRf "$RESTORE_DIR"/webroot/* "$DEPLOY_DIR/webroot"