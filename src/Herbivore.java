import java.awt.*;
import java.util.List;
import java.util.Random;

public class Herbivore extends Animal {

    private static final int BASE_SIZE = 2;
    private static final int BASE_SPEED = 2;

    public Herbivore(int x, int y) {
        super(x, y);
        setSize(BASE_SIZE);
        setSpeed(BASE_SPEED);
        setCentre(new Point(x, y));
        setCircle(new Circle(getSize(), getCentre(), new Color(0, 100, 0)));
    }

    @Override
    public void giveBirth(List<LivingThing> animalList, int offsprings) {
        Random r = new Random();
        for (int i = 0; i < offsprings; i++)
            animalList.add(new Herbivore(getCentre().x - 15 + r.nextInt(30), getCentre().y - 15 + r.nextInt(30)));
        setAlive(false);
    }
}
