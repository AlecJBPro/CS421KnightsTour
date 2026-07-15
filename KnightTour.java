// user input for itnerface 
public final class KnightTour {
    private KnightTour() {
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            printUsage();
            return;
        }

        try {
            int strategy = Integer.parseInt(args[0]);
            int size = Integer.parseInt(args[1]);
            int startRow = Integer.parseInt(args[2]);
            int startColumn = Integer.parseInt(args[3]);

            KnightBoard board = new KnightBoard(size, strategy);
            boolean solved = board.solve(startRow, startColumn);
            System.out.println("The total number of moves is " + board.getMovesTried());
            if (solved) {
                printBoard(board);
            } else {
                System.out.println("No solution found!");
            }
        } catch (NumberFormatException exception) {
            System.err.println("All four arguments must be integers.");
            printUsage();
        } catch (IllegalArgumentException exception) {
            System.err.println("Error: " + exception.getMessage());
            printUsage();
        }
    }

    private static void printBoard(KnightBoard board) {
        int width = Integer.toString(board.getSize() * board.getSize()).length();
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                if (column > 0) {
                    System.out.print(" ");
                }
                System.out.printf("%" + width + "d", board.getMoveNumber(row, column));
            }
            System.out.println();
        }
    }

    private static void printUsage() {
        System.err.println("Usage: java KnightTour <0|1|2> <n> <x> <y>");
        System.err.println("  0 = basic, 1 = border heuristic, 2 = Warnsdorff heuristic");
    }
}
