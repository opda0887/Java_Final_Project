@echo off

REM move to the directory the bat exist
cd /d %~dp0

REM create out folder (if not exist)
if not exist out (
  mkdir out
)

REM compile Game.java, and put all the .class into out folder
javac -encoding UTF-8 -d out Game.java
if errorlevel 1 (
  echo Compile error, please check the source code in this folder.
  pause
  exit /b 1
)

REM run the game
echo Game running...
java -cp out Game
if errorlevel 1 (
  echo Execution error.
  pause
  exit /b 1
)
