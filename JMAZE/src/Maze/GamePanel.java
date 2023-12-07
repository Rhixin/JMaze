package Maze;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public int screen_height = 800;
    public int screen_width = 800;

    public GamePanel() {
        setBackground(Color.BLACK);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(screen_width, screen_height);
    }
}
