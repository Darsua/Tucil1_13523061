package src;
import java.io.*;
import java.util.*;

class Cell {
    public List<Character> cell;

    public Cell() {
        this.cell = new ArrayList<>();
    }

    public void add(char c) {
        cell.add(c);
    }

    public void printCell() {
        for (char c : cell) {
            System.out.print(c);
        }
    }
}

class Board {
    private final Cell[][] board;

    public Board(int N, int M) {
        this.board = new Cell[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public void printBoard() {
        int cellWidth = getBoardHeight();

        for (Cell[] row : board) {
            for (Cell c : row) {
                if (c.cell.isEmpty()) {
                    System.out.print('_');
                    for (int i = 0; i < cellWidth; i++) {
                        System.out.print(' ');
                    }
                } else {
                    c.printCell();
                    for (int i = c.cell.size(); i <= cellWidth; i++) {
                        System.out.print(' ');
                    }
                }
            }
            System.out.println();
        }
    }

    public void printHeightMap() {
        for (Cell[] row : board) {
            for (Cell c : row) {
                System.out.print(c.cell.size());
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public void placeBlock(Block block, int x, int y) {
        for (int i = 0; i < block.height(); i++) {
            for (int j = 0; j < block.width(); j++) {
                if (block.block[i][j] == 1) {
                    board[x + i][y + j].add(block.letter);
                }
            }
        }
    }

    public int getBoardHeight() {
        int maxHeight = 0;
        for (Cell[] row : board) {
            for (Cell c : row) {
                if (c.cell.size() > maxHeight) {
                    maxHeight = c.cell.size();
                }
            }
        }
        return maxHeight;
    }

}

class Block {
    public int[][] block;
    public char letter;

    public Block(String[] parts) {
        this.letter = parts[0].charAt(0);
        int width = parts[0].length();
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].length() > width) {
                width = parts[i].length();
            }
        }

        int width1 = width;
        int height = parts.length;
        this.block = new int[height][width1];

        for (int i = 0; i < parts.length; i++) {
            for (int j = 0; j < parts[i].length(); j++) {
                if (parts[i].charAt(j) == ' ') {
                    block[i][j] = 0;
                } else {
                    block[i][j] = 1;
                }
            }
        }
    }

    public int width() {
        return block[0].length;
    }

    public int height() {
        return block.length;
    }

    public void rotate() {
        int[][] temp = new int[width()][height()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                temp[j][height() - i - 1] = block[i][j];
            }
        }
        block = temp;
    }

    public void printBlock() {
        for (int[] row : block) {
            for (int c : row) {
                if (c == 0) {
                    System.out.print(' ');
                } else {
                    System.out.print(letter);
                }
            }
            System.out.println();
        }
    }

}

public class Main {

    @SuppressWarnings("BusyWait")
    private static int[] parseCase() throws InterruptedException {

        while (true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter the path of the txt file: ");
                String path = "test/test.txt"; System.out.println("test/test.txt"); // br.readLine();

                BufferedReader fileReader = new BufferedReader(new FileReader(path));

                // Read N, M, P
                String line = fileReader.readLine();

                String[] dimensions = line.split(" ");
                if (dimensions.length != 3) { // Check if the dimensions are valid
                    throw new IllegalArgumentException("Invalid dimensions '" + line + "'!");
                }
                int[] parsedCase = new int[4];
                for (int i = 0; i < 3; i++) {
                    parsedCase[i] = Integer.parseInt(dimensions[i]);
                }

                // Read the case type
                line = fileReader.readLine();
                switch (line) {
                    case "DEFAULT" -> parsedCase[3] = 0;
                    case "CUSTOM" -> parsedCase[3] = 1;
                    case "PYRAMID" -> parsedCase[3] = 2;
                    default -> throw new IllegalArgumentException("Invalid case type '" + line + "'!");
                }

                // Read the blocks
                int blockCount = 0;
                List<String> parts = new ArrayList<>();
                List<Block> blocks = new ArrayList<>();

                while ((line = fileReader.readLine()) != null) {
                    // Iterate over every block character
                    for (int i = 0; i < line.length(); i++) {
                        // Check if the block part is valid
                        if ('A' + blockCount != line.charAt(i)) {
                            // Check if the block part is the start of a new block
                            if (line.charAt(i) == 'A' + blockCount + 1 && i == 0) {

                                // START TEST BLOCK
                                Block tempBlock = new Block(parts.toArray(new String[0]));
                                blocks.add(tempBlock);
//                                for (int j = 0; j < 4; j++) {
//                                    tempBlock.printBlock();
//                                    tempBlock.rotate();
//                                    System.out.println();
//                                }
                                // END TEST BLOCK

                                blockCount++;
                                parts.clear();
                            } else {
                                throw new IllegalArgumentException("Invalid block part '" + line + "'!");
                            }
                        }
                    }
                    // Add block line to the list
                    parts.add(line);
                }

                fileReader.close();

                // START TEST BOARD
                Board board = new Board(parsedCase[0], parsedCase[1]);

                board.placeBlock(blocks.get(4), 1, 1);
                Block secondBlock = blocks.get(4);
                secondBlock.rotate();
                board.placeBlock(secondBlock, 1, 1);
                board.placeBlock(blocks.get(1), 0, 0);

                board.printBoard();
                System.out.println();
                board.printHeightMap();

                // TODO: Placing two blocks on the same position overwrites the first block and empty spaces
                //       are considered as part of the block resulting in empty spaces also overwriting the first block

                // END TEST BOARD

                return parsedCase;

            }

            catch (IOException e) {
                System.err.println("\nThe system cannot find the file specified!");
                System.err.println("Please check the path and try again.");
            }
            catch (IllegalArgumentException e) {
                System.err.println("\n" + e.getMessage());
                System.err.println("Please check the file and try again.");
            }

            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.print(".\n\n");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("\n=== Penyelesaian IQ Puzzler Pro dengan Algoritma Brute Force ===\n");
        parseCase();

    }
}