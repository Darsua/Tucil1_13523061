package src;
import java.io.*;
import java.util.*;

class Board {
    private final char[][] board;

    public Board(int N, int M) {
        this.board = new char[N][M];
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public void placeBlock(Block block, int x, int y) {
        for (int i = 0; i < block.height(); i++) {
            if (block.width() >= 0) System.arraycopy(block.block[i], 0, board[y + i], x, block.width());
        }
    }
}

class Block {
    public char[][] block;

    public Block(String[] parts) {
        int width = parts[0].length();
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].length() > width) {
                width = parts[i].length();
            }
        }

        int width1 = width;
        int height = parts.length;
        this.block = new char[height][width1];

        for (int i = 0; i < parts.length; i++) {
            for (int j = 0; j < parts[i].length(); j++) {
                block[i][j] = parts[i].charAt(j);
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
        char[][] temp = new char[width()][height()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                temp[j][height() - i - 1] = block[i][j];
            }
        }
        block = temp;
    }

    public void printBlock() {
        for (char[] row : block) {
            for (char c : row) {
                if (c == '\u0000') {
                    System.out.print(' ');
                } else {
                    System.out.print(c);
                }
            }
            System.out.println();
        }
    }
}

public class Main {

    @SuppressWarnings("BusyWait")
    private static void parseCase() throws InterruptedException {

        while (true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter the path of the txt file: ");
                String path = "test/test.txt"; System.out.println("test/test.txt"); // br.readLine();

                BufferedReader fileReader = new BufferedReader(new FileReader(path));

                // Read N, M, P, S
                String line = fileReader.readLine();

                String[] dimensions = line.split(" ");
                if (dimensions.length != 3) { // Check if the dimensions are valid
                    throw new IllegalArgumentException("Invalid dimensions '" + line + "'!");
                }

                int N = Integer.parseInt(dimensions[0]),
                        M = Integer.parseInt(dimensions[1]),
                        P = Integer.parseInt(dimensions[2]),
                        S;

                // Input validation for N, M, P
                if (P < 1) {
                    throw new IllegalArgumentException("Number of blocks cannot be less than 1! (" + P + ")!");
                } if (P > 26) {
                    throw new IllegalArgumentException("Number of blocks > 26! (" + P + ")!");
                } if (N < 1 || M < 1) {
                    throw new IllegalArgumentException("Invalid board dimensions (" + N + "x" + M + ")!");
                }

                // Read the case type
                line = fileReader.readLine();
                switch (line) {
                    case "DEFAULT" -> S = 0;
                    case "CUSTOM" -> S = 1;
                    case "PYRAMID" -> S = 2;
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
                                // for (int j = 0; j < 4; j++) {
                                //     tempBlock.printBlock();
                                //     tempBlock.rotate();
                                //     System.out.println();
                                // }
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
                Board board = new Board(N, M);

                board.placeBlock(blocks.get(4), 1, 1);
                board.placeBlock(blocks.get(1), 1, 1);
                // TODO: Placing two blocks on the same position overwrites the first block and empty spaces
                //       are considered as part of the block resulting in empty spaces also overwriting the first block

                board.printBoard();
                // END TEST BOARD

                return;

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