// creates the row column position on board
public final class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    // getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
