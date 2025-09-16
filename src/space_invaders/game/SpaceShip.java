package space_invaders.game;

import space_invaders.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.LinkedList;

/**
 * Represents the spaceship in the game.
 */
public class SpaceShip extends GameObject {
    private static final int SHIP_WIDTH = 30;
    private static final int SHIP_HEIGHT = 50;
    private static final int MAX_X = GameConstants.GAME_SCREEN_WIDTH - SHIP_WIDTH;
    private static final int MAX_Y = GameConstants.GAME_SCREEN_HEIGHT - SHIP_HEIGHT;
    private static final int HEALTH_BAR_WIDTH = 70;
    private static final int HEALTH_BAR_HEIGHT = 20;
    private static int SHIP_DAMAGE = 25;


    private double speedx = 0;
    private double speedy = 0;

    private int life = 5;
    private int hp = 100;
    private int score = 0;

    private final int up, down, left, right, shoot, respawn;

    private final BufferedImage shipIMG;
    private final BufferedImage laser;
    private final ProjectileController shooter;

    public SpaceShip(double x, double y, BufferedImage shipIMG, BufferedImage laser, int up, int down, int left, int right, int shoot, int respawn, ProjectileController shooter) {
        super(x, y, SHIP_WIDTH, SHIP_HEIGHT);
        this.shipIMG = shipIMG;
        this.laser = laser;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.shoot = shoot;
        this.respawn = respawn;
        this.shooter = shooter;
    }

    @Override
    public void tick() {
        x+= speedx;
        y+= speedy;
        // Boundary checks
        if (x < 0) x = 0;
        if (x > MAX_X) x = MAX_X;
        if (y < 0) y = 0;
        if (y > MAX_Y) y = MAX_Y;

        // Update projectiles
        shooter.tick();
    }

    @Override
    public void render(Graphics g) {
        if (shipIMG != null) {
            g.drawImage(shipIMG, (int) x, (int) y, width, height, null);
        }
        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect((int) x, (int) y + height + 10, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        g.setColor(Color.green);
        g.fillRect((int) x, (int) y + height + 10, Math.min(hp * HEALTH_BAR_WIDTH / 100, HEALTH_BAR_WIDTH), HEALTH_BAR_HEIGHT);
    }

    public void moveUp() { speedy = -5; }
    public void moveDown() { speedy = 5; }
    public void moveLeft() { speedx = -5; }
    public void moveRight() { speedx = 5; }
    public void stopHorizontalMovement() { speedx = 0; }
    public void stopVerticalMovement() { speedy = 0; }

    public boolean checkCollision(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            life--;
            if (life > 0) {
                respawn();
            } else {
                gameOver();
            }
        }
    }

    private void respawn() {
        x = 100;
        y = 100;
        hp = 100;
    }

    private void gameOver() {
        System.out.println("Game Over!");
    }

    public void increaseScore(int points) {
        score += points;
    }

    public void handleKeyPress(int keyCode) {
        if (keyCode == up) moveUp();
        else if (keyCode == down) moveDown();
        else if (keyCode == left) moveLeft();
        else if (keyCode == right) moveRight();
        else if (keyCode == shoot) shoot();
        else if (keyCode == respawn) respawn();
    }

    public void handleKeyRelease(int keyCode) {
        if (keyCode == up || keyCode == down) stopVerticalMovement();
        if (keyCode == left || keyCode == right) stopHorizontalMovement();
    }

    void shoot() {
        // Create and add a projectile to the shooter
        // Example:
        shooter.addProjectile(new Projectile(x + getWidth() / 2.0, y, laser, 0, -10, 10));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void setHp(int health) {
        this.hp = health;
    }

    public int getLife() {
        return life;
    }

    public int getHp() {
        return hp;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setSpeedx(int speedx) {
        this.speedx = speedx;
    }

    public void setSpeedy(int speedy) {
        this.speedy = speedy;
    }

    public double getSpeedx() {
        return speedx;
    }

    public double getSpeedy() {
        return speedy;
    }

    public int getDAMAGE() {
        return SHIP_DAMAGE;
    }

    public void setDAMAGE(int damage) {
        SHIP_DAMAGE = damage;
    }

    public int getRespawn() {
        return respawn;
    }
}
