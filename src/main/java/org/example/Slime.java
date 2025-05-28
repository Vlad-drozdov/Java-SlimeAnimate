package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Slime extends JPanel {

    private BufferedImage spriteSheet;
    private BufferedImage currentFrame;
    private final int frameWidth = 128;
    private final int frameHeight = 128;
    private int frameX = 0;
    private final int frameY = 0;
    private final int dx = 128;
    private final int moveDx = 7;

    private final FrameHandler frameHandler;
    private final UI ui;
    private final Map<String, BufferedImage> animations = new HashMap<>();

    private String nowAnimate = "/Blue_Slime/Idle.png";
    private String previousAnimate = "";
    private int animateFrames = 8;
    private boolean isRight = true;
    private boolean isMove = false;
    private boolean isJump = false;
//    private boolean isIdle = true;

    private boolean space = false;
    private boolean shift = false;
    private boolean d = false;
    private boolean a = false;
    private boolean e = false;

    public Slime(UI ui, FrameHandler frameHandler) {
        this.frameHandler = frameHandler;
        this.ui = ui;
        loadAnimations();
        frameHandler.addObject(this);
    }

    private void loadAnimations() {
        String[] paths = {
                "/Blue_Slime/Idle.png",
                "/Blue_Slime/Walk.png",
                "/Blue_Slime/Run.png",
                "/Blue_Slime/Jump.png",
                "/Blue_Slime/Attack_1.png"
        };

        for (String path : paths) {
            try {
                animations.put(path, ImageIO.read(getClass().getResource(path)));
            } catch (IOException ex) {
                throw new RuntimeException("Error loading " + path);
            }
        }
    }

    public BufferedImage flip(BufferedImage original) {
        int w = original.getWidth();
        int h = original.getHeight();
        BufferedImage flipped = new BufferedImage(w, h, original.getType());
        Graphics2D g = flipped.createGraphics();
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-w, 0);
        g.drawImage(original, transform, null);
        g.dispose();
        return flipped;
    }

    public void doIt() {
        if (e) {
            nowAnimate = "/Blue_Slime/Attack_1.png";
        } else if (a || d) {
            isMove = true;
            if (space){
                nowAnimate ="/Blue_Slime/Jump.png";
            }
            else {
                nowAnimate = shift ? "/Blue_Slime/Run.png" : "/Blue_Slime/Walk.png";
            }
        } else if (space) {
            nowAnimate = "/Blue_Slime/Jump.png";
        } else {
            nowAnimate = "/Blue_Slime/Idle.png";
        }

        if (!nowAnimate.equals(previousAnimate)) {
            previousAnimate = nowAnimate;
            action();
        }
    }

    public void move() {
        int x = getX();
        int y = getY();

        int panelWidth = ui.getWidth();      // ширина родительского окна
        int slimeWidth = getWidth();         // ширина самого слайма
        int maxRight = panelWidth - slimeWidth;
        int maxLeft = 0;

        double speed = moveDx;

        if (nowAnimate.equals("/Blue_Slime/Run.png")) {
            speed *= 1.5;
        }

        if (isRight) {
            // Движение вправо
            if (x + speed <= maxRight+32) {
                setLocation((int) (x + speed), y);
            } else {
                setLocation(maxRight+32, y);
            }
        } else {
            // Движение влево
            if (x - speed >= maxLeft-38) {
                setLocation((int) (x - speed), y);
            } else {
                setLocation(maxLeft-38, y);
            }
        }
    }


    public void action() {
        spriteSheet = animations.get(nowAnimate);
        animateFrames = spriteSheet.getWidth() / frameWidth;
        frameX = 0;
        currentFrame = spriteSheet.getSubimage(frameX, frameY, frameWidth, frameHeight);
    }

    protected void animateStep() {
        frameX += dx;
        if (frameX >= spriteSheet.getWidth()) {
            frameX = 0;
        }
        currentFrame = spriteSheet.getSubimage(frameX, frameY, frameWidth, frameHeight);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentFrame != null) {
            if (!isRight) {
                g.drawImage(flip(currentFrame), 0, 0, getWidth(), getHeight(), null);
            } else {
                g.drawImage(currentFrame, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }

    public void setRight(boolean right) { isRight = right; }

    public void setE(boolean e) { this.e = e; }

    public void setA(boolean a) { this.a = a; }

    public void setD(boolean d) { this.d = d; }

    public void setSpace(boolean space) { this.space = space; }

    public void setShift(boolean shift) { this.shift = shift; }

//    public boolean isShift() { return shift; }

    public int getAnimateFrames() { return animateFrames; }

    public boolean isMove() { return isMove; }

    public void setMove(boolean move) { isMove = move; }
}
