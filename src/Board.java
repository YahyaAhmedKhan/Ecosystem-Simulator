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
    private Stack CarnivoreBabies;
    private Stack CannibalBabies;

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
        carnivoresList = new ArrayList<LivingThing>();
        cannnibalsList = new ArrayList<LivingThing>();

        HerbivoreBabies = new Stack<Herbivore>();
        CarnivoreBabies = new Stack<Carnivore>();
        CannibalBabies = new Stack<Cannibal>();
        

        Random r = new Random();
        for (int i = 0; i < PLANTS; i++) {
            plantsList.add(new Plant(r.nextInt(B_WIDTH), r.nextInt(B_HEIGHT)));
        }
        for (int i = 0; i < HERBIVORES; i++) {
            herbivoresList.add(new Herbivore(r.nextInt(B_WIDTH), r.nextInt(B_HEIGHT)));
        }
        for (int i = 0; i < CARNIVORES; i++) {
            carnivoresList.add(new Carnivore(r.nextInt(B_WIDTH), r.nextInt(B_HEIGHT)));
        }
        for (int i = 0; i < CANNIBALS; i++) {
            cannnibalsList.add(new Cannibal(r.nextInt(B_WIDTH), r.nextInt(B_HEIGHT)));
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

    private void drawPlants(Graphics g) {
        for (LivingThing plant : plantsList) {
            if (plant.isAlive()) {
                plant.live();
                plant.grow();
                plant.getCircle().draw(g);
            }
        }
    }

    private void drawHerbivores(Graphics g) {
        for (LivingThing livingThing : herbivoresList) {
            Herbivore herbivore = (Herbivore) livingThing;
            if (herbivore.isAlive()) {
                if (herbivore.getTarget() == null || !herbivore.getTarget().isAlive()) {
                    herbivore.setTarget(herbivore.findTarget(plantsList));
                }
                herbivore.move();

                if (herbivore.getTarget() != null)
                    if (herbivore.collide(herbivore.getTarget()))
                        herbivore.eat(herbivore.getTarget());

                if (herbivore.getSize() > 10) {
                    herbivore.giveBirth(HerbivoreBabies, 8);
                    herbivore.setAlive(false);
                }

            }

            herbivore.live();
            if (herbivore.isAlive())
                herbivore.getCircle().draw(g);
        }
        while (!HerbivoreBabies.isEmpty())
            herbivoresList.add((Herbivore) HerbivoreBabies.pop());
    }

    private void drawCarnivores(Graphics g){
        for (LivingThing livingThing : carnivoresList) {
            Carnivore carnivore = (Carnivore) livingThing;
            if (carnivore.isAlive()) {
                if (carnivore.getTarget() == null || !carnivore.getTarget().isAlive()) {
                    carnivore.setTarget(carnivore.findTarget(herbivoresList));
                    carnivore.setTarget(carnivore.findTarget(cannnibalsList));
                    carnivore.lockTarget();

                }
                carnivore.move();

                if (carnivore.getTarget() != null)
                    if (carnivore.collide(carnivore.getTarget()))
                        carnivore.eat(carnivore.getTarget());

                if (carnivore.getSize() > 10) {
                    carnivore.giveBirth(CarnivoreBabies, 4);
                    carnivore.setAlive(false);
                }

            }
            carnivore.live();
            if (carnivore.isAlive())
                carnivore.getCircle().draw(g);
        }
        while (!CarnivoreBabies.isEmpty())
            carnivoresList.add((Carnivore) CarnivoreBabies.pop());
    }
    private void drawCannibals(Graphics g){
        for (LivingThing livingThing : cannnibalsList) {
            Cannibal cannibal = (Cannibal) livingThing;
            if (cannibal.isAlive()) {
                if (cannibal.getTarget() == null || !cannibal.getTarget().isAlive() && !cannibal.isTargetLocked()) {
                    cannibal.setTarget(cannibal.findTarget(herbivoresList));
                    cannibal.setTarget(cannibal.findTarget(carnivoresList));
                    cannibal.setTarget(cannibal.findTarget(cannnibalsList));
                    cannibal.lockTarget();

                }
                cannibal.move();

                if (cannibal.getTarget() != null)
                    if (cannibal.collide(cannibal.getTarget()))
                        cannibal.eat(cannibal.getTarget());

                if (cannibal.getSize() > 10) {
                    cannibal.giveBirth(CarnivoreBabies, 2);
                    cannibal.setAlive(false);
                }

            }
            cannibal.live();
            if (cannibal.isAlive())
                cannibal.getCircle().draw(g);
        }
        while (!CannibalBabies.isEmpty())
            cannnibalsList.add((Cannibal) CannibalBabies.pop());
    }

    private void Draw(Graphics g) {

        Toolkit.getDefaultToolkit().sync();
        drawPlants(g);
        drawHerbivores(g);
        drawCarnivores(g);
        drawCannibals(g);


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