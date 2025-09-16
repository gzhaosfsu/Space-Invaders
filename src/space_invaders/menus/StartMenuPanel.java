package space_invaders.menus;

import space_invaders.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher launcher;

    public StartMenuPanel(Launcher launcher) {
        this.launcher = launcher;
        loadBackgroundImage();
        initializePanel();
    }

    private void loadBackgroundImage() {
        try {
            // Load the start menu background image
            menuBackground = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("ufo.png")));
        } catch (IOException e) {
            System.err.println("Error loading menu background image");
            e.printStackTrace();
            // Consider alternative error handling here
        }
    }

    private void initializePanel() {
        setBackground(Color.BLACK);
        setLayout(null);

        JButton startButton = createButton("Start", 150, 300, e -> launcher.setFrame("game"));
        JButton exitButton = createButton("Exit", 150, 400, e -> launcher.closeGame());

        add(startButton);
        add(exitButton);
    }

    private JButton createButton(String text, int x, int y, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Courier New", Font.BOLD, 24));
        button.setBounds(x, y, 250, 50);  // Adjusted size to match design
        button.addActionListener(actionListener);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (menuBackground != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(menuBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
