package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class JMap extends JPanel {

    private BufferedImage backgroundImage;
    private BufferedImage currentFrame;

    private int x = 230;

    private int maxX;
    private int maxY;

    JMap(){
        setOpaque(true);
        setDoubleBuffered(true);
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Maps/1.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        maxX = backgroundImage.getWidth()-230;
        maxY = backgroundImage.getHeight()-70;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        currentFrame = backgroundImage.getSubimage(230+x, 160, 300, 300);
        currentFrame = backgroundImage.getSubimage(x, backgroundImage.getHeight()-(getHeight()+70), getWidth(), getHeight());
        if (currentFrame != null){
            g.drawImage(currentFrame,0,0,getWidth(),getHeight(),null);
        }

    }

    public void moveRight(int dx){
        if (x<957-getWidth()){
            x+=dx;
            repaint();
        }

    }

    public void moveLeft(int dx){
        if (x>230){
            x+=dx;
            repaint();
        }

    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
