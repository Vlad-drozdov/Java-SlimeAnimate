package org.example;

import java.util.ArrayList;

public class FrameHandler extends Thread {

    private final ArrayList<Slime> slimes = new ArrayList<>();

    public void addObject(Slime slime) {
        slimes.add(slime);
    }

    @Override
    public void run() {
        while (true) {
            for (Slime slime : slimes) {
                for (int i = 0; i < slime.getAnimateFrames(); i++) {
                    slime.animateStep();
                    if (slime.isMove()) slime.move();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("Frame_Update_Error");
                    }
                }
                slime.doIt();
            }
        }
    }
}
