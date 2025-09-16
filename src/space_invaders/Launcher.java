package space_invaders;

import space_invaders.game.GameWorld;
import space_invaders.game.ShipControl;
import space_invaders.menus.EndGamePanel;
import space_invaders.menus.StartMenuPanel;
import space_invaders.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

public class Launcher {

    private JPanel mainPanel;
    private GameWorld gamePanel;
    private final JFrame jf;
    private CardLayout cl;

    public Launcher() {
        this.jf = new JFrame("Space Wars"); // Set title in constructor
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
    }

    private void initUIComponents() {
        this.mainPanel = new JPanel();
        JPanel startPanel = new StartMenuPanel(this);
        this.gamePanel = new GameWorld(); // Create game panel but do not start it yet
        JPanel endPanel = new EndGamePanel(this);

        cl = new CardLayout();
        this.mainPanel.setLayout(cl);
        this.mainPanel.add(startPanel, "start");
        this.mainPanel.add(gamePanel, "game");
        this.mainPanel.add(endPanel, "end");

        this.jf.add(mainPanel);
        this.jf.setResizable(false); // Set JFrame to be non-resizable
        setFrame("start"); // Show the start menu panel
    }

    public void setFrame(String type) {
        this.jf.setVisible(false); // Hide the JFrame temporarily

        switch (type) {
            case "start":
                this.jf.setSize(GameConstants.START_MENU_SCREEN_WIDTH, GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "game":
                this.jf.setSize(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
                if (!this.gamePanel.isRunning()) { // Ensure game panel is not already running
                    (new Thread(this.gamePanel)).start(); // Start the game loop in a new thread
                }
                break;
            case "end":
                this.jf.setSize(GameConstants.END_MENU_SCREEN_WIDTH, GameConstants.END_MENU_SCREEN_HEIGHT);
                break;
        }

        this.cl.show(mainPanel, type); // Show the specified panel
        this.jf.setVisible(true); // Make the JFrame visible again
    }

    public JFrame getJf() {
        return jf;
    }

    public void closeGame() {
        this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWorld game = new GameWorld();
            game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
            game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
            game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
            JFrame frame = new JFrame(TITLE);
            KeyListener ShipControl = new ShipControl(game.getSpaceShip(), KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
            frame.addKeyListener(ShipControl);
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
            frame.setFocusable(true);
            game.requestFocusInWindow(); // Request focus for the game panel
            game.start();
        });
        });
    }
}
