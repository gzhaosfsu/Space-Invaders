package space_invaders.game;

import space_invaders.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a projectile in the game.
 */
public class Projectile extends GameObject {

    private final double speedx;
    private final double speedy;
    private final BufferedImage laserImg;
    private static final int PROJECTILE_WIDTH = 1;
    private static final int PROJECTILE_HEIGHT = 2;
    private final int damage;

    public Projectile(double x, double y, BufferedImage laserImg, double speedx, double speedy, int damage) {
        super(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        this.laserImg = laserImg;
        this.speedx = speedx;
        this.speedy = speedy;
        this.damage = damage;
    }

    @Override
    public void tick() {
        x += speedx;
        y += speedy;
        if (isOutOfBounds()) {
            setActive(false);
        }
    }

    private boolean isOutOfBounds() {
        return x < 0 || x > GameConstants.GAME_SCREEN_WIDTH ||
                y < 0 || y > GameConstants.GAME_SCREEN_HEIGHT;
    }

    @Override
    public void render(Graphics g) {
        if (laserImg != null && isActive) {
            g.drawImage(laserImg, (int) x, (int) y, width, height, null);
        }
    }

    public int getDamage() { return damage; }
}
