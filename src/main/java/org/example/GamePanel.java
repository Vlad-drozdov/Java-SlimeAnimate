package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;


public class GamePanel extends JFrame implements KeyListener, ActionListener {

    private SettingsData settingsData;

    private final JMap map = new JMap();

    private final GameLogic logic;

    private final Slime slime;
    private final Monster monster;

    private Component[] components;

    public GamePanel() {
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(600,400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - (getSize().width/2), screenSize.height / 2 - (getSize().height/2));

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icons/settings.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(scaledIcon);
        button.setActionCommand("settings");
        button.setBounds(getSize().width-60, 0, 50, 50);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setFocusable(false);

        button.addActionListener(this);




        slime = new Slime(this);
        monster = new Monster(this);


//        map.setBounds(-390,-140,1446,477);
        map.setBounds(-16,-38,getSize().width, getSize().height); // Потом переместить всю отрисовку в отдельный класс

//        slime.setBounds(-40, 134, 128, 128);
        slime.setBounds(-40, getSize().height-128-38, 128, 128);
        monster.setBounds(170, getSize().height-87-32, 128, 87);

        slime.action();
        monster.action();

        add(button);
        add(slime);
        add(monster);
        add(map);

//        pack();

        components = getContentPane().getComponents();

        logic = new GameLogic(this);
        slime.setGameLogic(logic);
        monster.setGameLogic(logic);

        slime.startAnimate();
        monster.startAnimate();



        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setResizable(true);
        setVisible(true);

        settingsData = new SettingsData();
    }

    public void updateLayout() {
        map.setBounds(-16, -38, getWidth(), getHeight());
        slime.setBounds(-40, getHeight() - 128 - 38, 128, 128);
        monster.setBounds(170, getHeight() - 87 - 32, 128, 87);

        Component[] comps = getContentPane().getComponents();
        for (Component c : comps) {
            if (c instanceof JButton && ((JButton) c).getActionCommand().equals("settings")) {
                c.setBounds(getWidth() - 60, 0, 50, 50);
            }
        }

        revalidate();
        repaint();
    }

    public JMap getMap() {
        return map;
    }

    @Override public void keyTyped(KeyEvent e) { }

    @Override public void keyPressed(KeyEvent e) { logic.keyPressed(e.getKeyCode()); }

    @Override public void keyReleased(KeyEvent e) { logic.keyReleased(e.getKeyCode()); }

    public Component[] getEntities() { return components; }

    @Override public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "settings":
                Point location = getLocationOnScreen();
                settingsData.setGameX(location.x);
                settingsData.setGameY(location.y);
                settingsData.setWidth(getSize().width);
                settingsData.setHeight(getSize().height);
                new SettingsUI(this,settingsData);
                break;
        }
    }
}
