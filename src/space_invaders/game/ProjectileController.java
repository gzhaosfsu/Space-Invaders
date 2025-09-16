package space_invaders.game;

import space_invaders.GameConstants;
import space_invaders.game.Enemies;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectileController {
    List<Projectile> projectiles;
    GameWorld gameWorld;

    public ProjectileController(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.projectiles = new LinkedList<>();
    }

    public void tick() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.tick();
            if (!projectile.isActive()) {
                iterator.remove(); // Remove inactive projectiles
            } else {
                checkCollisions(projectile);
            }
        }
    }

    private void checkCollisions(Projectile projectile) {
        for (Enemies enemy : gameWorld.getEnemies()) {
            if (projectile.getBounds().intersects(enemy.getBounds()) && enemy.isActive()) {
                enemy.hit(projectile.getDamage());
                projectile.setActive(false);
            }
        }
        if (projectile.getBounds().intersects(GameWorld.spaceShip.getBounds())) {
            GameWorld.spaceShip.takeDamage(projectile.getDamage());
            projectile.setActive(false);
        }
    }

    public void render(Graphics g) {
        for (Projectile projectile : projectiles) {
            projectile.render(g);
        }
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }
}
