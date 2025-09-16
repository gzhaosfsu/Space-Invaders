package space_invaders.game;

import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyFastShip extends Enemies {

    private static final int INITIAL_HEALTH = 50;
    private static final int FAST_WIDTH = 100;
    private static final int FAST_HEIGHT = 80;
    private static final int LEFT_BOUND = 0;
    private static final int RIGHT_BOUND = 950;
    private static final int TOP_BOUND = 0;
    private static final int BOTTOM_BOUND = 200;
    private static final int FAST_DAMAGE = 15;

    private int speedx = -7;
    private int speedy = 3;
    private final Random rand;

    public EnemyFastShip(double x, double y, BufferedImage fastShipImage, BufferedImage projectileImg, GameWorld game) {
        super(x, y, FAST_WIDTH, FAST_HEIGHT, fastShipImage, projectileImg, game);
        this.health = INITIAL_HEALTH;
        this.rand = new Random();
    }

    @Override
    public void tick() {
        if (!isDead) {
            x += speedx;
            y += speedy;

            if (rand.nextInt(100) < 1) { // Adjust shooting probability as needed
                shooter.addProjectile(new Projectile(x, y, projectileImg, 0, 5, FAST_DAMAGE));
            }

            if (x < LEFT_BOUND || x > RIGHT_BOUND - getWidth()) {
                speedx = -speedx;
            }
            if (y < TOP_BOUND || y > BOTTOM_BOUND - getHeight()) {
                speedy = -speedy;
            }

            shooter.tick();
        }
    }
}
