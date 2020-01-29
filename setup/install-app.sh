#!/bin/sh

. ./app.conf

echo "Installing application to $BASE_DIR ..."

if [ ! -d "$REPO_DIR" ]; then
  [ ! -d "$BUILD_DIR" ] && mkdir "$BUILD_DIR"
  cd "$BUILD_DIR" || exit
  git clone "$REPO_URL" "$APP_NAME"
else
  cd "$REPO_DIR" || exit
  git pull origin master
fi

# create directory structure
[ ! -d "$DEPLOY_DIR" ] && mkdir "$DEPLOY_DIR"
[ ! -d "$DEPLOY_DIR/bin" ] && mkdir "$DEPLOY_DIR/bin"
[ -d "$REPO_DIR/app/commands" ] && [ ! -d "$DEPLOY_DIR/commands" ] && mkdir "$DEPLOY_DIR/commands"
[ -d "$REPO_DIR/app/commands" ] && [ ! -d "$DEPLOY_DIR/commands/completed" ] && mkdir "$DEPLOY_DIR/commands/completed"
[ -d "$REPO_DIR/app/commands" ] && [ ! -d "$DEPLOY_DIR/commands/failed" ] && mkdir "$DEPLOY_DIR/commands/failed"
[ -d "$REPO_DIR/app/commands" ] && [ ! -d "$DEPLOY_DIR/commands/incoming" ] && mkdir "$DEPLOY_DIR/commands/incoming"
[ -d "$REPO_DIR/app/commands" ] && [ ! -d "$DEPLOY_DIR/commands/queued" ] && mkdir "$DEPLOY_DIR/commands/queued"
[ -d "$REPO_DIR/app/commands/sample" ] && [ ! -d "$DEPLOY_DIR/commands/sample" ] && mkdir "$DEPLOY_DIR/commands/sample"
[ ! -d "$DEPLOY_DIR/config" ] && mkdir "$DEPLOY_DIR/config"
[ ! -d "$DEPLOY_DIR/lib" ] && mkdir "$DEPLOY_DIR/lib"
[ ! -d "$DEPLOY_DIR/logs" ] && mkdir "$DEPLOY_DIR/logs"
[ ! -d "$DEPLOY_DIR/temp" ] && mkdir "$DEPLOY_DIR/temp"
[ ! -d "$DEPLOY_DIR/work" ] && mkdir "$DEPLOY_DIR/work"
[ -d "$REPO_DIR/app/webapps" ] && [ ! -d "$DEPLOY_DIR/webapps" ] && mkdir "$DEPLOY_DIR/webapps"

rm -rf "${DEPLOY_DIR:?}"/bin/*
[ -d "$REPO_DIR/app/bin" ] && cp -pR "$REPO_DIR"/app/bin/* "$DEPLOY_DIR/bin"

[ -d "$REPO_DIR/app/commands/sample" ] && rm -rf "${DEPLOY_DIR:?}"/commands/sample/*
[ -d "$REPO_DIR/app/commands/sample" ] && cp -pR "$REPO_DIR"/app/commands/sample/* "$DEPLOY_DIR/commands/sample"

cp "$REPO_DIR/setup/app.conf" "$BASE_DIR" || exit
cp "$REPO_DIR"/setup/scripts/*.sh "$BASE_DIR" || exit
chmod +x "$BASE_DIR"/*.sh
cp "$REPO_DIR/setup/install-service.sh" "$BASE_DIR/setup" || exit
cp "$REPO_DIR/setup/uninstall-service.sh" "$BASE_DIR/setup" || exit
chmod +x "$BASE_DIR"/setup/*.sh

echo "--------------------------------------------------------------------------"
echo "Your application installation is complete."
echo "To register this application as a service, run the following script:"
echo "  $BASE_DIR/setup/install-service.sh"
echo "You can also remove a registered service by running the following script:"
echo "  $BASE_DIR/setup/uninstall-service.sh"
echo "--------------------------------------------------------------------------"