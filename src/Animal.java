import java.util.List;
import java.awt.Point;

public abstract class Animal extends LivingThing {

    private LivingThing target;
    private boolean targetLocked = false;

    public boolean isTargetLocked() {
        return this.targetLocked;
    }

    public void lockTarget() {
        if (getTarget() != null && getTarget().isAlive())
            targetLocked = true;
        ;
    }

    public void unlockTarget() {
        targetLocked = false;
    }

    public LivingThing getTarget() {
        return target;
    }

    public void setTarget(LivingThing target) {
        this.target = target;
    }

    protected Animal(int x, int y) {
        super(x, y);
        setDeathCounter(400);
    }

    @Override
    public void live() {

        setDeathCounter(getDeathCounter() - 1);
        if (getDeathCounter() == 0) {
            setAlive(false);
            System.out.println(this + " starved");
        }

    }

    public void eat(LivingThing livingThing) {
        livingThing.setAlive(false);
        grow();
        setDeathCounter(400);
        unlockTarget();
        if (!(livingThing instanceof Plant))
            System.out.println(this + " ate " + livingThing);
    }

    /**
     * gets the best target in the given list, if the target is closer than current
     * one
     * 
     * @param livingThingList list of prey
     * @return the better target if found in the list, if not found, returns the
     *         precious target
     */
    public LivingThing findTarget(List<LivingThing> livingThingList) {
        updateTargetLock();

        if (!isTargetLocked()) {
            int minDist = Integer.MAX_VALUE;
            LivingThing target = null;
            for (LivingThing livingThing : livingThingList) {

                int x = getCentre().x;
                int y = getCentre().y;
                int xTarget = livingThing.getCentre().x;
                int yTarget = livingThing.getCentre().y;
                int dist = (int) Point.distance(x, y, xTarget, yTarget);

                if (!this.equals(livingThing) && livingThing.isAlive()
                        && (getSize() >= livingThing.getSize()) && dist < minDist) { // performance
                    // found object is:
                    // not self, alive, smaller, and closer
                    if (getTarget() != null && getTarget().isAlive()) { // if u already have a valid tareget
                        if (Point.distance(x, y, xTarget, yTarget) < Point.distance(x, y, getTarget().getCentre().x,
                                getTarget().getCentre().y)) {
                            minDist = dist;
                            target = livingThing;
                        } else {
                            target = getTarget();
                        }
                    } else {
                        minDist = dist;
                        target = livingThing;
                    }
                }
            }
            // if (target == null)
            // System.out.println("no prey " + isTargetLocked());
            updateTargetLock();
            return target;
        } else {
            updateTargetLock();
            return getTarget();
        }

    }

    /**
     * gives birth to a number of offsprings and adds them to a list
     * 
     * @param animalList the list to add the offsprings to
     * @param offsprings the numnber of offsprings
     */
    public abstract void giveBirth(List<LivingThing> animalList, int offsprings);

    // @Override
    // public boolean equals(Object obj) {
    // // if (this.toString().equals(obj.toString()))
    // // return true;
    // // else
    // // return false;
    // return super.equals(obj);
    // }

    /**
     * moves toward target with its given speed, if it has a target
     * and eats it if it has reached it
     */
    @Override
    public void moveToEat() {
        updateTargetLock();
        if (isTargetLocked()) {
            int xDisp = getTarget().getCentre().x - getCentre().x;
            int yDisp = getTarget().getCentre().y - getCentre().y;
            int diag = (int) Math.sqrt(xDisp * xDisp + yDisp * yDisp);
            if (diag != 0)
                getCentre().setLocation(getCentre().x + (getSpeed() * xDisp / diag),
                        getCentre().y + (getSpeed() * yDisp / diag));
            if (collide(getTarget()))
                eat(getTarget());
        }
        else {
            System.out.println(isTargetLocked() + " " +  (isTargetLocked() ? getTarget().isAlive() : "unlocked") );
        }
        updateTargetLock();
    }

    @Override
    public boolean collide(LivingThing livingThing) {

        if (Point.distance(getCentre().x, getCentre().y, livingThing.getCentre().x,
                livingThing.getCentre().y) < (getSize() + livingThing.getSize()))
            return true;
        else
            return false;
    }

    @Override
    public void grow() {
        setSize(getSize() + 1);
        getCircle().setSize(getSize());
    }

    /**
     * unlocks target if the target is null, dead or bigger than itself
     * locks target otherwise
     */
    public void updateTargetLock() {
        if (getTarget() != null && getTarget().isAlive() && getTarget().getSize() <= getSize())
            lockTarget();
        else
            unlockTarget();

    }
}
