package Maze;

import javax.swing.*;
import java.awt.*;

public class Entity extends JPanel {
    public static double z_counter = 0.0;
    public int pos_x;
    public int pos_y;
    public int delta_x;
    public int delta_y;
    public int width;
    public int height;
    public double angle;
    public Color color;
    public double speed;
    public double z_index;

    Entity(EntityBuilder builder) {
        this.pos_x = builder.pos_x;
        this.pos_y = builder.pos_y;
        this.delta_x = builder.delta_x;
        this.delta_y = builder.delta_y;
        this.width = builder.width;
        this.height = builder.height;
        this.angle = builder.angle;
        this.color = Color.WHITE;
        this.speed = builder.speed;
        this.z_index = builder.z_index;
    }

    // Builder Class
    public static class EntityBuilder {
        private int pos_x;
        private int pos_y;
        private int delta_x;
        private int delta_y;
        int width;
        int height;
        private double angle;
        public Color color;
        public double speed;
        public double z_index;

        public EntityBuilder() {
            // Default values
            pos_x = 300;
            pos_y = 400;
            delta_x = 60;
            delta_y = 60;
            width = 6;
            height = 6;
            angle = Math.PI;
            color = Color.WHITE;
            speed = 100;
            z_index = 0;
        }

        public EntityBuilder position(int x, int y) {
            this.pos_x = x;
            this.pos_y = y;
            return this;
        }

        public EntityBuilder delta(int dx, int dy) {
            this.delta_x = dx;
            this.delta_y = dy;
            return this;
        }

        public EntityBuilder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public EntityBuilder angle(double angle) {
            this.angle = angle;
            return this;
        }

        public EntityBuilder setColor(Color color) {
            this.color = color;
            return this;
        }

        public EntityBuilder speed(double speed) {
            this.speed = speed;
            return this;
        }

        public EntityBuilder z_index(double z_index) {
            this.z_index = z_index;
            return this;
        }

        public Entity build() {
            return new Entity(this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(pos_x,pos_y, width, height);
    }

    public void showEntity(){
        repaint();
    }

    public void assign_zindex(){
        z_index = z_counter;
        z_counter += 0.1;
    }


}

