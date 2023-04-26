#!/bin/bash

OS=""

if [[ "$(uname)" == "Darwin" ]]; then
    OS="macOS"
elif [[ "$(grep -Ei 'debian|buntu|mint' /etc/*-release)" ]]; then
    OS="Ubuntu"
else
    OS="Windows"
fi

chmod +x ./gradlew
./gradlew desktop:dist

case $OS in
    "macOS")
        java -XstartOnFirstThread -jar desktop/build/libs/desktop-1.0.jar
        ;;

    "Ubuntu")
        java -jar desktop/build/libs/desktop-1.0.jar
        ;;

    *)
        java -jar desktop/build/libs/desktop-1.0.jar
        ;;
esac
