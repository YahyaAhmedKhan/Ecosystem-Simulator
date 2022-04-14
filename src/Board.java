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

    private static int frame = 0;

    private final int PLANTS = 300;
    private final int HERBIVORES = 10;
    private final int CARNIVORES = 10;
    private final int CANNIBALS = 7;
    private ArrayList<LivingThing> plantsList;
    private ArrayList<LivingThing> herbivoresList;
    private ArrayList<LivingThing> carnivoresList;
    private ArrayList<LivingThing> cannnibalsList;

    private Stack HerbivoreBabies;

    private Timer timer;
    private int x, y;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        x = INITIAL_X;
        y = INITIAL_Y;

        plantsList = new ArrayList<LivingThing>();
        herbivoresList = new ArrayList<LivingThing>();
        HerbivoreBabies = new Stack<Herbivore>();

        Random r = new Random();
        for (int i = 0; i < PLANTS; i++) {
            plantsList.add(new Plant(r.nextInt(B_WIDTH), r.nextInt(B_HEIGHT)));
        }
        for (int i = 0; i < HERBIVORES; i++) {
            herbivoresList.add(new Herbivore(r.nextInt(B_WIDTH), r.nextInt(B_HEIGHT)));
        }

        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Draw(g);
        // System.out.println(frame++);
    }

    private void Draw(Graphics g) {

        Toolkit.getDefaultToolkit().sync();
        for (LivingThing plant : plantsList) {
            if (plant.isAlive()) {
                plant.live();
                plant.grow();
                plant.getCircle().draw(g);
            }
        }
        for (LivingThing livingThing : herbivoresList) {
            Herbivore herbivore = (Herbivore) livingThing;
            if (herbivore.isAlive()) {
                if (herbivore.getTarget() == null || !herbivore.getTarget().isAlive()) {
                    herbivore.setTarget(herbivore.findTarget(plantsList));
                }
                herbivore.move();
                // if (herbivore.getTarget() == null)
                // System.out.println("no");
                if (herbivore.getTarget() != null)
                    if (herbivore.collide(herbivore.getTarget()))
                        herbivore.eat(herbivore.getTarget());
                if (herbivore.getSize() > 20) {
                    herbivore.giveBirth(HerbivoreBabies, 8);
                    
                    herbivore.setAlive(false);
                }

            }

            herbivore.live();
            // herbivore.grow();
            herbivore.getCircle().draw(g);
        }
        while(!HerbivoreBabies.isEmpty())
        herbivoresList.add((Herbivore) HerbivoreBabies.pop());

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