import java.awt.*;
import java.util.List;
import java.util.Random;

public class Carnivore extends Animal {

    private static final int BASE_SIZE = 3;
    private static final int BASE_SPEED = 3;

    public Carnivore(int x, int y) {
        super(x, y);
        setSize(BASE_SIZE);
        setSpeed(BASE_SPEED);
        setCentre(new Point(x, y));
        setCircle(new Circle(getSize(), getCentre(), Color.red));
    }

    // @Override
    // public LivingThing findTarget(List<LivingThing> livingThingList) {
    //     // TODO Auto-generated method stub

    //     return super.findTarget(livingThingList);
    // }
    @Override
    public void giveBirth(List<LivingThing> animalList, int offsprings) {
        Random r = new Random();
        for (int i = 0; i < offsprings; i++)
            animalList.add(new Carnivore(getCentre().x - 15 + r.nextInt(30), getCentre().y - 15 + r.nextInt(30)));
        setAlive(false);
    }
}
