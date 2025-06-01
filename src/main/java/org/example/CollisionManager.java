package org.example;

import java.awt.*;

public class CollisionManager{

    protected Rectangle hitBox;
    protected Rectangle hurtBox;

    public void setHitBox(int x, int y,int width, int height) {
        hitBox = new Rectangle(width,height);
        hitBox.setLocation(x,y);
    }

    public void setHitBoxLocation(int x, int y){
        hitBox.setLocation(x,y);
    }

    public void setHurtBox(int x, int y,int width, int height) {
        hurtBox = new Rectangle(width,height);
        hurtBox.setLocation(x,y);
    }

    public void setHurtBoxLocation(int x, int y){
        hurtBox.setLocation(x,y);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Rectangle getHurtBox() {
        return hurtBox;
    }
}
