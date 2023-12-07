package Maze;

import java.awt.*;

public class Character extends Entity {

    Character(CharacterBuilder builder) {
        super(builder);
    }


    // Builder Class
    public static class CharacterBuilder extends EntityBuilder {


        public CharacterBuilder() {
            super();
        }

        @Override
        public Character build() {
            return new Character(this);
        }
    }

    public void setPosition(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public int getVertex_posx(){
        return pos_x + (width/2);
    }

    public int getVertex_posy(){
        return pos_y + (height/2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(pos_x,pos_y, width, height);

        //LINE HEAD INDICATOR TO KNOW ANGLE
        int lineSize = (Math.min(width, height) / 2) * 5;
        g.setColor(Color.ORANGE);
        g.drawLine(getVertex_posx(), getVertex_posy(),
                getVertex_posx() + (int) (lineSize * Math.cos(angle)),
                getVertex_posy() + (int) (lineSize * Math.sin(angle)));
    }



    public void update_deltas(double rotation){

        angle += rotation;

        // Ensure the angle is within the range [0, 2π)
        if (angle < 0) {
            angle += 2 * Math.PI;
        }

        if (angle > 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }

        delta_x = (int) (speed * Math.cos(angle));
        delta_y = (int) (speed * Math.sin(angle));
    }

    public void move_forward(double seconds) {
        setPosition(
                pos_x + (int) (delta_x * seconds),
                pos_y + (int) (delta_y * seconds)
        );
    }

    public void move_backward(double seconds) {
        setPosition(
                pos_x - (int) (delta_x * seconds),
                pos_y - (int) (delta_y * seconds)
        );
    }

    public void drawRay(int ray_x, int ray_y,Graphics g){
        g.setColor(Color.RED);
        g.drawLine(getVertex_posx(), getVertex_posy(), ray_x, ray_y);
    }



}

