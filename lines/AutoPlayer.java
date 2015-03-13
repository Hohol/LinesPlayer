package lines;

import java.io.IOException;

public class AutoPlayer {

    public static void main(String[] args) throws Exception {
        new AutoPlayer().play();
    }

    public void play() throws Exception {
        /*double sum = 0;
        int matchCnt = 100;
        for (int i = 0; i < matchCnt; i++) {
            sum += playMatch();
        }
        System.out.println("avg = " + sum / matchCnt);/**/
        playMatch();
        Thread.sleep(100);
    }

    private int playMatch() throws IOException {
        int iterationCnt = 0;
        AutoPlayerGameStateReader autoPlayerGameStateReader = new AutoPlayerGameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        boolean first = true;

        while (true) {
            iterationCnt++;
            Board board = autoPlayerGameStateReader.getBoard();
            if (first) {
                System.out.println(board);
                first = false;
            }
            Move move = bestMoveFinder.findBestMove(board);
            System.out.println(move);

            int oldEmptyCnt = board.getEmptyCnt();
            autoPlayerGameStateReader.makeMove(move);
            int newEmptyCnt = autoPlayerGameStateReader.getBoard().getEmptyCnt();

            showMove(autoPlayerGameStateReader.getBoard(), move);
            if (autoPlayerGameStateReader.lost()) {
                System.out.println("lost!");
                System.out.println("iterationCnt = " + iterationCnt);
                break;
            }
            if (newEmptyCnt == oldEmptyCnt) {
                autoPlayerGameStateReader.addRandomCells(3);
            }
            if (board.getEmptyCnt() == 0) {
                System.out.println(board);
                System.out.println("lost!");
                System.out.println("iterationCnt = " + iterationCnt);
                break;
            }
            System.out.println(autoPlayerGameStateReader.getBoard());
            //System.in.read();
            System.out.println("iterationCnt = " + iterationCnt);
        }
        return iterationCnt;
    }

    private void showMove(Board board, Move move) {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                System.out.print(board.get(i, j));
            }
            if (move.from.row == i) {
                System.out.print(" f");
            }
            if (move.to.row == i) {
                System.out.print(" t");
            }
            System.out.println();
        }
        System.out.println();
    }
}