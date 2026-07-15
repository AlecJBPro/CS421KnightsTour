import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// knights tour class 
public final class KnightBoard {
    // constants declarations
    public static final int BASIC = 0;
    public static final int BORDER_HEURISTIC = 1;
    public static final int WARNSDORFF_HEURISTIC = 2;

    // clockwise order beginning at top right
    private static final int[] ROW_CHANGE = {-2, -1, 1, 2, 2, 1, -1, -2};
    private static final int[] COLUMN_CHANGE = {1, 2, 2, 1, -1, -2, -2, -1};

    // instance variables
    private final int size;
    private final int strategy;
    private final int[][] board;
    private long movesTried;

    // constructor class 
    public KnightBoard(int size, int strategy) {
        if (size <= 2) {
            throw new IllegalArgumentException("Board size must be greater than 2.");
        }
        if (strategy < BASIC || strategy > WARNSDORFF_HEURISTIC) {
            throw new IllegalArgumentException("Search option must be 0, 1, or 2.");
        }
        this.size = size;
        this.strategy = strategy;
        this.board = new int[size][size];
    }

    // method to solve the knight's tour problem
    public boolean solve(int startRow, int startColumn) {
        if (!isOnBoard(startRow, startColumn)) {
            throw new IllegalArgumentException("Starting position is outside the board.");
        }

        clear();
        board[startRow][startColumn] = 1;
        movesTried = 1; // The initial placement is the first attempted move.
        return search(startRow, startColumn, 2);
    }

    // recursively search for a successful knight's tour
    private boolean search(int row, int column, int moveNumber) {
        if (moveNumber > size * size) {
            return true;
        }

        for (Position next : eligibleMoves(row, column)) {
            movesTried++;
            board[next.getRow()][next.getColumn()] = moveNumber;
            if (search(next.getRow(), next.getColumn(), moveNumber + 1)) {
                return true;
            }
            board[next.getRow()][next.getColumn()] = 0;
        }
        return false;
    }

    // get a list of eligible moves from a given position
    private List<Position> eligibleMoves(int row, int column) {
        List<Position> moves = new ArrayList<Position>(8);
        for (int i = 0; i < ROW_CHANGE.length; i++) {
            int nextRow = row + ROW_CHANGE[i];
            int nextColumn = column + COLUMN_CHANGE[i];
            if (isOnBoard(nextRow, nextColumn) && board[nextRow][nextColumn] == 0) {
                moves.add(new Position(nextRow, nextColumn));
            }
        }

        // sort the moves based on the strategy
        if (strategy == BORDER_HEURISTIC) {
            Collections.sort(moves, new Comparator<Position>() {
                public int compare(Position first, Position second) {
                    return borderDistance(first) - borderDistance(second);
                }
            });
        } else if (strategy == WARNSDORFF_HEURISTIC) {
            Collections.sort(moves, new Comparator<Position>() {
                public int compare(Position first, Position second) {
                    return onwardMoveCount(first) - onwardMoveCount(second);
                }
            });
        }
        return moves;
    }

    // calculate the distance of a position from the border of the board
    private int borderDistance(Position position) {
        int vertical = Math.min(position.getRow(), size - 1 - position.getRow());
        int horizontal = Math.min(position.getColumn(), size - 1 - position.getColumn());
        return vertical + horizontal;
    }

    // cont the number of onward moves from a given position
    private int onwardMoveCount(Position position) {
        int count = 0;
        for (int i = 0; i < ROW_CHANGE.length; i++) {
            int row = position.getRow() + ROW_CHANGE[i];
            int column = position.getColumn() + COLUMN_CHANGE[i];
            if (isOnBoard(row, column) && board[row][column] == 0) {
                count++;
            }
        }
        return count;
    }

    // check if a position is on the board
    private boolean isOnBoard(int row, int column) {
        return row >= 0 && row < size && column >= 0 && column < size;
    }

    // board clear
    private void clear() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                board[row][column] = 0;
            }
        }
    }

    // geetters 
    public long getMovesTried() {
        return movesTried;
    }

    public int getSize() {
        return size;
    }

    public int getMoveNumber(int row, int column) {
        if (!isOnBoard(row, column)) {
            throw new IllegalArgumentException("Position is outside the board.");
        }
        return board[row][column];
    }
}
