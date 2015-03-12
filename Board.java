import java.util.Arrays;
import java.util.List;

public class Board {
    public static final char EMPTY = '.';
    private final char[][] b;

    public Board(String s) {
        String[] ar = s.split("\n");
        b = new char[ar.length][];
        for (int i = 0; i < ar.length; i++) {
            b[i] = ar[i].toCharArray();
        }
    }

    public Board(Board board) {
        b = new char[board.getHeight()][board.getWidth()];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                b[i][j] = board.get(i, j);
            }
        }
    }

    public Board(int height, int width) {
        b = new char[height][width];
        for (char[] aB : b) {
            Arrays.fill(aB, EMPTY);
        }
    }

    public int getWidth() {
        return b[0].length;
    }

    public int getHeight() {
        return b.length;
    }

    char get(int row, int col) {
        return b[row][col];
    }

    void set(int row, int col, char value) {
        b[row][col] = value;
    }

    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        if (get(fromRow, fromCol) == EMPTY) {
            throw new RuntimeException();
        }
        if (get(toRow, toCol) != EMPTY) {
            throw new RuntimeException();
        }
        b[toRow][toCol] = b[fromRow][fromCol];
        b[fromRow][fromCol] = EMPTY;
    }

    boolean isEmpty(int row, int col) {
        return b[row][col] == EMPTY;
    }

    Board clearLines(List<Position> newPositions) {
        if (changes(newPositions)) {
            Board newBoard = new Board(this);
            for (Position pos : newPositions) {
                clear(newBoard, pos, 0, 1);
                clear(newBoard, pos, 1, -1);
                clear(newBoard, pos, 1, 0);
                clear(newBoard, pos, 1, 1);
            }
            return newBoard;
        }
        return this;
    }

    private boolean changes(List<Position> newTiles) {
        boolean r = false;
        for (Position pos : newTiles) {
            r |= check(pos, 0, 1);
            r |= check(pos, 1, -1);
            r |= check(pos, 1, 0);
            r |= check(pos, 1, 1);
        }
        return r;
    }

    private boolean check(Position pos, int dx, int dy) {
        int row = pos.row;
        int col = pos.col;
        int a = 0;
        int b = 0;
        char val = get(row, col);
        while (canIncrease(a, val, row, col, dx, dy)) {
            a++;
        }
        while (canIncrease(b, val, row, col, -dx, -dy)) {
            b++;
        }
        return a + b + 1 >= 5;
    }

    private void clear(Board newBoard, Position pos, int dx, int dy) {
        int row = pos.row;
        int col = pos.col;
        int a = 0;
        int b = 0;
        char val = get(row, col);
        while (canIncrease(a, val, row, col, dx, dy)) {
            a++;
        }
        while (canIncrease(b, val, row, col, -dx, -dy)) {
            b++;
        }
        if (a + b + 1 >= 5) {
            for (int d = -b; d <= a; d++) {
                int nx = row + d * dx;
                int ny = col + d * dy;
                newBoard.set(nx, ny, EMPTY);
            }
        }
    }

    private boolean canIncrease(int a, char val, int row, int col, int dx, int dy) {
        int nx = row + (a + 1) * dx;
        int ny = col + (a + 1) * dy;
        return nx >= 0 && nx < getHeight() && ny >= 0 && ny < getWidth() && get(nx, ny) == val;
    }

    @Override
    public boolean equals(Object o) {
        Board board = (Board) o;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (get(i, j) != board.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] chars : b) {
            sb.append(chars);
            sb.append("\n");
        }
        return sb.toString();
    }

    public void set(Cell newTile) {
        set(newTile.row, newTile.col, newTile.color);
    }

    public boolean inside(int x, int y) {
        return x >= 0 && x < getHeight() && y >= 0 && y < getWidth();
    }

    public void move(Move move) {
        move(move.from.row, move.from.col, move.to.row, move.to.col);
    }

    public int getEmptyCnt() {
        int emptyCnt = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (get(i, j) == EMPTY) {
                    emptyCnt++;
                }
            }
        }
        return emptyCnt;
    }
}