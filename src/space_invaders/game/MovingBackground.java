package space_invaders.game;

import space_invaders.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MovingBackground extends GameObject {

    private final BufferedImage spaceImg;
    private final GameWorld game;
    private final int IMAGE_HEIGHT;
    private final double SCROLL_SPEED;

    public MovingBackground(double x, double y, BufferedImage spaceImg, GameWorld game, double scrollSpeed) {
        super(x, y, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        this.spaceImg = spaceImg;
        this.game = game;
        this.IMAGE_HEIGHT = spaceImg.getHeight();
        this.SCROLL_SPEED = scrollSpeed;
    }

    @Override
    public void tick() {
        y += SCROLL_SPEED;  // Use configurable scroll speed

        // Reset background position for seamless scrolling
        if (y >= IMAGE_HEIGHT) {
            y -= IMAGE_HEIGHT; // Smooth transition by moving up
        }
    }

    @Override
    public void render(Graphics g) {
        // Draw the background image twice to cover the whole screen
        g.drawImage(spaceImg, (int) x, (int) y, null);
        g.drawImage(spaceImg, (int) x, (int) y - IMAGE_HEIGHT, null);
    }
}
