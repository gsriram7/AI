class Cell {
    int row;
    int col;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "(" +
                "row=" + row +
                ", col=" + col +
                ')';
    }
}
