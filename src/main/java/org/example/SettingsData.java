package org.example;

public class SettingsData {

    private int GameX;
    private int GameY;
    private int width;
    private int height;

    private boolean musicOn = true;


    public int getGameX() { return GameX; }
    public void setGameX(int gameX) { GameX = gameX; }

    public int getGameY() { return GameY; }
    public void setGameY(int gameY) { GameY = gameY; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public boolean isMusicOn() { return musicOn; }
    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }
}
