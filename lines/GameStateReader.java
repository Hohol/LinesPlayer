package lines;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameStateReader {
    public static final double CELL_SIZE = 39.5; // =(
    public static final int SIZE = 9;
    private static final Color[] COLORS = {
            new Color(163, 247, 161), //green 0
            new Color(141, 141, 141), //black 1
            new Color(239, 239, 137), //yellow 2
            new Color(245, 211, 157), //orange 3
            new Color(245, 155, 155), //red 4
            new Color(163, 161, 254), //blue 5
            new Color(255, 255, 255), //white 6
            new Color(215, 215, 215), //empty .
    };
    public static final int X_SHIFT = 2373;
    public static final int Y_SHIFT = 284;


    private final Robot robot;

    public GameStateReader() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public Board readGameState() {
        BufferedImage img = robot.createScreenCapture(new Rectangle(X_SHIFT, Y_SHIFT, 500, 500));
        Board board = new Board(SIZE, SIZE);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int x = (int) (i * CELL_SIZE + CELL_SIZE / 2);
                int y = (int) (j * CELL_SIZE + CELL_SIZE / 2);
                Color pixelColor = new Color(img.getRGB(x, y));
                int index = getColorIndex(pixelColor);
                if (index == COLORS.length - 1) {
                    board.set(j, i, Board.EMPTY);
                } else {
                    board.set(j, i, (char) ('0' + index));
                }
                //colors[j][i] = pixelColor; // j, i!
                //img.setRGB(x, y, Color.PINK.getRGB());
            }
        }

        /*printColor(colors[0][0], "green");
        printColor(colors[0][2], "black");
        printColor(colors[0][3], "yellow");
        printColor(colors[2][3], "orange");
        printColor(colors[2][0], "red");
        printColor(colors[8][3], "blue");
        printColor(colors[5][0], "white");
        printColor(colors[0][1], "empty");/**/
        //writeImage(img);
        return board;
    }

    private int getColorIndex(Color pixelColor) {
        int minDiff = Integer.MAX_VALUE;
        int bestIndex = -1;
        for (int i = 0; i < COLORS.length; i++) {
            Color color = COLORS[i];
            int diff = Math.abs(color.getRed() - pixelColor.getRed()) +
                    Math.abs(color.getGreen() - pixelColor.getGreen()) +
                    Math.abs(color.getBlue() - pixelColor.getBlue());
            if (diff < minDiff) {
                minDiff = diff;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    private void printColor(Color color, String comment) {
        System.out.println("new Color(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "), //" + comment);
    }

    private void writeImage(BufferedImage img) {
        try {
            ImageIO.write(img, "png", new File("img.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
