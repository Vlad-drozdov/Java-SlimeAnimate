package org.example;

import javax.swing.*;
import java.awt.*;

public class SettingsUI extends JDialog {

    private GamePanel game;
    private GameMusic music;
    private SettingsData data;

    private JTextField widthField;
    private JTextField heightField;
    private JCheckBox musicOn;


    public SettingsUI(JFrame owner, GameMusic music, SettingsData data) {
        super(owner, "Settings", true);
        game = (GamePanel) owner;
        this.music = music;
        this.data = data;


        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(300, 300);

        setLocation(data.getGameX() + (data.getWidth() / 2) - (getWidth() / 2), data.getGameY() + (data.getHeight() / 2) - (getHeight() / 2));

        JLabel screenSize = new JLabel("Game screen size");
        screenSize.setBounds(10, 10, 120, 20);
        add(screenSize);


        widthField = new JTextField(String.valueOf(data.getWidth()));
        widthField.setBounds(140, 7, 50, 25);
        add(widthField);

        heightField = new JTextField(String.valueOf(data.getHeight()));
        heightField.setBounds(200, 7, 50, 25);
        add(heightField);

        JLabel musicLabel = new JLabel("Music");
        musicLabel.setBounds(10, 40, 40, 20);
        add(musicLabel);

        musicOn = new JCheckBox();
        musicOn.setBounds(60,40,20,20);
        musicOn.setSelected(data.isMusicOn());
        add(musicOn);

        JPanel choosePanel = createChoosePanel();
        add(choosePanel);

        setFocusable(true);
        setVisible(true);
    }

    private JPanel createChoosePanel() {
        JPanel choosePanel = new JPanel();
        choosePanel.setLayout(null);
        choosePanel.setBounds(40, 220, 210, 30);

        JButton ok = new JButton("Submit");
        ok.setBounds(0, 0, 100, 30);
        ok.setForeground(Color.GREEN);
        ok.addActionListener(e -> {

            editWindowSize();
            musicOnOff();
            dispose();

        });
        choosePanel.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(110, 0, 100, 30);
        cancel.setForeground(Color.RED);
        cancel.addActionListener(e -> dispose());
        choosePanel.add(cancel);

        return choosePanel;
    }

    private void editWindowSize() {
        int width = Integer.parseInt(widthField.getText());
        int height = Integer.parseInt(heightField.getText());

        int mapWidth = game.getMap().getMaxX();
        int mapHeight = game.getMap().getMaxY();

        if (width>mapWidth) width = mapWidth;
        else if (width<300) width = 300;

        if (height>mapHeight) height = mapHeight;
        else if (height<300) height = 300;

        game.setSize(width, height);
        game.updateLayout();

        game.revalidate();
        game.repaint();
    }

    private void musicOnOff(){
        if (musicOn.isSelected()){
            music.play();
        }
        else{
            music.stop();
        }
    }
}
