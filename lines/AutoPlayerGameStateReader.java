package lines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AutoPlayerGameStateReader {
    private final Random rnd = new Random();
    private Board board;

    public AutoPlayerGameStateReader() {
        board = new Board(9, 9);
        addRandomCells();
    }

    private List<Cell> getRandomNewCells(Board newBoard) {
        List<Cell> r = new ArrayList<>();
        while (r.size() < 3) {
            int p = rnd.nextInt(newBoard.getHeight() * newBoard.getWidth());
            int x = p / newBoard.getWidth();
            int y = p % newBoard.getWidth();
            char color = (char) ('0' + rnd.nextInt(7));
            if (newBoard.isEmpty(x, y)) {
                r.add(new Cell(x, y, color));
            }
        }
        return r;
    }

    public Board getBoard() {
        return board;
    }

    void addRandomCells() {
        List<Cell> newCells = getRandomNewCells(board);
        List<Position> positions = new ArrayList<>();
        for (Cell newCell : newCells) {
            board.set(newCell);
            positions.add(new Position(newCell.row, newCell.col));
        }
        board = board.clearLines(positions);
    }

    public void makeMove(Move move) {
        board.move(move);
        board = board.clearLines(Arrays.asList(new Position(move.to.row, move.to.col)));
    }

    public boolean lost() {
        return board.getEmptyCnt() < 3;
    }
}