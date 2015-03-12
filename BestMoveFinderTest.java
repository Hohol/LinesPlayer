import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

@Test
public class BestMoveFinderTest {
    BestMoveFinder bestMoveFinder = new BestMoveFinder(1);

    @Test
    void test() {
        Board board = new Board(
                "1111.1"
        );
        check(board, new Move(0, 5, 0, 4));
    }

    @Test
    void test2() {
        Board board = new Board(
                "" +
                        ".111.....\n" +
                        "........1\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........."
        );
        check(board, new Move(1, 8, 0, 4));
    }

    @Test
    void test22() {
        Board good = new Board(
                "" +
                        ".1111....\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........."
        );
        Board bad = new Board(
                "" +
                        ".111.....\n" +
                        "..1......\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........."
        );
        checkGoodBad(good, bad);
    }

    @Test
    void testClear() {
        Board board = new Board(
                "" +
                        "11111..........\n" +
                        ".222222222.....\n" +
                        "...............\n" +
                        "..3........4444\n" +
                        "...33......4444\n" +
                        "...333333..4444\n" +
                        "....33.....4444\n" +
                        "....3..........\n" +
                        "....3......4444\n" +
                        "....3......4444"
        );
        Board actual = board.clearLines(Arrays.asList(
                new Position(0, 0),
                new Position(1, 5),
                new Position(5, 4)
        ));
        Board expected = new Board(
                "" +
                        "...............\n" +
                        "...............\n" +
                        "...............\n" +
                        "..3........4444\n" +
                        "...3.......4444\n" +
                        "...........4444\n" +
                        ".....3.....4444\n" +
                        "...............\n" +
                        "...........4444\n" +
                        "...........4444"
        );
        assertEquals(actual, expected);
    }

    @Test
    void test3() {
        Board board = new Board(
                "" +
                        ".........\n" +
                        ".........\n" +
                        "..2.2....\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "2........"
        );

        Move move = bestMoveFinder.findBestMove(board);
        assertEquals(move, new Move(8, 0, 2, 3));
    }

    @Test
    void test33() {
        Board board = new Board(
                "" +
                        "..2.2....\n" +
                        "........2"
        );

        Move move = bestMoveFinder.findBestMove(board);
        assertEquals(move, new Move(1, 8, 0, 3));
    }

    @Test
    void test4() {
        Board good = new Board(
                "" +
                        "..222....\n" +
                        "........."
        );
        Board bad = new Board(
                "" +
                        "..2......\n" +
                        ".2......2"
        );

        checkGoodBad(good, bad);
    }

    @Test
    void test5() {
        Board good = new Board(
                "" +
                        "11.......\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........."
        );
        Board bad = new Board(
                "" +
                        "1........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........1"
        );
        checkGoodBad(good, bad);
    }

    @Test
    void test6() {
        Board good = new Board(
                "" +
                        "6.4.....1\n" +
                        "..30.....\n" +
                        ".4.......\n" +
                        "1........\n" +
                        ".........\n" +
                        "..00.3.45\n" +
                        ".2.0....5\n" +
                        "..2.0...5\n" +
                        "5.4.60..."
        );
        Board bad = new Board(
                "" +
                        "6.4.....1\n" +
                        "..30.....\n" +
                        ".4.......\n" +
                        "1........\n" +
                        "...0.....\n" +
                        "..00.3.45\n" +
                        ".2.0....5\n" +
                        "..2.0...5\n" +
                        "5.4.6...."
        );
        checkGoodBad(good, bad);
    }

    @Test
    void test7() {
        Board board = new Board(
                "" +
                        "6.4.....1\n" +
                        "..301....\n" +
                        ".4.......\n" +
                        "1.....0..\n" +
                        "...0....5\n" +
                        ".000.3.45\n" +
                        ".2.0....5\n" +
                        "..230...5\n" +
                        "5.4.6...."
        );

        check(board, new Move(8, 0, 3, 8));
    }

    @Test
    void testBadDiagonal() {
        Board good = new Board(
                "" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "...5.....\n" +
                        "..5......\n" +
                        "........."
        );
        Board bad = new Board(
                "" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".5.......\n" +
                        "..5......\n" +
                        "........."
        );
        checkGoodBad(good, bad);
    }

    @Test
    void test8() {
        Board good = new Board(
                "" +
                        "4.......1\n" +
                        "...2.1...\n" +
                        ".22......\n" +
                        ".2....6..\n" +
                        ".......1.\n" +
                        ".1...4...\n" +
                        "..21..1..\n" +
                        "4........\n" +
                        ".0000...."
        );
        Board bad = new Board(
                "" +
                        "4.......1\n" +
                        ".....1...\n" +
                        ".22......\n" +
                        ".22...6..\n" +
                        ".......1.\n" +
                        ".1...4...\n" +
                        "..21..1..\n" +
                        "4........\n" +
                        ".0000...."
        );
        checkGoodBad(good, bad);
    }

    @Test
    void test9() {
        Board good = new Board(
                "" +
                        "...110...\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........."
        );
        Board bad = new Board(
                "" +
                        "11.......\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........0"
        );
        checkGoodBad(good, bad);
    }

    @Test
    void test10() {
        Board good = new Board(
                "" +
                        "...11.11.\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "........."
        );
        Board bad = new Board(
                "" +
                        "...11....\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "...11...."
        );
        checkGoodBad(good, bad);
    }

    @Test
    void test12() {
        Board board = new Board(
                "" +
                        "....0....\n" +
                        "....0....\n" +
                        "....0....\n" +
                        "11110....\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        ".........\n" +
                        "1........"
        );
        Move move = bestMoveFinder.findBestMove(board);
        assertEquals(move.from, new Position(3, 4));
    }

    //-----utils

    private void checkGoodBad(Board good, Board bad) {
        BestMoveFinder bmf = new BestMoveFinder(0);
        double goodScore = bmf.evaluate(good);
        double badScore = bmf.evaluate(bad);
        assertTrue(goodScore > badScore + 1e-5, "goodScore = " + goodScore + ", badScore = " + badScore);
    }

    private void check(Board board, Move expected) {
        Move move = bestMoveFinder.findBestMove(board);
        assertEquals(move, expected);
    }
}