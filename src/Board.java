import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
    private final int DELAY = 50;

    private static int frame = 0;

    Random r;

    private final int PLANTS = 500;
    private final int HERBIVORES = 15;
    private final int CARNIVORES = 12;
    private final int CANNIBALS = 10;

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

        r = new Random();
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
                herbivore.live();
                herbivore.setTarget(herbivore.findTarget(plantsList));

                // herbivore.lockTarget();

                herbivore.moveToEat(); // move to the target, if it exists and eat if close enough

                if (herbivore.getSize() > 10) { // die if you're too big
                    herbivore.giveBirth(HerbivoreBabies, 8);
                    herbivore.setAlive(false);
                }
                herbivore.getCircle().draw(g);
            }

        }
        while (!HerbivoreBabies.isEmpty())
            herbivoresList.add((Herbivore) HerbivoreBabies.pop());
    }

    private void drawCarnivores(Graphics g) {
        for (LivingThing livingThing : carnivoresList) {
            Carnivore carnivore = (Carnivore) livingThing;
            if (carnivore.isAlive()) {
                carnivore.live();
                carnivore.setTarget(carnivore.findTarget(herbivoresList));
                carnivore.setTarget(carnivore.findTarget(cannnibalsList));

                carnivore.moveToEat();

                if (carnivore.getSize() > 10) {
                    carnivore.giveBirth(CarnivoreBabies, 4);
                    carnivore.setAlive(false);
                }
                carnivore.getCircle().draw(g);
            }
        }
        while (!CarnivoreBabies.isEmpty())
            carnivoresList.add((Carnivore) CarnivoreBabies.pop());
    }

    private void drawCannibals(Graphics g) {
        for (LivingThing livingThing : cannnibalsList) {
            Cannibal cannibal = (Cannibal) livingThing;
            if (cannibal.isAlive()) {
                cannibal.live();
                cannibal.setTarget(cannibal.findTarget(herbivoresList));
                cannibal.setTarget(cannibal.findTarget(carnivoresList));
                cannibal.setTarget(cannibal.findTarget(cannnibalsList));

                cannibal.moveToEat();

                if (cannibal.getSize() > 10) {
                    cannibal.giveBirth(CarnivoreBabies, 2);
                    cannibal.setAlive(false);
                }
                cannibal.getCircle().draw(g);
            }
        }
        while (!CannibalBabies.isEmpty())
            cannnibalsList.add((Cannibal) CannibalBabies.pop());
    }

    private void Draw(Graphics g)  {

        Toolkit.getDefaultToolkit().sync();
        drawPlants(g);
        drawHerbivores(g);
        drawCarnivores(g);
        drawCannibals(g);
        removeDead();;

        // for (int i = 0; i < 3; i++) {
        // plantsList.add(new Plant(r.nextInt(B_WIDTH), r.nextInt(B_HEIGHT)));
        // }

    }
    public void removeDead(){
        for (int i = 0; i < plantsList.size(); i++){
            if (!plantsList.get(i).isAlive()) plantsList.remove(i);
        }
        for (int i = 0; i < herbivoresList.size(); i++){
            if (!herbivoresList.get(i).isAlive()) herbivoresList.remove(i);
        }
        for (int i = 0; i < carnivoresList.size(); i++){
            if (!carnivoresList.get(i).isAlive()) carnivoresList.remove(i);
        }
        for (int i = 0; i < cannnibalsList.size(); i++){
            if (!cannnibalsList.get(i).isAlive()) cannnibalsList.remove(i);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // x += 1;
        // y += 1;

        // if (y > B_HEIGHT) {

        //     y = INITIAL_Y;
        //     x = INITIAL_X;
        // }

        repaint();
    }
}