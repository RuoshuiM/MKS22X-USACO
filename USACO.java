import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class USACO {
    public static int bronze(String filename) {

        try (BufferedReader inFile = new BufferedReader(new FileReader(filename))) {
            String[] specs = inFile.readLine().split(" ");
            final int ROW = Integer.parseInt(specs[0]);
            final int COL = Integer.parseInt(specs[1]);
            final int LAKE_ELEVATION = Integer.parseInt(specs[2]);
            final int NUM_INSTRUCTIONS = Integer.parseInt(specs[3]);
            int[][] pasture = new int[ROW][COL];

            for (int r = 0; r < ROW; r++) {
                String[] cells = inFile.readLine().split(" ");
                for (int c = 0; c < COL; c++) {
                    pasture[r][c] = Integer.parseInt(cells[c]);
                }
            }
            // pasture is made
//            printArrays(pasture);

            // stomping pasture
            for (int i = 0; i < NUM_INSTRUCTIONS; i++) {
                String[] cells = inFile.readLine().split(" ");
                int srow = Integer.parseInt(cells[0]) - 1;
                int scol = Integer.parseInt(cells[1]) - 1;
                int sdepth = Integer.parseInt(cells[2]);

                int maxDepth = 0;

                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        int depth = pasture[srow + j][scol + k];
                        maxDepth = Math.max(maxDepth, depth);
                    }
                }

                int newDepth = maxDepth - sdepth;

                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        pasture[srow + j][scol + k] = Math.min(newDepth, pasture[srow + j][scol + k]);
                    }
                }

//                printArrays(pasture);
            }

            int totalDepth = 0;

            for (int[] row : pasture) {
                for (int depth : row) {
                    totalDepth += Math.max(LAKE_ELEVATION - depth, 0);
                }
            }

            return totalDepth * 72 * 72;

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return -1;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    private final static int TREE = -1;

    public static int silver(String filename) {
        try (BufferedReader inFile = new BufferedReader(new FileReader(filename))) {
            String[] specs = inFile.readLine().split(" ");

            // ROWS and COLS: array-length, but starts at 0
            int ROWS = Integer.parseInt(specs[0]);
            int COLS = Integer.parseInt(specs[1]);
            int SECS = Integer.parseInt(specs[2]);

            int[][] pasture = new int[ROWS][COLS];

            for (int r = 0; r < ROWS; r++) {
                String[] cells = inFile.readLine().split("");
                for (int c = 0; c < COLS; c++) {
                    pasture[r][c] = cells[c].equals(".") ? 0 : TREE;
                }
            }

            String[] positions = inFile.readLine().split(" ");

            int startr = Integer.parseInt(positions[0]) - 1;
            int startc = Integer.parseInt(positions[1]) - 1;
            int endr = Integer.parseInt(positions[2]) - 1;
            int endc = Integer.parseInt(positions[3]) - 1;

            pasture[startr][startc] = 1;

            for (int step = 0; step < SECS; step++) {
                int[][] fPasture = new int[ROWS][COLS];
                for (int r = 0; r < ROWS; r++) {
                    for (int c = 0; c < COLS; c++) {

                        int cur = pasture[r][c];
                        if (cur > 0) {
                            modifyNeighbors(pasture, fPasture, r, c);
                        } else if (cur == TREE) {
                            fPasture[r][c] = TREE;
                        }
                    }
                }
                pasture = fPasture;

//                debugging
//                System.out.println(toString(pasture));
            }

            return pasture[endr][endc];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    private static void modifyNeighbors(int[][] pasture, int[][] fPasture, int r, int c) {
        for (int[] dir : directions) {
            int newR = r + dir[0];
            int newC = c + dir[1];
            try {
                if (pasture[newR][newC] != TREE) {
                    fPasture[newR][newC] += pasture[r][c];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
    }

    public static void printArrays(int[][] nums) {
        for (int[] row : nums) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @SuppressWarnings("unused")
    private static String toString(int[][] nums) {
        StringBuilder str = new StringBuilder();
        for (int[] row : nums) {
            for (int cell : row) {
                str.append(cell + " ");
            }
            str.append(System.lineSeparator());
        }
        return str.toString();
    }

    public static void main(String... args) {
 for (int i = 1; i <= 5; i++) {
          System.out.println(bronze(String.format("makelake.%d.in", i)));
      }
        // passed all cases

        for (int i = 1; i <= 5; i++) {
            System.out.println(silver(String.format("ctravel.%d.in", i)));
        }
        // passed all cases
    }
}
