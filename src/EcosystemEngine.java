import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.awt.Graphics;
import java.security.cert.PKIXCertPathBuilderResult;

public class EcosystemEngine {
    private Board board;

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private final int DEFAULT_PLANTS_COUNT = 300;
    private final int DEFAULT_HERBIVORES_COUNT= 15;
    private final int DEFAULT_CARNIVORES_COUNT = 10;
    private final int DEFAULT_CANNIBALS_COUNT = 7;
    private final int DEFAULT_SPREAD = 30;

    private final int DEFAULT_PLANT_SIZE = 2;
    private final int DEFAULT_HERBIVORES_SIZE = 3;
    private final int DEFAULT_CARNIVORES_SIZE = 3;
    private final int DEFAULT_CANNIBALS_SIZE = 3;

    private final int DEFAULT_PLANT_MAX_SIZE = 10;
    private final int DEFAULT_HERBIVORES_MAX_SIZE = 10;
    private final int DEFAULT_CARNIVORES_MAX_SIZE = 10;
    private final int DEFAULT_CANNIBALS_MAX_SIZE = 10;

    private ArrayList<LivingThing> plantsList;
    private ArrayList<LivingThing> herbivoresList;
    private ArrayList<LivingThing> carnivoresList;
    private ArrayList<LivingThing> cannnibalsList;

    private Stack HerbivoreBabies;
    private Stack CarnivoreBabies;
    private Stack CannibalBabies;

    private int plantsCount;
    private int herbivoreCount;
    private int carnivoreCount;
    private int cannibalCount;

    public int getPlantsCount() {
        return this.plantsCount;
    }

    public void setPlantsCount(int plantsCount) {
        this.plantsCount = plantsCount;
    }

    public int getHerbivoreCount() {
        return this.herbivoreCount;
    }

    public void setHerbivoreCount(int herbivoreCount) {
        this.herbivoreCount = herbivoreCount;
    }

    public int getCarnivoreCount() {
        return this.carnivoreCount;
    }

    public void setCarnivoreCount(int carnivoreCount) {
        this.carnivoreCount = carnivoreCount;
    }

    public int getCannibalCount() {
        return this.cannibalCount;
    }

    public void setCannibalCount(int cannibalCount) {
        this.cannibalCount = cannibalCount;
    }









    public EcosystemEngine(Board board){
        plantsList = new ArrayList<LivingThing>();
        herbivoresList = new ArrayList<LivingThing>();
        carnivoresList = new ArrayList<LivingThing>();
        cannnibalsList = new ArrayList<LivingThing>();

        HerbivoreBabies = new Stack<Herbivore>();
        CarnivoreBabies = new Stack<Carnivore>();
        CannibalBabies = new Stack<Cannibal>();

        setPlantsCount(DEFAULT_PLANTS_COUNT);
        setHerbivoreCount(DEFAULT_HERBIVORES_COUNT);
        setCarnivoreCount(DEFAULT_CARNIVORES_COUNT);
        setCannibalCount(DEFAULT_CANNIBALS_COUNT);        
    }

    public void spawnLivingThings(){
        Random r = new Random();
        int width = board.getWidth();
        int height = board.getHeight();

        for (int i = 0; i < plantsCount; i++) {
            plantsList.add(new Plant(r.nextInt(width), r.nextInt(height)));
        }
        for (int i = 0; i < herbivoreCount; i++) {
            herbivoresList.add(new Herbivore(r.nextInt(width), r.nextInt(height)));
        }
        for (int i = 0; i < carnivoreCount; i++) {
            carnivoresList.add(new Carnivore(r.nextInt(width), r.nextInt(height)));
        }
        for (int i = 0; i < cannibalCount; i++) {
            cannnibalsList.add(new Cannibal(r.nextInt(width), r.nextInt(height)));
        }
    }

    public void updateAll(){
        updatePlants();
        updateHerbivores();
        updateCarnivores();
        updateCannibals();
    }

    private void updatePlants() {
        for (LivingThing plant : plantsList) {
            if (plant.isAlive()) {
                plant.live();
                plant.grow();
                plant.getCircle().draw(board.getGraphics());
            }
        }
    }

    private void updateHerbivores() {
        for (LivingThing livingThing : herbivoresList) {
            Herbivore herbivore = (Herbivore) livingThing;
            if (herbivore.isAlive()) {
                if (herbivore.getTarget() == null || !herbivore.getTarget().isAlive()) {
                    herbivore.setTarget(herbivore.findTarget(plantsList));
                }
                herbivore.moveToEat();

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
                herbivore.getCircle().draw(board.getGraphics());
        }
        while (!HerbivoreBabies.isEmpty())
            herbivoresList.add((Herbivore) HerbivoreBabies.pop());
    }

    private void updateCarnivores(){
        for (LivingThing livingThing : carnivoresList) {
            Carnivore carnivore = (Carnivore) livingThing;
            if (carnivore.isAlive()) {
                if (carnivore.getTarget() == null || !carnivore.getTarget().isAlive()) {
                    carnivore.setTarget(carnivore.findTarget(herbivoresList));
                    carnivore.setTarget(carnivore.findTarget(cannnibalsList));
                    carnivore.lockTarget();

                }
                carnivore.moveToEat();

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
                carnivore.getCircle().draw(board.getGraphics());
        }
        while (!CarnivoreBabies.isEmpty())
            carnivoresList.add((Carnivore) CarnivoreBabies.pop());
    }
    private void updateCannibals(){
        for (LivingThing livingThing : cannnibalsList) {
            Cannibal cannibal = (Cannibal) livingThing;
            if (cannibal.isAlive()) {
                if (cannibal.getTarget() == null || !cannibal.getTarget().isAlive() && !cannibal.isTargetLocked()) {
                    cannibal.setTarget(cannibal.findTarget(herbivoresList));
                    cannibal.setTarget(cannibal.findTarget(carnivoresList));
                    cannibal.setTarget(cannibal.findTarget(cannnibalsList));
                    cannibal.lockTarget();

                }
                cannibal.moveToEat();

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
                cannibal.getCircle().draw(board.getGraphics());
        }
        while (!CannibalBabies.isEmpty())
            cannnibalsList.add((Cannibal) CannibalBabies.pop());
    }









}
