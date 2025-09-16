package space_invaders.game;


import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemies extends GameObject {
    protected int health;
    protected boolean isDead = false;
    protected BufferedImage enemyImage;
    protected BufferedImage projectileImg;
    protected ProjectileController shooter;

    public Enemies(double x, double y, int width, int height, BufferedImage image, BufferedImage projectileImg, GameWorld game) {
        super(x, y, width, height);
        this.enemyImage = image;
        this.projectileImg = projectileImg;
        this.shooter = new ProjectileController(game);
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) {
            isDead = true;
        }
    }

    public boolean isDead() { return isDead; }
    public int getHealth() { return health; }

    @Override
    public void render(Graphics g) {
        if ( !isDead) {
            g.drawImage(enemyImage, (int) x, (int) y, null);
            shooter.render(g);
        }
    }

    @Override
    public abstract void tick();
}
