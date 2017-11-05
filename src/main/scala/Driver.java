import java.io.*;

public class Driver {

    public static void main(String[] args) throws IOException {
        String src = "/Users/selvaram/selva/AI/src/main/resources/input.txt";
        String dest = "/Users/selvaram/selva/AI/src/main/resources/output.txt";
        driver(new String[]{src, dest});
    }

    public static void driver(String[] args) throws IOException {
        File source = new File(args[0]);
        File dest = new File(args[1]);

        BufferedReader in = new BufferedReader(new FileReader(source));

        String algo = in.readLine();
        int dimension = Integer.parseInt(in.readLine());
        int lizards = Integer.parseInt(in.readLine());
        int tree = 0;
        Board board = new Board(dimension);

        for (int i = 0; i < dimension; i++) {
            String row = in.readLine();
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '2') {
                    tree++;
                    board.placeTreeIn(i, j);
                }
            }
        }

        in.close();
        Board copy = new Board(board);

        writeFailure(dest);
        if (!doesSolExist(dimension, lizards, tree)) {
            writeFailure(dest);
            System.exit(0);
        }
        if ("SA".equals(algo)) {
            SimulatedAnnealing sa = new SimulatedAnnealing(board, lizards);
            boolean isScuccessful = sa.simulate(1000, 0.98);
            if (isScuccessful) writeSuccess(dest, sa.currentState.toString());
            else writeFailure(dest);
        } else {
            Search dfs = new Search(board, lizards);
            if (tree == 0) dfs.isOptimized = true;
            if (dfs.startDFS()) {
                writeSuccess(dest, dfs.game.toString());
            } else {
                if (!dfs.isOptimized) {
                    if (!fallback(copy, lizards, dest))
                        writeFailure(dest);
                }
                else
                    writeFailure(dest);
            }
        }
    }

    private static void writeSuccess(File out, String result) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(out));
        output.write("OK\n");
        output.write(result);
        output.close();
    }

    private static void writeFailure(File out) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(out));
        output.write("FAIL");
        output.close();
    }

    private static boolean fallback(Board copy, int lizards, File dest) throws IOException {
        Search search = new Search(copy, lizards);
        search.isOptimized = true;
        search.timeOut = 20;
        boolean isComplete = false;
        try {
            isComplete = search.startDFS();
        } catch (Exception e) {
            isComplete = false;
        }

        if (isComplete) writeSuccess(dest, search.game.toString());
        return isComplete;
    }

    private static boolean doesSolExist(int dimension, int lizards, int trees) {
        if (lizards > (dimension * dimension - trees)) return false;
        if (dimension < 3) return lizards < dimension;
        if (dimension == 3 && trees == 0) return lizards < 3;
        return lizards <= (dimension + trees);
    }
}
