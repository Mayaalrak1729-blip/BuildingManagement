@echo off
REM Run the JavaFX version.  Easiest way (needs Maven + internet once):  mvn javafx:run
REM Without Maven: download the JavaFX SDK from https://openjfx.io and set PATH_TO_FX, e.g.
REM   set PATH_TO_FX=C:\javafx-sdk-21\lib
if "%PATH_TO_FX%"=="" (
  echo Set PATH_TO_FX to the lib folder of your JavaFX SDK, or use: mvn javafx:run
  exit /b 1
)
if exist out rmdir /s /q out
mkdir out
dir /s /b src\*.java > sources.txt
javac -d out --module-path "%PATH_TO_FX%" --add-modules javafx.controls @sources.txt || exit /b 1
del sources.txt
xcopy /e /i /y resources\gui out\gui >nul
java --module-path "%PATH_TO_FX%" --add-modules javafx.controls -cp out gui.Launcher
