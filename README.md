 <h1 align="center">The last survivors</h1>
 <br/>

## Setting up the production mode

1. Clone the repository
```bash
git clone https://github.com/polibuda-projects/the-last-survivors.git
```
2. Make sure you have `Java >=11` installed
3. Change access rights to the `gradlew` file
```bash
chmod +x ./gradlew
```
4. In the project directory run the following command
```bash
./gradlew desktop:dist
```
5. The executable file will be located in `desktop/build/libs/desktop-1.0.jar`
 - for Windows and Linux users
```bash
java -jar desktop/build/libs/desktop-1.0.jar
```
 - for macOS users
```bash
java -XstartOnFirstThread -jar desktop/build/libs/desktop-1.0.jar
```