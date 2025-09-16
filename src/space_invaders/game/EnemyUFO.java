package space_invaders.game;

import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyUFO extends Enemies {

    private static final int INITIAL_HEALTH = 50;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 60;
    private static final int LEFT_BOUND = 0;
    private static final int RIGHT_BOUND = 950;
    private static final int TOP_BOUND = 0;
    private static final int BOTTOM_BOUND = 600;
    private static final int SHOOT_CHANCE = 100;
    private static final int SHOOT_INTERVAL = 1000;
    private static final int UFO_DAMAGE = 10;

    private int speedx = 2;
    private int speedY = 2;
    private final Random rand;

    public EnemyUFO(double x, double y, BufferedImage ufoImage, BufferedImage projectileImg, GameWorld game) {
        super(x, y, WIDTH, HEIGHT, ufoImage, projectileImg, game);
        this.health = INITIAL_HEALTH;
        this.rand = new Random();
    }

    @Override
    public void tick() {
        if (!isDead) {
            x += speedx;
            y += speedY;

            if (rand.nextInt(SHOOT_INTERVAL) < SHOOT_CHANCE) {
                shooter.addProjectile(new Projectile(x, y , projectileImg, 0, 5, UFO_DAMAGE));
            }

            if (x < LEFT_BOUND || x > RIGHT_BOUND - getWidth()) {
                speedx = -speedx;
            }
            if (y < TOP_BOUND || y > BOTTOM_BOUND - getHeight()) {
                speedY = -speedY;
            }

            shooter.tick();
        }
    }
}
