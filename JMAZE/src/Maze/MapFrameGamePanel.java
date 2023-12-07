package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class MapFrameGamePanel extends GamePanel implements Runnable{

    //<-------------GAME LOOP OPTIONS------------>
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private static final double MAX_DISTANCE = 100000.00;
    private boolean isRunning = true;
    private static final int TOTAL_RAYS = 200;
    private static final int TOTAL_TILES = 16*16;


    //<--------------GAME OBJECTS----------------->
    private Map map = new Map();
    private Character mainCharacter = (Character) new Character.CharacterBuilder().position(75, 75).build();
    private screen3D screen = new screen3D(TOTAL_RAYS);

    //<------STORE KEYS TO RESPECT GAME LOOP----->
    private static Set<Integer> pressedKeys = new HashSet<>();



    //<--------------CONSTRUCTOR----------------->
    public MapFrameGamePanel() {
        setupKeyBindings();
        setPreferredSize(getPreferredSize());
        setFocusable(true);
        requestFocusInWindow();
    }

    //<------------RENDERING OF OBJECTS IN SCREEN---------->
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //map.paintComponent(g);
        screen.paintComponents(g);
        //mainCharacter.paintComponent(g);
        drawrays3D(g);

    }


    //<--------------GAME LOOP------------------->
    public void run() {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;

        while (isRunning) {
            long now = System.nanoTime();
            long elapsedTime = now - lastLoopTime;
            lastLoopTime = now;

            lastFpsTime += elapsedTime;
            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
            }

            updateGameLogic();
            processInput(elapsedTime);
            repaint();

            try {
                long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //<--------------GAME LOGIC------------------->
    public void updateGameLogic(){
        //drawrays3D(getGraphics());
    }

    private boolean collidesWithWalls(int deltaX, int deltaY) {

        int characterCenterX = deltaX + mainCharacter.pos_x + 5;
        int characterCenterY = deltaY + mainCharacter.pos_y + 5;

        for (Wall wall : map.walls) {
            if(wall.solid == 0){
                continue;
            }
            int wallCenterX = wall.pos_x + wall.width / 2;
            int wallCenterY = wall.pos_y + wall.height / 2;

            int halfCharacterWidth = mainCharacter.width / 2;
            int halfCharacterHeight = mainCharacter.height / 2;
            int halfWallWidth = wall.width / 2;
            int halfWallHeight = wall.height / 2;


            if (Math.abs(characterCenterX - wallCenterX) < (halfCharacterWidth +halfWallWidth) &&
                    Math.abs(characterCenterY - wallCenterY) < (halfCharacterHeight +halfWallHeight)) {
                return true;
            }
        }

        return false;
    }


    private void drawrays3D(Graphics g) {
        // Assuming each tile is 50 pixels wide and 50 pixels high
        int tileWidth = 50;
        int tileHeight = 50;

        int  ray_posx = 0, ray_posy = 0;
        int dof = 0, x_offset = 1, y_offset = 1, mx, my, mp;
        double ray_space = 0.0174533 / 5.0;
        double ray_angle;
        double lineDistance = 0;

        ray_angle = mainCharacter.angle - ray_space * 150;

        if(ray_angle < 0){
            ray_angle += 2 * Math.PI;
        }
        if(ray_angle > Math.PI * 2){
            ray_angle -= Math.PI * 2;
        }


        for (int r = 0; r < TOTAL_RAYS; r++) {
            //VERTICAL
            dof = 0;

            double distV = MAX_DISTANCE;
            int vx = mainCharacter.pos_x;
            int vy = mainCharacter.pos_y;
            double nTan = -Math.tan(ray_angle);

            //looking left
            if (ray_angle > Math.PI / 2 && ray_angle < 3 * Math.PI / 2) {
                ray_posx = (((mainCharacter.getVertex_posx() / tileWidth) * tileWidth) - 1);
                ray_posy = (int) ((mainCharacter.getVertex_posx() - ray_posx) * nTan + mainCharacter.getVertex_posy());
                x_offset = -tileWidth;
                y_offset = (int) Math.round( -x_offset * nTan);
            }

            //looking right
            if (ray_angle < Math.PI / 2 || ray_angle > 3 * Math.PI / 2) {
                ray_posx = (int) ((((int) mainCharacter.getVertex_posx() / tileWidth) * tileWidth) + tileWidth);
                ray_posy = (int) ((mainCharacter.getVertex_posx() - ray_posx) * nTan + mainCharacter.getVertex_posy());
                x_offset = tileWidth;
                y_offset = (int) Math.round(-x_offset * nTan);
            }

            //looking staright up or down
            if (ray_angle == Math.PI || ray_angle == Math.PI*2) {
                ray_posx = mainCharacter.getVertex_posx();
                ray_posy = mainCharacter.getVertex_posy();
                dof = 15;
            }


            while (dof < 16) {
                mp = getIndexofPoints(ray_posx, ray_posy, x_offset, y_offset);

                if (mp >= 0 && mp < (TOTAL_TILES) && map.map[mp] == 1) {
                    vx=ray_posx;
                    vy=ray_posy;
                    distV = getDistance(mainCharacter.pos_x, mainCharacter.pos_y, vx,vy,ray_angle);
                    break;
                } else {
                    ray_posx += x_offset;
                    ray_posy += y_offset;
                    dof += 1;
                }

            }

            //HORIZONTAL
            dof = 0;
            double distH = MAX_DISTANCE;
            int hx = mainCharacter.pos_x;
            int hy = mainCharacter.pos_y;

            double aTan = -1.0 / Math.tan(ray_angle);

            //looking up
            if (ray_angle > Math.PI) {
                ray_posy = (int) ((((int) mainCharacter.getVertex_posy() / tileHeight) * tileHeight) - 1);
                ray_posx = (int) ((mainCharacter.getVertex_posy() - ray_posy) * aTan + mainCharacter.getVertex_posx());
                y_offset = -tileHeight;
                x_offset = (int) Math.round (-y_offset * aTan);
            }

            //looking down
            if (ray_angle < Math.PI) {
                ray_posy = (int) ((((int) mainCharacter.getVertex_posy() / tileHeight) * tileHeight) + tileHeight);
                ray_posx = (int) ((mainCharacter.getVertex_posy() - ray_posy) * aTan + mainCharacter.getVertex_posx());
                y_offset = tileHeight;
                x_offset = (int) Math.round( -y_offset * aTan);
            }

            //looking straight left or right
            if (ray_angle == 0 || ray_angle == Math.PI) {
                ray_posx = mainCharacter.getVertex_posx();
                ray_posy = mainCharacter.getVertex_posy();
                break;
            }

            while (dof < 16) {
                mp = getIndexofPoints(ray_posx, ray_posy, x_offset,y_offset);


                if (mp >= 0 && mp < TOTAL_TILES && map.map[mp] == 1) {
                    hx=ray_posx;
                    hy=ray_posy;
                    distH = getDistance(mainCharacter.pos_x, mainCharacter.pos_y, hx,hy,ray_angle);
                    break;
                } else {
                    ray_posx += x_offset;
                    ray_posy += y_offset;
                    dof += 1;
                }

            }

            //<--------------------------------->
            //COMPARE HORIZONTAL - VERTICAL
            int winner;
            Color color = Color.WHITE;
            if(distV < distH){
                ray_posx=vx;
                ray_posy=vy;
                lineDistance = distV;
                winner = 1;
            } else {
                ray_posx=hx;
                ray_posy=hy;
                lineDistance = distH;
                winner = 2;
            }

            //mainCharacter.drawRay(ray_posx, ray_posy, g);
            //<--------------------------------->

            //<-------Draw 3D------------->
            //Fix Fisheye effect
            double cast_angle = mainCharacter.angle - ray_angle;
            if (cast_angle < 0) {
                cast_angle += Math.PI * 2;
            }

            if (cast_angle > Math.PI * 2) {
                cast_angle -= Math.PI * 2;
            }

            lineDistance = lineDistance * Math.cos(cast_angle);

            double lineH = (50 * screen.screen_height) / lineDistance;

            double ty_step =  50.0 / (double) lineH;
            double ty_off = 0;

            if (lineH > screen.screen_height) {
                ty_off = (lineH - screen.screen_height) / 2.0;
                lineH = screen.screen_height;
            }
            double line_Offset = (screen.screen_height - lineH) / 2;
            double percent_shade = lineH / screen.screen_height;

            screen.drawVerticalPixel(g, r * (screen_width/TOTAL_RAYS), (int) line_Offset, (int) lineH, winner, lineDistance, ty_step, ty_off, ray_posx, ray_posy, ray_angle, percent_shade);

            //Prepare next ray
            ray_angle += ray_space;
            if(ray_angle < 0){
                ray_angle += 2 * Math.PI;
            }
            if(ray_angle > Math.PI * 2){
                ray_angle -= Math.PI * 2;
            }
        }
    }

    private int getIndexofPoints(int ray_x, int ray_y, int x_offset, int y_offset){
        if(x_offset != 0 && y_offset != 0){
            ray_x = ray_x + (x_offset / Math.abs(x_offset));
            ray_y = ray_y + (y_offset / Math.abs(y_offset));
        }

        int multi_y = ray_x / 50;
        int multi_x = ray_y / 50;
        int mp = multi_x * 16 + multi_y;

        return mp;
    }



    private double getDistance(int ax, int ay, int bx, int by, double angle){
        double dx = bx - ax;
        double dy = by - ay;

        double rotatedX = dx * Math.cos(angle) - dy * Math.sin(angle);
        double rotatedY = dx * Math.sin(angle) + dy * Math.cos(angle);

        return Math.sqrt(rotatedX * rotatedX + rotatedY * rotatedY);
    }




    //<-------MAIN CHARACTER KEY LISTENERS-------->

    private void processInput(long elapsedTime) {
        double seconds = elapsedTime / 1_000_000_000.0;
        int new_x = (int) (mainCharacter.delta_x * seconds);
        int new_y = (int) (mainCharacter.delta_y * seconds);
        float rotation_speed = 1.5F;

        if (pressedKeys.contains(KeyEvent.VK_W)) {
            if (!collidesWithWalls(new_x, new_y)) {
                mainCharacter.move_forward(seconds);
            }

        }

        if (pressedKeys.contains(KeyEvent.VK_A)) {
            mainCharacter.update_deltas(-rotation_speed * seconds);
        }

        if (pressedKeys.contains(KeyEvent.VK_S)) {
            if (!collidesWithWalls(-new_x, -new_y)) {
                mainCharacter.move_backward(seconds);
            }
        }

        if (pressedKeys.contains(KeyEvent.VK_D)) {
            mainCharacter.update_deltas(rotation_speed * seconds);
        }
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Key binding for W
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "W released");
        actionMap.put("W pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.add(KeyEvent.VK_W);
            }
        });
        actionMap.put("W released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.remove(KeyEvent.VK_W);
            }
        });

        // Key binding for A
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");
        actionMap.put("A pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.add(KeyEvent.VK_A);
            }
        });
        actionMap.put("A released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.remove(KeyEvent.VK_A);
            }
        });

        // Key binding for S
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "S pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "S released");
        actionMap.put("S pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.add(KeyEvent.VK_S);
            }
        });
        actionMap.put("S released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.remove(KeyEvent.VK_S);
            }
        });

        // Key binding for D
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D released");
        actionMap.put("D pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.add(KeyEvent.VK_D);
            }
        });
        actionMap.put("D released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                pressedKeys.remove(KeyEvent.VK_D);
            }
        });
    }



}
