package Maze;

import Helpers.ShadeMaker;
import Helpers.Texture;

import java.awt.*;
import java.util.HashMap;

public class screen3D extends GamePanel{
    private int vertical_pixels;
    private ShadeMaker shader = new ShadeMaker();
    private static Texture texture = new Texture();
    private HashMap<Integer, Color> colorsTexture1;
    private int[] arrayTexture1 = texture.readIntArrayFromFile("C:/Users/Zhazted Rhixin/IdeaProjects/JMAZE/src/Textures/Wall.txt");

    public screen3D(){
        vertical_pixels = 300;
        setBackground(Color.BLUE);
    }

    public screen3D(int num_rays){
        vertical_pixels = num_rays;
        setBackground(Color.BLUE);
        colorsTexture1 = hashColors(arrayTexture1);
    }

    private HashMap<Integer, Color> hashColors(int[] wall_texture_final){
        HashMap<Integer, Color> colorMap = new HashMap<>();

        for (int i = 0; i < wall_texture_final.length; i += 3) {
            int textureIndex = i / 3;
            int color_r = wall_texture_final[i];
            int color_g = wall_texture_final[i + 1];
            int color_b = wall_texture_final[i + 2];

            Color color = new Color(color_r, color_g, color_b);
            colorMap.put(textureIndex, color);
        }

        return colorMap;
    }


    public void drawVerticalPixel(Graphics g, int x, int lineOffset, int lineHeight,int winner, double lineDistance, double ty_step, double ty_off, int ray_posx, int ray_posy, double ray_angle, double percent){
        double texture_y = ty_step * ty_off;
        double texture_x;
        if(winner == 1){
            texture_x = ray_posy % 50;
            if (ray_angle > Math.PI / 2.0 && ray_angle < Math.PI * 3.0 / 2.0) {
                texture_x = 49 - texture_x;
            }
        } else{
            texture_x = ray_posx % 50;
            if (ray_angle < Math.PI) {
                texture_x = 49 - texture_x;
            }
        }


        double texture_step = ty_step;
        int pixel_width = screen_width/vertical_pixels;
        Color c;
        int index;

        for(int z = 0; z < lineHeight; z++){
            index = ((int) (texture_y) * 50 + (int) texture_x);


            c = shader.shade(colorsTexture1.get(index), percent * 1.5);

            if (lineDistance >= 350){
                c = Color.BLACK;
            }

            g.setColor(c);
            g.fillRect(x, (int) (z + lineOffset), pixel_width, 1);
            texture_y += texture_step;
        }



    }


}
