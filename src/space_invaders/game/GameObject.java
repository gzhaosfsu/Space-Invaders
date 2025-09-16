package space_invaders.game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {

    protected double x, y;
    protected int width, height;
    protected boolean isActive = true; // Track if the object is active

    public GameObject(double x, double y, int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be non-negative.");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() { return x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return y; }

    public void setY(double y) { this.y = y; }

    public int getWidth() { return width; }

    public void setWidth(int width) {
        if (width < 0) throw new IllegalArgumentException("Width must be non-negative.");
        this.width = width;
    }

    public int getHeight() { return height; }

    public void setHeight(int height) {
        if (height < 0) throw new IllegalArgumentException("Height must be non-negative.");
        this.height = height;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean isActive) { this.isActive = isActive; }

    public abstract void tick();

    public abstract void render(Graphics g);
}
