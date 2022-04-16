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
    // public void move() {
    // if (getTarget() != null) {
    // if (getTarget().isAlive()) {
    // int xDisp = getTarget().getCentre().x - getCentre().x;
    // int yDisp = getTarget().getCentre().y - getCentre().y;
    // int diag = (int) Math.sqrt(xDisp * xDisp + yDisp * yDisp);
    // if (diag != 0)
    // getCentre().setLocation(getCentre().x + (BASE_SPEED * xDisp / diag),
    // getCentre().y + (BASE_SPEED * yDisp / diag));

    // }
    // }

    // }

    // @Override
    // public boolean collide(LivingThing livingThing) {

    // if (Point.distance(getCentre().x, getCentre().y, livingThing.getCentre().x,
    // livingThing.getCentre().y) < (getSize() + livingThing.getSize()))
    // return true;
    // else
    // return false;
    // }

    // @Override
    // public void grow() {
    // setSize(getSize() + 1);
    // getCircle().setSize(getSize());
    // }

    // @Override
    // public LivingThing findTarget(List<LivingThing> livingThingList) {
    // int minDist = Integer.MAX_VALUE;
    // LivingThing target = null;
    // for (LivingThing livingThing : livingThingList) {
    // int dist = (int) Point.distance(getCentre().x, getCentre().y,
    // livingThing.getCentre().x,
    // livingThing.getCentre().y);
    // if (minDist > dist && livingThing.isAlive()) {
    // minDist = dist;
    // target = livingThing;
    // }
    // }
    // return target;
    // }

    @Override
    public void giveBirth(List<LivingThing> animalList, int offsprings) {
        Random r = new Random();
        for (int i = 0; i < offsprings; i++)
            animalList.add(new Carnivore(getCentre().x - 15 + r.nextInt(30), getCentre().y - 15 + r.nextInt(30)));
        setAlive(false);
    }
}
