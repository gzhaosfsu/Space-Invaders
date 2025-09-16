package space_invaders.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GameWorld extends JPanel implements Runnable {

    private static final String TITLE = "Space Wars";
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final int SCALE = 1;
    private static final int ENEMY_UFO_CHANCE = 2000;
    private static final int ENEMY_FAST_SHIP_CHANCE = 4000;
    private static final int SCORE_THRESHOLD_FOR_BOSS = 300;
    private static BufferedImage background, spaceShipImg, laserImg, ufo, ufoProjectile, heart, boss, bossBullet, gameOver, fastShip, fastShipBullet;
    private static boolean running = false;
    private static boolean init = false;
    private static boolean isDead;
    private static Thread thread;
    private static Random rand;
    static SpaceShip spaceShip;
    private static ProjectileController spaceShipLaser;
    private static MovingBackground movingBackground;
    private static MusicPlayer music;
    LinkedList<EnemyUFO> enemyUFOs = new LinkedList<>();
    LinkedList<EnemyBoss> enemyBosses = new LinkedList<>();
    LinkedList<EnemyFastShip> enemyFastShips = new LinkedList<>();
    KeyListener keyListener = new KeyAdapter() {};

    public GameWorld() {
        init();
        addKeyListener(keyListener);
        setFocusable(true);
    }

    public static void loadImages() {
        try {
            background = ImageIO.read(new File("resources/background.jpeg"));
            spaceShipImg = ImageIO.read(new File("resources/spaceShipimg.png"));
            laserImg = ImageIO.read(new File("resources/laser.png"));
            ufo = ImageIO.read(new File("resources/ufo.png"));
            ufoProjectile = ImageIO.read(new File("resources/ufoBullet.png"));
            heart = ImageIO.read(new File("resources/hearts.png"));
            boss = ImageIO.read(new File("resources/boss.png"));
            bossBullet = ImageIO.read(new File("resources/bossBullet.png"));
            gameOver = ImageIO.read(new File("resources/gameOver.jpg"));
            fastShip = ImageIO.read(new File("resources/fastShip.png"));
            fastShipBullet = ImageIO.read(new File("resources/fastBullet.png"));
        } catch (IOException e) {
            System.out.println("Error Loading Recources");
        }
    }

    private void init() {
        if (init) return;

        isDead = false;
        rand = new Random();
        loadImages();
        spaceShipLaser = new ProjectileController(this);
        spaceShip = new SpaceShip(512, 600, spaceShipImg, laserImg, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_R, spaceShipLaser);
        movingBackground = new MovingBackground(0, -700, background, this, 0.8);
        music = new MusicPlayer();
        init = true;
    }

    private synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private static synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.interrupt();
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    public void run() {
        final double ticksPerSecond = 60.0;
        final double nsPerTick = 1_000_000_000.0 / ticksPerSecond;
        double delta = 0;
        long lastTime = System.nanoTime();
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " Ticks, FPS: " + frames);
                updates = 0;
                frames = 0;
            }
        }

        stop();
    }

    private void tick() {
        if (init && !isDead) {
            updateGameElements();
            checkForNewEnemies();
            updateEnemyStatus();
        }
    }

    private void updateGameElements() {
        movingBackground.tick();
        spaceShip.tick();
        spaceShipLaser.tick();
        checkSpaceshipStatus();
    }

    private void render() {
        repaint(); // This will call paintComponent
    }

    private void renderGameObjects(Graphics g) {
        movingBackground.render(g);
        spaceShip.render(g);
        spaceShipLaser.render(g);

        for (EnemyUFO ufo : enemyUFOs) {
            ufo.render(g);
        }

        for (EnemyFastShip fastShip : enemyFastShips) {
            fastShip.render(g);
        }

        if (!enemyBosses.isEmpty()) {
            enemyBosses.getFirst().render(g);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
        renderGameObjects(g); // Render all game objects
    }

    private void checkForNewEnemies() {
        if (enemyBosses.isEmpty()) {
            addEnemyUFO();
            addEnemyFastShip();
        }

        if (spaceShip.getScore() % SCORE_THRESHOLD_FOR_BOSS == 0 && enemyBosses.isEmpty() && spaceShip.getScore() != 0) {
            addBoss();
        }

        updateEnemies();
    }

    private void updateEnemies() {
        for (EnemyUFO ufo : enemyUFOs) {
            ufo.tick();
        }
        if (!enemyBosses.isEmpty()) {
            enemyBosses.getFirst().tick();
        }
        for (EnemyFastShip fastShip : enemyFastShips) {
            fastShip.tick();
        }
    }

    private void addEnemyFastShip() {
        int randomNumber = rand.nextInt(ENEMY_FAST_SHIP_CHANCE);

        if (randomNumber < 10) {
            addFastShipAtPosition(512, 10);
        } else if (randomNumber < 20) {
            addFastShipAtPosition(40, 10);
        } else if (randomNumber < 30) {
            addFastShipAtPosition(200, 10);
        } else if (randomNumber < 40) {
            addFastShipAtPosition(300, 10);
        } else if (randomNumber < 50) {
            addFastShipAtPosition(400, 10);
        }
    }

    private void addEnemyUFO() {
        int randomNumber = rand.nextInt(ENEMY_UFO_CHANCE);

        if (randomNumber < 10) {
            addUFOAtPosition(512, 10);
        } else if (randomNumber < 20) {
            addUFOAtPosition(40, 10);
        } else if (randomNumber < 30) {
            addUFOAtPosition(200, 10);
        } else if (randomNumber < 40) {
            addUFOAtPosition(300, 10);
        } else if (randomNumber < 50) {
            addUFOAtPosition(400, 10);
        }
    }

    private void addFastShipAtPosition(int x, int y) {
        enemyFastShips.add(new EnemyFastShip(x, y, fastShip, fastShipBullet, this));
    }

    private void addUFOAtPosition(int x, int y) {
        enemyUFOs.add(new EnemyUFO(x, y, ufo, ufoProjectile, this));
    }

    private void updateEnemyStatus() {
        enemyUFOs.removeIf(Enemies::isDead);
        if (!enemyBosses.isEmpty()) {
            EnemyBoss boss = enemyBosses.getFirst();
            if (boss.getHealth() <= 0) {
                spaceShip.setScore(spaceShip.getScore() + 100);
                enemyBosses.removeFirst();
            }
        }
        enemyFastShips.removeIf(Enemies::isDead);
    }

    public void addBoss() {
        enemyBosses.add(new EnemyBoss(512, 40, boss, bossBullet, this));
    }

    public void checkSpaceshipStatus() {
        if (spaceShip.getHp() <= 0) {
            spaceShip.setLife(spaceShip.getLife() - 1);
            if (spaceShip.getLife() <= 0) {
                spaceShip.setSpeedx(0);
                spaceShip.setSpeedy(0);
                isDead = true;
            } else {
                spaceShip.setHp(100);
                spaceShip.setX(512);
                spaceShip.setY(600);
            }
        }
    }

    void restartGame() {
        init = false;
        stop();
        init();
        start();
    }

    public boolean isRunning() {
        return running;
    }

    public LinkedList<Enemies> getEnemies() {
        LinkedList<Enemies> enemies = new LinkedList<>();
        enemies.addAll(enemyUFOs);
        enemies.addAll(enemyFastShips);
        enemies.addAll(enemyBosses);
        return enemies;
    }

    public static void main(String[] args) {

    }

    private SpaceShip getSpaceShip() {
        return spaceShip;
    }
}
