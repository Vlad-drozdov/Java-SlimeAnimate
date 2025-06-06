package org.example;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage spriteSheet;
    private int frameWidth, frameHeight;
    private int frameCount;
    private int currentFrame = 0;

    private long frameDuration;

    public Animation(BufferedImage sheet, int frameWidth, int frameHeight, int frameCount, long frameDuration) {
        this.spriteSheet = sheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;
    }

    public void update() {
        currentFrame = (currentFrame + 1) % frameCount;
    }

    public void reset() {
        currentFrame = 0;
    }

    public BufferedImage getCurrentFrame() {
        int x = currentFrame * frameWidth;
        return spriteSheet.getSubimage(x, 0, frameWidth, frameHeight);
    }

    public int getFrameCount() {
        return frameCount;
    }

}
