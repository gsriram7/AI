import java.io.*;

public class homework {
    public static void main(String[] args) throws IOException {
        File source = new File("/Users/selvaram/selva/AI/src/main/resources/input.txt");
        File dest = new File("/Users/selvaram/selva/AI/src/main/resources/output.txt");

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

        BufferedWriter out = new BufferedWriter(new FileWriter(dest));
        if (!doesSolExist(dimension, lizards, tree)) {
            out.write("FAIL");
            out.close();
            System.exit(0);
        }
        if ("SA".equals(algo)) {
            SimulatedAnnealing sa = new SimulatedAnnealing(board, lizards);
            boolean isScuccessful = sa.simulate(1000, 0.98);
            if (isScuccessful) {
                out.write("OK\n");
                out.write(sa.currentState.toString());
            }
            else {
                out.write("FAIL");
            }
        }
        else {
            Assignment dfs = new Assignment(board, lizards);
            if (dfs.startDFS()) {
                out.write("OK\n");
                out.write(dfs.game.toString());
            }
            else {
                out.write("FAIL");
            }
        }

        out.close();
    }

    private static boolean doesSolExist(int dimension, int lizards, int trees) {
        if (dimension < 3) return lizards < dimension;
        if (dimension == 3 && trees == 0) return lizards < 3;
        return lizards <= (dimension + trees);
    }
}
