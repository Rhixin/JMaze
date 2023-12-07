package Maze;

import javax.swing.*;
import java.awt.*;

public class MapFrame extends JFrame {
    static MapFrameGamePanel mapGamePanel = new MapFrameGamePanel();

    public void start(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Map Frame");
        setContentPane(mapGamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        new Thread(mapGamePanel).start();
    }

}
