public class Player {

    public void play() throws Exception {
        AutoPlayerGameStateReader autoPlayerGameStateReader = new AutoPlayerGameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder();
        boolean first = true;
        int iterationCnt = 0;
        while (true) {
            iterationCnt++;
            Board board = autoPlayerGameStateReader.getBoard();
            if (first) {
                System.out.println(board);
                first = false;
            }
            Move move = bestMoveFinder.findBestMove(board);
            System.out.println(move);

            autoPlayerGameStateReader.makeMove(move);
            showMove(autoPlayerGameStateReader.getBoard(), move);
            if (autoPlayerGameStateReader.lost()) {
                System.out.println("lost!");
                System.out.println("iterationCnt = " + iterationCnt);
                break;
            }
            autoPlayerGameStateReader.addRandomCells();
            if (board.getEmptyCnt() == 0) {
                System.out.println(board);
                System.out.println("lost!");
                System.out.println("iterationCnt = " + iterationCnt);
                break;
            }
            System.out.println(autoPlayerGameStateReader.getBoard());
            System.in.read();
            System.out.println("iterationCnt = " + iterationCnt);
        }
        Thread.sleep(100);
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