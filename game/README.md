All source files are located in ```game/core/src/main/java/io/github/archessmn/ENG1```.

# Build instructions

To build the jar file, clone the project and then in command prompt:
```
cd ProjectDir/game
gradlew build
```
The build will be located in ```ProjectDir/game/lwjgl3/build/libs/ProjectName.jar```.


# Run instructions

## Using CMD
To run game, clone the project and then in command prompt:
```
cd ProjectDir/game
gradlew lwjgl3:run
```
This should run the game.

## Using VSCode

If you are using VSCode, when you click open the ```ProjectDir/game``` folder and there should be an elephant symbol on the left bar. Click on it and navigate to ```lwjgl3/tasks/application``` and there is a run command which should run the game.


# Test instructions

## Using CMD
To test the game and get a test report, clone the project then in command prompt:
```
cd ProjectDir/game
gradlew jacocoTestReport
```
The test report will be located in ```game/headless/build/reports/jacoco/test/html/index.html```.
Just open the index.html file and the entire test report should be shown.
