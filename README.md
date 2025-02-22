# IQ Puzzler Pro Solver

This program solves the **IQ Puzzler Pro** puzzle using a **brute force algorithm**. It attempts to place blocks on a board and find a solution where all spaces are filled.

## Features
- Reads input from a text file containing board dimensions and block shapes.
- Implements a brute-force backtracking algorithm to find a solution.
- Displays the board with ANSI color-coded block representations.
- Saves the solution to a text file and an image file.

## Prerequisites
- Java 8 or higher

## How to Run
1. Simply run the program:
   ```sh
   java -jar bin/BruteforceIQ.jar
   ```
2. Follow the prompts to enter the path to your input file.

## Input File Format
The input file must follow this structure:
```
N M P
MODE
BOARD DATA (for CUSTOM mode)
BLOCK DATA
```
- `N M P` are the board dimensions and the number of blocks.
- `MODE` can be `DEFAULT`, `CUSTOM`, or `PYRAMID`.
- `BOARD DATA` is required only for `CUSTOM` mode.
- `BLOCK DATA` contains the representations of the blocks.

## Example Input File
```
3 3 3
DEFAULT
A  
AA
A
 B
BB
C
C
```

## Output
- If a solution is found, the program prints the completed board and asks if you want to save it.
- If saved, the solution will be stored in:
    - A text file: `filename_solution.txt`
    - An image file: `filename_solution.png`

## Author
Created by Darrel Adinarya Sunanda `13523061`

## License
This project is licensed under the MIT License.
