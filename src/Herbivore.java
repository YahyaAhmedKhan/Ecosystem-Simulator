import java.awt.*;
import java.util.List;
import java.util.Random;

public class Herbivore extends Animal {

    private static final int BASE_SIZE = 4;
    private static final int BASE_SPEED = 2;

    public Herbivore(int x, int y) {
        super(x, y);
        setSize(BASE_SIZE);
        setSpeed(BASE_SPEED);
        setCentre(new Point(x, y));
        setCircle(new Circle(getSize(), getCentre(), new Color(0, 100, 0)));
    }

    @Override
    public void move() {
        if (getTarget() != null) {
            if (getTarget().isAlive()) {
                int xDisp = getTarget().getCentre().x - getCentre().x;
                int yDisp = getTarget().getCentre().y - getCentre().y;
                int diag = (int) Math.sqrt(xDisp * xDisp + yDisp * yDisp);
                if (diag != 0)
                    getCentre().setLocation(getCentre().x + (2 * xDisp / diag), getCentre().y + (2 * yDisp / diag));

            }
        }

    }

    @Override
    public boolean collide(LivingThing livingThing) {

        if (Point.distance(getCentre().x, getCentre().y, livingThing.getCentre().x,
                livingThing.getCentre().y) < (getSize() + livingThing.getSize()))

            // if (Math.sqrt((livingThing.getCentre().x - getCentre().x) *
            // (livingThing.getCentre().x - getCentre().x) +
            // (livingThing.getCentre().y - getCentre().y) * (livingThing.getCentre().y -
            // getCentre().y))
            // - (getSize() + livingThing.getSize()) < 1)
            return true;
        else
            return false;
    }

    @Override
    public void grow() {
        setSize(getSize() + 1);
        getCircle().setSize(getSize());
    }

    @Override
    public LivingThing findTarget(List<LivingThing> livingThingList) {
        int minDist = Integer.MAX_VALUE;
        LivingThing target = null;
        for (LivingThing livingThing : livingThingList) {
            // int dist = ((Math.abs(getCentre().x - livingThing.getCentre().x))
            // + (Math.abs(getCentre().y - livingThing.getCentre().y)));
            // if (minDist > dist) {
            // minDist = dist;
            // target = livingThing;
            // }

            int dist = (int) Point.distance(getCentre().x, getCentre().y, livingThing.getCentre().x,
                    livingThing.getCentre().y);
            if (minDist > dist && livingThing.isAlive()) {
                minDist = dist;
                target = livingThing;
            }
        }
        return target;
    }

    @Override
    public void giveBirth(List<LivingThing> animalList, int offsprings) {
        for (int i = 0; i < offsprings; i++)
            animalList.add(
                    new Herbivore(getCentre().x + (int) Math.random() * 20, getCentre().y + (int) Math.random() * 20));
    }
}
