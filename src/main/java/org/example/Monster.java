package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Monster extends Entity {

    private MonsterFrameHandler frameHandler;
    private final GamePanel ui;

    private boolean isDie = false;

    public Monster(GamePanel ui) {
        super();
        this.ui = ui;
        collision = new CollisionManager();
        collision.setHurtBox(215, 97, 40, 55);
        frameWidth = 720;
        frameHeight = 490;
        dx = frameWidth;
        nowAnimate = "/Minotaur_01/Idle.png";
        loadAnimations();
        animateFrames = animations.get(nowAnimate).getWidth()/frameWidth;
        frameHandler = new MonsterFrameHandler(this);
    }

    public void startAnimate() {
        frameHandler.start();
    }

    private void loadAnimations() {
        String[] paths = {
                "/Minotaur_01/Idle.png",
                "/Minotaur_01/Die.png",
        };

        for (String path : paths) {
            try {
                animations.put(path, ImageIO.read(getClass().getResourceAsStream(path)));
            } catch (IOException ex) {
                throw new RuntimeException("Error loading " + path);
            }
        }
    }

//    public void idle(){
//        nowAnimate = "/Minotaur_01/Idle.png";
//        action();
//    }

    public void die(){
        if (!isDie){
            nowAnimate = "/Minotaur_01/Die.png";
            action();
            isDie = true;
            frameHandler.setChangeAnimation(true);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentFrame != null) {
            g.drawImage(flip(currentFrame), 0, 0, getWidth(), getHeight(), null);
        }
    }

    public void dieFinished() {
        ui.remove(this);
        ui.repaint();
    }
}

class MonsterFrameHandler extends Thread {

    private final Monster monster;
    private boolean running = true;
    private String currentAnimation;
    private boolean changeAnimation = false;

    public MonsterFrameHandler(Monster monster){
        this.monster = monster;
    }

    @Override
    public void run() {
        whileLoop: while (running) {
            currentAnimation = monster.nowAnimate;

            for (int i = 0; i < monster.getAnimateFrames()-1; i++) {
                if (changeAnimation || !monster.nowAnimate.equals(currentAnimation)) {
                    changeAnimation = false;
                    continue whileLoop;
                }

                monster.animateStep();

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (monster.nowAnimate.equals("/Minotaur_01/Die.png")) {
                monster.dieFinished();
                running = false;
            }
        }
    }

    public void setChangeAnimation(boolean b){
        changeAnimation = b;
    }

    public void stopHandler(){
        running = false;
        interrupt();
    }
}
