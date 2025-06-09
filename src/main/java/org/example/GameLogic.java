package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameLogic {

    private final GamePanel ui;
    private JMap map;
    private Slime hero;
    private ArrayList<Entity> enemies = new ArrayList<>();

    public GameLogic(GamePanel ui) {
        this.ui = ui;
        Component[] objects = ui.getEntities();
        for (Component component: objects){
            if (component instanceof Slime hero){
                this.hero = hero;
            }else if (component instanceof JMap map){
                this.map = map;
            }
            else if (component instanceof Monster monster){
                enemies.add(monster);
            }
        }
    }

    protected void checkHit(){
        Rectangle heroHit =  hero.collision.getHitBox();
        for (Entity entity : enemies) {
            if (entity instanceof Monster enemy) { // Пока только один вид врагов, если будет больше то добавить метод смерти для всех Существ
                Rectangle enemyHurt = enemy.collision.getHurtBox();
                if (heroHit.intersects(enemyHurt)) {
                    enemy.die(); // Смерть или получение урона
                } else {
                    return;
                }
            }
        }
    }

    protected void heroMoved(int dx){
        if (dx>0){
            map.moveRight(dx);
        } else {
            map.moveLeft(dx);
        }
    }

    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                hero.setSpace(true);
                break;
            case KeyEvent.VK_A:
                hero.setA(true);
                hero.setRight(false);
                hero.setMove(true);
                break;
            case KeyEvent.VK_D:
                hero.setD(true);
                hero.setRight(true);
                hero.setMove(true);
                break;
            case KeyEvent.VK_SHIFT:
                hero.setShift(true);
                break;
            case KeyEvent.VK_E:
                hero.setE(true);
                break;

        }
        hero.doIt();
    }

    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_SHIFT -> {
                hero.setShift(false);
            }
            case KeyEvent.VK_D -> {
                hero.setD(false);
                hero.setMove(false);
            }
            case KeyEvent.VK_A -> {
                hero.setA(false);
                hero.setMove(false);
            }
            case KeyEvent.VK_E -> {
                hero.setE(false);
            }
            case KeyEvent.VK_SPACE -> {
                hero.setSpace(false);
            }
        }
    }
}
