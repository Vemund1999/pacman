# pacman

![pacman](https://github.com/Vemund1999/pacman/assets/88531005/c66335ee-d5ca-4426-aefc-9165208a0804)


The project is made in java.
It's a classic pacman game.
The user plays as pacman.
The ghosts try to hunt pacman down.
If pacman manages to eat all the food on the board, pacman wins.
If the ghosts manage to catch pacman before that, the ghosts win.


## How to run project
Run the file:
```
pacman\desktop\src\com\mygdx\game\Main.java
```
## Where is the code located
The code for the project is mainly located at
```
pacman\core\src\com\mygdx\game
```
![image](https://github.com/Vemund1999/pacman/assets/88531005/22c8e3ad-f15d-447b-876f-f1e233e47d33)


## Features
The user can control pacman's movment on the board.
The ghosts will find the shortest path towards pacman.
Pacman can eat food pieces laid out on the board.
Pacman can also eat cherries, which will make pacman able to temporarily eat the ghosts.
If a ghost is eaten, it's temporarily timed-out.

## About the code
### Pacman's movment
Pacman moves along a node-tree.

### The ghosts' movment
The ghosts uses dijkstras algorithm along the node-tree to find the shortest path to pacman
When pacman eats a cherry, he will be able to eat the ghosts. The ghosts will run away from pacman to avoid being eaten.
When the ghosts are running away, the node they are running to is the node farthest that's away from pacman.

### Placement of food
The foods are placed in a hashlist, where each foodpiece is accessed by a x and y coordinate.
When pacman's coordinate is the same as some foodpiece, the index for that foodpiece is accessed, and after that removed from the board.

### Design patterns
Singleton is used for the game-window, the hashlist for the foodpieces, and the nodetree.
These datastructures are regularly refrenced, so it's useful to have them be singletons.

Observer pattern is used for when it needs to be communicated to the ghosts that pacman has eaten a cherry.








