package src;
import java.io.*;

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

                // TODO: Parse the puzzle blocks

                fileReader.close();
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