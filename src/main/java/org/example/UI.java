package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UI extends JFrame implements KeyListener {

    private final FrameHandler frameHandler = new FrameHandler();
    private Slime slime = new Slime(this,frameHandler);

    public UI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setPreferredSize(new Dimension(300, 300));
        frameHandler.start();
        slime.setBounds(-40, 134, 128, 128);
        slime.action();
        add(slime);
        pack();

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }

    public void setSlimePosition(int x){
         slime.setLocation(x,slime.getY());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                slime.setSpace(true);
                break;
            case KeyEvent.VK_A:
                slime.setA(true);
                slime.setRight(false);
                slime.setMove(true);
                break;
            case KeyEvent.VK_D:
                slime.setD(true);
                slime.setRight(true);
                slime.setMove(true);
                break;
            case KeyEvent.VK_SHIFT:
                slime.setShift(true);
                break;
            case KeyEvent.VK_E:
                slime.setE(true);
                break;
        }
        slime.doIt();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SHIFT -> {
                slime.setShift(false);
            }
            case KeyEvent.VK_D -> {
                slime.setD(false);
                slime.setMove(false);
            }
            case KeyEvent.VK_A -> {
                slime.setA(false);
                slime.setMove(false);
            }
            case KeyEvent.VK_E -> {
                slime.setE(false);
            }
            case KeyEvent.VK_SPACE -> {
                slime.setSpace(false);
            }
        }
    }
}
