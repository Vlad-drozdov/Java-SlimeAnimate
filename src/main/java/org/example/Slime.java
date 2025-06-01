package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Slime extends Entity {

    private final SlimeFrameHandler frameHandler;
    private final UI ui;

    private final int moveDx = 7;

    private String previousAnimate = "";

    private boolean isRight = true;
    private boolean isMove = false;
    private boolean isJump = false;
    private boolean isAttack = false;
//    private boolean isIdle = true;

    private boolean space = false;
    private boolean shift = false;
    private boolean d = false;
    private boolean a = false;
    private boolean e = false;

    public Slime(UI ui) {
        super();
        this.ui = ui;
        collision = new CollisionManager();
        collision.setHitBox(getX()+84,getY()+97,15,18);
        frameWidth = 128;
        frameHeight = 128;
        dx = 128;
        nowAnimate = "/Blue_Slime/Idle.png";
        loadAnimations();
        animateFrames = animations.get(nowAnimate).getWidth()/frameWidth;
        frameHandler = new SlimeFrameHandler(this);
    }

    public void startAnimate(){
        frameHandler.start();
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

    public void doIt() {
//        if (isJump) return;

        if (e) {
            nowAnimate = "/Blue_Slime/Attack_1.png";
            isAttack = true;
        } else if (a || d) {
            isMove = true;
            if (space){
                nowAnimate ="/Blue_Slime/Jump.png";
//                isJump = true;
            }
            else {
                nowAnimate = shift ? "/Blue_Slime/Run.png" : "/Blue_Slime/Walk.png";
            }
        } else if (space) {
            nowAnimate = "/Blue_Slime/Jump.png";
//            isJump = true;
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

//        int hitBoxX = collision.getHitBox().x;
        int hitBoxY = collision.getHitBox().y;

        int panelWidth = ui.getWidth();
        int slimeWidth = getWidth();
        int maxRight = panelWidth - slimeWidth;
        int maxLeft = 0;

        double speed = moveDx;

        if (nowAnimate.equals("/Blue_Slime/Run.png")) {
            speed *= 1.5;
        }

        if (isRight) {
            if (x + speed <= maxRight+32) {
                setLocation((int) (x + speed), y);
            } else {
                setLocation(maxRight+32, y);
            }
            collision.setHitBoxLocation(x + 84, hitBoxY);
        }
        else {
            if (x - speed >= maxLeft-38) {
                setLocation((int) (x - speed), y);
            } else {
                setLocation(maxLeft-38, y);
            }
            collision.setHitBoxLocation(x + 26, hitBoxY);
        }
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



    public void setE(boolean e) { this.e = e; }

    public void setA(boolean a) { this.a = a; }

    public void setD(boolean d) { this.d = d; }

    public void setSpace(boolean space) { this.space = space; }

    public void setShift(boolean shift) { this.shift = shift; }

//    public boolean isShift() { return shift; }



    public void setRight(boolean right) { isRight = right; }

    public boolean isRight() { return isRight; }

    public boolean isMove() { return isMove; }

    public void setMove(boolean move) { isMove = move; }

    public boolean isJump() { return isJump; }

    public void setJump(boolean jump) { isJump = jump; }

    public boolean isAttack() { return isAttack; }

    public void setAttack(boolean b) { isAttack = b; }
}

class SlimeFrameHandler extends Thread {

    private final Slime slime;

    public SlimeFrameHandler(Slime slime){
        this.slime = slime;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < slime.getAnimateFrames()-1; i++) {
                slime.animateStep();
                if (slime.isMove()) slime.move();
                if (slime.isAttack())
                    if (i == slime.getAnimateFrames()-2) slime.getGameLogic().checkHit();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (slime.isAttack()) slime.setAttack(false);
//            if (slime.isJump()){
//                slime.setJump(false);
//            }

            slime.doIt();
        }
    }
}

