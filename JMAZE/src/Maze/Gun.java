package Maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gun extends Entity{
    private double damage;
    private int bullets;
    private double cooldown;
    private BufferedImage backgroundImage;

    Gun(Gun.GunBuilder builder) {
        super(builder);
    }

    protected void paintComponent(Graphics2D g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            // Specify the portion of the image to draw (adjust these values accordingly)
            int sourceX = 0;
            int sourceY = 0;
            int sourceWidth = 100;  // adjust to the desired width
            int sourceHeight = 100; // adjust to the desired height

            // Specify the destination rectangle
            int destinationX = 0;
            int destinationY = 0;
            int destinationWidth = getWidth();
            int destinationHeight = getHeight();

            g.drawImage(backgroundImage, destinationX, destinationY, destinationWidth, destinationHeight,
                    sourceX, sourceY, sourceX + sourceWidth, sourceY + sourceHeight, this);
        }

    }

    public static class GunBuilder extends EntityBuilder {
        private double damage;
        private int bullets;
        private double cooldown;

        private BufferedImage backgroundImage;


        public GunBuilder(){
            super();
            damage = 10;
            bullets = 5;
            cooldown = 3;
            try {
                backgroundImage = ImageIO.read(new File("../Animations/Shotgun.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public GunBuilder damage(double damage) {
            this.damage = damage;
            return this;
        }

        public GunBuilder cooldown(double cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public GunBuilder bullets(int bullets) {
            this.bullets = bullets;
            return this;
        }

        public GunBuilder backgroundImage(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        @Override
        public Gun build() {
            return new Gun(this);
        }
    }
}
