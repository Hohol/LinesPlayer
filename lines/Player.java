package lines;

import java.io.IOException;

public class Player {
    public void play() throws IOException {
        GameStateReader gameStateReader = new GameStateReader();
        BestMoveFinder bestMoveFinder = new BestMoveFinder(1);
        MoveMaker moveMaker = new MoveMaker();
        int iterationCnt = 0;
        while(true) {
            iterationCnt++;
            Board board = gameStateReader.readGameState();
            System.out.println(board);
            if(board.getEmptyCnt() == 0) {
                break;
            }
            Move move = bestMoveFinder.findBestMove(board);
            System.out.println(move);
            moveMaker.makeMove(move);
            System.out.println("iterationCnt = " + iterationCnt);

            //System.in.read();
        }
    }
}
