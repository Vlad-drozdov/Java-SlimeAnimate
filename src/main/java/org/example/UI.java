package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UI extends JFrame implements KeyListener {

    private final GameLogic gameLogic;

    private final Slime slime;
    private final Monster monster;

    private Component[] components;

    public UI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - 150, screenSize.height / 2 - 150);
        setPreferredSize(new Dimension(300, 300));

        slime = new Slime(this);
        monster = new Monster(this);

        slime.setBounds(-40, 134, 128, 128);
        monster.setBounds(170, 182, 128, 87);

        slime.action();
        monster.action();

        add(slime);
        add(monster);
        pack();

        components = getContentPane().getComponents();

        gameLogic = new GameLogic(this);
        slime.setGameLogic(gameLogic);
        monster.setGameLogic(gameLogic);

        slime.startAnimate();
        monster.startAnimate();



        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
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

    public Component[] getEntities() {
        return components;
    }
}
