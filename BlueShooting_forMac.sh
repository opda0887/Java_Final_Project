# move to the directory the bat exist
cd "$(dirname "$0")"

# create out folder (if not exist)
if [ ! -d "out" ]; then
  mkdir out
fi

# compile Game.java, and put all the .class into out folder
javac -encoding UTF-8 -d out Game.java
if [ $? -ne 0 ]; then
  echo "Compile error, please check the source code in this folder."
  exit 1
fi

# run the game
echo "Game running..."
java -cp out Game
if [ $? -ne 0 ]; then
  echo "Execution error."
  exit 1
fi
