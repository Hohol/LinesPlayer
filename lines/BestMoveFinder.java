package lines;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BestMoveFinder {
    private final Random rnd = new Random(323);

    private final int depthLimit = 1;

    public Move findBestMove(Board board) {
        return findBestMoveInternal(board, 0).move;
    }

    MoveAndEvaluation findBestMoveInternal(Board board, int depth) {
        MoveAndEvaluation best = null;
        for (int fromRow = 0; fromRow < board.getHeight(); fromRow++) {
            for (int fromCol = 0; fromCol < board.getWidth(); fromCol++) {
                if (board.isEmpty(fromRow, fromCol)) {
                    continue;
                }
                boolean[][] reachable = reachable(board, fromRow, fromCol);
                for (int toRow = 0; toRow < board.getHeight(); toRow++) {
                    for (int toCol = 0; toCol < board.getWidth(); toCol++) {
                        if (!board.isEmpty(toRow, toCol)) {
                            continue;
                        }
                        if (!reachable[toRow][toCol]) {
                            continue;
                        }
                        if (!sameColorNear(board, toRow, toCol, board.get(fromRow, fromCol))) { // cell (fromRow, fromCol) may be near - it's ok
                            continue;
                        }
                        board.move(fromRow, fromCol, toRow, toCol);
                        Board newBoard = board.clearLines(Arrays.asList(new Position(toRow, toCol)));

                        double score;
                        int emptyCnt = newBoard.getEmptyCnt();
                        if (emptyCnt < 3) {
                            score = 0;
                        } else {
                            if (depth == depthLimit) {
                                score = evaluate(newBoard);
                            } else {
                                score = evaluate(newBoard) + findBestMoveInternal(newBoard, depth + 1).score / 2 + evaluateRandom(newBoard) / 2;
                            }/* else {
                                score = 0;
                                for (int i = 0; i < statesPerMoveLimit; i++) {
                                    List<lines.Cell> newTiles = getRandomNewTiles(newBoard);
                                    for (lines.Cell newTile : newTiles) {
                                        newBoard.set(newTile);
                                    }
                                    List<lines.Position> p = new ArrayList<>();
                                    for (lines.Cell newTile : newTiles) {
                                        p.add(new lines.Position(newTile.row, newTile.col));
                                    }
                                    lines.Board b = newBoard.clearLines(p);
                                    score += findBestMoveInternal(b, depth + 1).score;
                                    for (lines.Cell newTile : newTiles) {
                                        newBoard.set(newTile.row, newTile.col, lines.Board.EMPTY);
                                    }
                                }
                                score /= statesPerMoveLimit;
                            }*/
                        }

                        if (best == null || score > best.score) {
                            best = new MoveAndEvaluation(new Move(fromRow, fromCol, toRow, toCol), score);
                        }
                        board.move(toRow, toCol, fromRow, fromCol);
                    }
                }
            }
        }
        if (best == null) {
            best = new MoveAndEvaluation(null, board.getHeight() * board.getWidth());
        }
        return best;
    }

    private boolean sameColorNear(Board board, int row, int col, char color) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (board.inside(row + dx, col + dy) && board.get(row + dx, col + dy) == color) {
                    return true;
                }
            }
        }
        return false;
    }

    double evaluate(Board board) {
        double r = board.getEmptyCnt() * 100;
        double r2 = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                r2 += getScore(board, i, j, 0, 1);
                r2 += getScore(board, i, j, 1, -1);
                r2 += getScore(board, i, j, 1, 0);
                r2 += getScore(board, i, j, 1, 1);
            }
        }
        return r + r2;
    }

    private double evaluateRandom(Board board) {
        if (board.getEmptyCnt() == 0) {
            return 0;
        }
        int cnt = 0;
        double score = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.get(i, j) != Board.EMPTY) {
                    continue;
                }
                List<Position> newPositions = Arrays.asList(new Position(i, j));
                for (int color = 0; color < 7; color++) {
                    board.set(i, j, (char) (color + '0'));
                    Board newBoard = board.clearLines(newPositions);
                    cnt++;
                    double r = newBoard.getEmptyCnt() * 100;
                    double r2 = 0;
                    for (int i1 = 0; i1 < newBoard.getHeight(); i1++) {
                        for (int j1 = 0; j1 < newBoard.getWidth(); j1++) {
                            r2 += getScore(newBoard, i1, j1, 0, 1);
                            r2 += getScore(newBoard, i1, j1, 1, -1);
                            r2 += getScore(newBoard, i1, j1, 1, 0);
                            r2 += getScore(newBoard, i1, j1, 1, 1);
                        }
                    }
                    score += r + r2;
                }
                board.set(i, j, Board.EMPTY);
            }
        }
        return score / cnt;
    }

    private double getScore(Board board, int sx, int sy, int dx, int dy) {
        if (board.isEmpty(sx, sy)) {
            return 0;
        }
        if (board.inside(sx - dx, sy - dy) && board.get(sx - dx, sy - dy) == board.get(sx, sy)) { // already considered
            return 0;
        }
        if (badDiagonal(board, sx, sy, dx, dy)) {
            return 0;
        }
        double r = 1;
        int x = sx;
        int y = sy;
        while (board.inside(x + dx, y + dy) && board.get(x, y) == board.get(x + dx, y + dy)) {
            r++;
            x += dx;
            y += dy;
        }
        if (r == 1) {
            return 0;
        }
        if (r == 2) {
            r = 0.1;
        }
        if (r == 3) {
            r /= 5;
        }
        if (!board.inside(sx - dx, sy - dy)) {
            r *= 0.6;
        } else if (board.get(sx - dx, sy - dy) != Board.EMPTY) {
            r *= 0.75;
        }
        if (!board.inside(x + dx, y + dy)) {
            r *= 0.6;
        } else if (board.get(x + dx, y + dy) != Board.EMPTY) {
            r *= 0.75;
        }

        return r;
    }

    private boolean badDiagonal(Board board, int sx, int sy, int dx, int dy) {
        int len = 1;
        int x = sx, y = sy;
        while (board.inside(x + dx, y + dy)) {
            x += dx;
            y += dy;
            len++;
        }
        x = sx;
        y = sy;
        while (board.inside(x - dx, y - dy)) {
            x -= dx;
            y -= dy;
            len++;
        }
        return len < 5;
    }


    private boolean[][] reachable(Board board, int fromRow, int fromCol) {
        boolean[][] visited = new boolean[board.getHeight()][board.getWidth()];
        dfs(true, board, visited, fromRow, fromCol);
        return visited;
    }

    private void dfs(boolean first, Board board, boolean[][] visited, int row, int col) {
        if (row < 0 || row >= board.getHeight() || col < 0 || col >= board.getWidth()) {
            return;
        }
        if (visited[row][col]) {
            return;
        }
        if (!first && !board.isEmpty(row, col)) {
            return;
        }
        visited[row][col] = true;
        dfs(false, board, visited, row - 1, col);
        dfs(false, board, visited, row, col - 1);
        dfs(false, board, visited, row, col + 1);
        dfs(false, board, visited, row + 1, col);
    }
}