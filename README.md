# RushHour

When one of my kids got the logic game [Rush Hour&reg;](https://www.thinkfun.com/products/rush-hour/) in birthday present I thought it would be a fun afternoon 
trying to make a program to solve the different puzzles.

Initial approach is to simply represent the board as a 36 character array (each piece is represented by different letters)
and find a shortest path using [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm).

Future work could be to try to find all shortest solutions to a given state. Probably need some kind of dynamic to find solvable / unsolvable states
in order to prune the search tree. The current breadth first is too greedy memory wise for my machine. 