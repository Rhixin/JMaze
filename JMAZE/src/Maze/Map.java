package Maze;

import java.awt.*;
import java.util.ArrayList;

public class Map extends GamePanel {
    int map[]= {
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
            1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1,
            1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1,
            1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,1,0,1,1,0,0,0,0,1,0,1,
            1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,
            1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,
            1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,
            1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1,
            1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1,
            1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1,
            1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,
            1,0,0,0,0,1,0,1,1,0,0,0,0,1,0,1,
            1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
    };

    public ArrayList<Wall> walls;

    public Map(){
        walls = new ArrayList<>();
        initializeWalls();
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Wall wall : walls){
            wall.paintComponent(g);
        }
    }



    private void initializeWalls(){
        walls.clear();
        int running_x = 0;
        int running_y = 0;
        for(int i = 0; i < map.length; i++){
            Wall w;
            if(map[i] == 1){
                w = (Wall) new Wall.WallBuilder().position(running_x,running_y).size(50, 50).build();
            } else{
                w = (Wall) new Wall.WallBuilder().solid(0).setColor(Color.BLACK).size(0, 0).position(running_x,running_y).build();
            }

            walls.add(w);

            running_x += (50);
            if( (i+1) % 16 == 0){
                running_x = 0;
                running_y += (50);
            }

        }
    }

}
