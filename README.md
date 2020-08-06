# Minesweeper

## Description:
Remember the old Minesweeper ?
We play on a square board and we have to click on the board on the cells which do not have a mine. And obviously we don’t know where mines are. If a cell where a mine is present is clicked then we lose, else we are still in the game.


## Important facts:
1.	Beginner – 9 * 9 Board and 10 Mines
2.	Intermediate – 16 * 16 Board and 40 Mines
3.	Advanced – 24 * 24 Board and 99 Mines

## Signs
1.  `-` - init state of cell 
2.  `*` - mine
3.  `x` - cell is empty, there aren't any mines around

## Special requirement: 
Write the game in a way that uses several classes, good naming convention and documentation are also recommended.
Spring boot will be plus.

## Example 1:
```java
Input

0
1 2
2 3
3 4
4 5

Output

Enter the Difficulty Level
Press 0 for BEGINNER (9 * 9 Cells and 10 Mines)
Press 1 for INTERMEDIATE (16 * 16 Cells and 40 Mines)
Press 2 for ADVANCED (24 * 24 Cells and 99 Mines)
Current Status of Board : 
    0 1 2 3 4 5 6 7 8 

0   - - - - - - - - - 
1   - - - - - - - - - 
2   - - - - - - - - - 
3   - - - - - - - - - 
4   - - - - - - - - - 
5   - - - - - - - - - 
6   - - - - - - - - - 
7   - - - - - - - - - 
8   - - - - - - - - - 

Enter your move, (row, column) -> Current Status of Board : 
    0 1 2 3 4 5 6 7 8 

0   - - - - - - - - - 
1   - - 2 - - - - - - 
2   - - - - - - - - - 
3   - - - - - - - - - 
4   - - - - - - - - - 
5   - - - - - - - - - 
6   - - - - - - - - - 
7   - - - - - - - - - 
8   - - - - - - - - - 

Enter your move, (row, column) ->     
    0 1 2 3 4 5 6 7 8 

0   - - - - - - - * * 
1   - - 2 * - - - - - 
2   - - - * * - - - - 
3   - - - - - - - * - 
4   - - - - - - - - - 
5   - - - - - - - - - 
6   - - * - - - - - - 
7   - - - - * - - * - 
8   - * - - - - - - - 

You lost!
```