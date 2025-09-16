package space_invaders.game;

import space_invaders.GameConstants;

import java.awt.image.BufferedImage;

public class EnemyBoss extends Enemies {

    private static final int INITIAL_HEALTH = 170;
    private static final int BOSS_WIDTH = 170;
    private static final int BOSS_HEIGHT = 150;
    private static final int SHOOT_INTERVAL = 1000; // Milliseconds
    private static final int BOSS_DAMAGE = 25;
    BufferedImage boss;
    BufferedImage bossShot;
    ProjectileController bossShotControl;

    private int speedx = 5;
    private long lastShootTime = 0;

    public EnemyBoss(double x, double y, BufferedImage bossImage, BufferedImage bossBullet, GameWorld game) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, bossImage, bossBullet, game);
        this.health = INITIAL_HEALTH;
    }

    @Override
    public void tick() {
        if (health > 0) {
            x += speedx;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShootTime > SHOOT_INTERVAL) {
                shooter.addProjectile(new Projectile(x , y, bossShot, 0, 5, BOSS_DAMAGE));
                shooter.addProjectile(new Projectile(x , y, bossShot, 3, 3, BOSS_DAMAGE));
                shooter.addProjectile(new Projectile(x , y, bossShot, -3, 3, BOSS_DAMAGE));
                lastShootTime = currentTime;
            }

            if (x < 0 || x > GameConstants.GAME_SCREEN_WIDTH - getWidth()) {
                speedx = -speedx;
            }

            shooter.tick();
        }
    }
}
