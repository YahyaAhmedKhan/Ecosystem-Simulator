import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;

    private EcosystemEngine EcoEngine;

    public EcosystemEngine getEcoEngine() {
        return this.EcoEngine;
    }

    public void setEcoEngine(EcosystemEngine EcoEngine) {
        this.EcoEngine = EcoEngine;
    }

    private Timer timer;
    private int x, y;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setSize(B_WIDTH, B_HEIGHT);

        x = INITIAL_X;
        y = INITIAL_Y;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void addEngine(EcosystemEngine ecosystemEngine){
        
        EcoEngine = ecosystemEngine;
        EcoEngine.spawnLivingThings();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Draw(g);
        // System.out.println(frame++);
    }

    

    private void Draw(Graphics g) {

        Toolkit.getDefaultToolkit().sync();
        EcoEngine.updateAll();



    }

    @Override
    public void actionPerformed(ActionEvent e) {

        x += 1;
        y += 1;

        if (y > B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }

        repaint();
    }
}