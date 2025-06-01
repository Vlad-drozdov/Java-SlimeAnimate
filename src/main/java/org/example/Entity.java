package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity extends JPanel implements EntityInterface {

    protected CollisionManager collision;
    private GameLogic logic;

    protected BufferedImage spriteSheet;
    protected BufferedImage currentFrame;

    protected int frameWidth;
    protected int frameHeight;
    protected int frameX = 0;
    protected int frameY = 0;
    protected int dx;

    protected int animateFrames;
    protected String nowAnimate;
    protected final Map<String, BufferedImage> animations = new HashMap<>();

    public Entity() {
        setOpaque(false);
    }

    public int getAnimateFrames() {
        return animateFrames;
    }

    @Override
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

    public BufferedImage flip(BufferedImage original) {
        int w = original.getWidth();
        int h = original.getHeight();
        BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = flipped.createGraphics();
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-w, 0);
        g.drawImage(original, transform, null);
        g.dispose();
        return flipped;
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }

    public void setGameLogic(GameLogic logic) {
        this.logic = logic;
    }

    public GameLogic getGameLogic() {
        return logic;
    }
}