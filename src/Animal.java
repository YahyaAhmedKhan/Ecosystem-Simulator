import java.util.List;
import java.awt.Point;

public abstract class Animal extends LivingThing {

    public LivingThing getTarget() {
        return target;
    }

    public void setTarget(LivingThing target) {
        this.target = target;
    }

    private LivingThing target;

    protected Animal(int x, int y) {
        super(x, y);
        setDeathCounter(400);
    }

    @Override
    public void live() {
        setDeathCounter(getDeathCounter() - 1);
        if (getDeathCounter() == 0) {
            setAlive(false);
            System.out.println("died");
        }
    }

    public void eat(LivingThing livingThing) {
        livingThing.setAlive(false);
        grow();
    }

    public LivingThing findTarget(List<LivingThing> livingThingList) {
        int minDist = Integer.MAX_VALUE;
        LivingThing target = null;
        for (LivingThing livingThing : livingThingList) {

            int dist = (int) Point.distance(getCentre().x, getCentre().y, livingThing.getCentre().x,
                    livingThing.getCentre().y);
            if (minDist > dist && livingThing.isAlive() && (getSize() >= livingThing.getSize())) { // performance
                minDist = dist;
                target = livingThing;
            }
        }
        return target;
    }
    public abstract void giveBirth(List<LivingThing> animalList, int offsprings);

}
