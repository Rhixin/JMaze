package Maze;

import java.awt.*;

public class Wall extends Entity{
    public int solid;

    private Wall(WallBuilder builder) {
        super(builder);
        this.color = builder.color;
        this.solid = builder.solid;
    }

    // Builder Class for Wall
    public static class WallBuilder extends EntityBuilder {

        private int solid;

        public WallBuilder() {
            super(); // Call the constructor of the superclass (EntityBuilder)
            solid = 1;
        }


        public WallBuilder solid(int solid) {
            this.solid = solid;
            return this;
        }


        @Override
        public Wall build() {
            return new Wall(this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(pos_x,pos_y, width, height);
    }



}
