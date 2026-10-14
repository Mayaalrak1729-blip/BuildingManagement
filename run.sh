#!/bin/sh
# Run the JavaFX version.  Easiest way (needs Maven + internet once):  mvn javafx:run
# Without Maven: download the JavaFX SDK from https://openjfx.io and set PATH_TO_FX, e.g.
#   PATH_TO_FX=/opt/javafx-sdk-21/lib ./run.sh
if [ -z "$PATH_TO_FX" ]; then
  echo "Set PATH_TO_FX to the lib folder of your JavaFX SDK, or use: mvn javafx:run"; exit 1
fi
rm -rf out && mkdir out
javac -d out --module-path "$PATH_TO_FX" --add-modules javafx.controls $(find src -name '*.java') || exit 1
cp -r resources/gui/. out/gui/
java --module-path "$PATH_TO_FX" --add-modules javafx.controls -cp out gui.Launcher
