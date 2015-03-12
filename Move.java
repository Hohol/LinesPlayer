public class Move {
    public final Position from;
    public final Position to;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        from = new Position(fromRow, fromCol);
        to = new Position(toRow, toCol);
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (!from.equals(move.from)) return false;
        if (!to.equals(move.to)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }
}