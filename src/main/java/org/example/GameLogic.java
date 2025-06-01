package org.example;

import java.awt.*;
import java.util.ArrayList;

public class GameLogic {

    private final UI ui;
    private Slime hero;
    private ArrayList<Entity> enemies = new ArrayList<>();

    public GameLogic(UI ui) {
        this.ui = ui;
        Component[] objects = ui.getEntities();
        for (Component entity: objects){
            if (entity instanceof Slime hero){
                this.hero = hero;
            } else{
                enemies.add((Entity) entity);
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
}
