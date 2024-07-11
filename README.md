
Welcome to Zork, a text based adventure, this is a spin off of the very popular video game serires from the 1970s. 

## Zork Dungeons you can play: Bloodborne.zork , SnackLand.zork , infiltration.zork , Westeros.zork  
## These zork dungeons have walkthrough.txt files you can use if you get lost

## Features
- Your goal is to win the game, traverse rooms in order to do so. Different dungeons have different win conditions.
- You are able to save the game by typing in `save {$yourFileName}`
- You can interact with items, pick them up, examine, whatever. 
  - Interacting with items causes events to happen, such as teleporting, gaining points, losing health, etc.

### Example gameplay
```bash
Welcome to Westeros!

Winterfell
You stand within a huge castle complex spanning several acres, consisting of
two massive walls and a village located just outside. Banners featuring the
head of a direwolf are flapping in the chilly breeze.

There is a dagger here.

You can go s to North Kingsroad.
> 
```
## Running Locally and playing Dang's game
  - clone the repo
  - then run these scripts:
 ```bash
   cd ~/240-fall2023-team5/src
   javac *.java
   java Interpreter ../files/SnackLand.zork 
   ```

### Alternatively if you wanted to play a different file
```bash
   cd ~/240-fall2023-team5/src
   javac *.java
   java Interpreter ../files/(your_file_here).(zork/sav)
   ```

### Useful commands in ~/.bashrc
```bash
alias gozork="cd ~/240-fall2023-team5/src ; pwd"
alias runzork="javac *.java && java Interpreter"
```
