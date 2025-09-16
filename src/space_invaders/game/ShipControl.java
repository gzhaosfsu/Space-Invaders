package space_invaders.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *
 * @author anthony-pc
 */
public class ShipControl implements KeyListener {
    private final SpaceShip spaceShip;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    private boolean isShooting = false;
    private final int speed = 8;




    public ShipControl(SpaceShip spaceShip, int up, int down, int left, int right, int shoot) {
        this.spaceShip = spaceShip;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(key);
        if (spaceShip.getHp() > 0) {
        switch (key) {
            case KeyEvent.VK_W:
                spaceShip.setSpeedy(-speed);
                break;
            case KeyEvent.VK_S:
                spaceShip.setSpeedy(speed);
                break;
            case KeyEvent.VK_A:
                spaceShip.setSpeedx(-speed);
                break;
            case KeyEvent.VK_D:
                spaceShip.setX((spaceShip.x) + speed);
                break;
            case KeyEvent.VK_SPACE:
                if (!isShooting) {
                    isShooting = true;
                    this.spaceShip.shoot();
                }
                break;
           }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S: spaceShip.setSpeedy(0); break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D: spaceShip.setSpeedx(0); break;
            case KeyEvent.VK_SPACE: isShooting = false; break;
        }
    }


}

