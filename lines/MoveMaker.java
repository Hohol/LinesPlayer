package lines;

import java.awt.*;
import java.awt.event.InputEvent;

import static lines.GameStateReader.*;

public class MoveMaker {
    private final Robot robot;

    public MoveMaker() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(Move move) {
        try {
            pointAndClick(move.from);
            pointAndClick(move.to);
            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void pointAndClick(Position p) throws InterruptedException {
        robot.mouseMove((int) (X_SHIFT + p.col * CELL_SIZE + CELL_SIZE / 2), (int) (Y_SHIFT + p.row * CELL_SIZE + CELL_SIZE / 2));
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(200);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
