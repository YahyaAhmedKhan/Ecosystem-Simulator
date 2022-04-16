import java.awt.*;
import java.util.List;
import java.util.Random;

public class Cannibal extends Carnivore {

    private static final int BASE_SIZE = 3;
    private static final int BASE_SPEED = 3;

    public Cannibal(int x, int y) {
        super(x, y);
        getCircle().setColor(Color.blue);
    }

    @Override
    public void giveBirth(List<LivingThing> animalList, int offsprings) {
        Random r = new Random();
        for (int i = 0; i < offsprings; i++)
            animalList.add(new Cannibal(getCentre().x - 15 + r.nextInt(30), getCentre().y - 15 + r.nextInt(30)));
        setAlive(false);
    }
}
