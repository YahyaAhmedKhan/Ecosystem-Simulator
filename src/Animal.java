import java.util.List;

import javax.lang.model.util.ElementScanner6;

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
        if (livingThing.getSize() <= getSize()) {
            livingThing.setAlive(false);
            grow();
            setDeathCounter(400);
            unlockTarget();
            if (!(livingThing instanceof Plant))
                System.out.println(this + " ate " + livingThing);
        }
    }

    public boolean isValidTarget(LivingThing livingThing) {
        if (livingThing.isAlive() && livingThing.getSize() <= getSize())
            return true;
        else
            return false;
    }

    public void updateIfBetter(LivingThing livingThing) {
        updateTargetLock();
        if (!isTargetLocked()) {
            if (getTarget() == null)
                setTarget(livingThing);
            else if (isValidTarget(getTarget())) {
                if (Point.distance(getCentre().x, getCentre().y, getTarget().getCentre().x,
                        getTarget().getCentre().y) <= Point.distance(getCentre().x, getCentre().y,
                                livingThing.getCentre().x,
                                livingThing.getCentre().y))
                    return;
                else {
                    setTarget(livingThing);
                    return;
                }
            }
        }
    }

    /**
     * gets the best target in the given list, if the target is closer than current
     * one
     * 
     * @param livingThingList list of prey
     * @return the better target if found in the list, if not found, returns the
     *         previous target, returns null if not suitable prey found
     */
    public LivingThing findTarget(List<LivingThing> livingThingList) {
        updateTargetLock();
        LivingThing bestTarget = getTarget();

        // if (getTarget() == null || !getTarget().isAlive() || getTarget().getSize() >
        // getSize()) {
        // unlockTarget();
        // }
        // if (!isTargetLocked()) {
        // System.out.println(3);

        int minDistance = Integer.MAX_VALUE;
        if (bestTarget != null)
            minDistance = (int) Point.distance(getCentre().x, getCentre().y, bestTarget.getCentre().x,
                    bestTarget.getCentre().y);
        for (LivingThing livingThing : livingThingList) {

            // System.out.println(7);

            int x = getCentre().x;
            int y = getCentre().y;
            int xTarget = livingThing.getCentre().x;
            int yTarget = livingThing.getCentre().y;
            int distanceToNewTarget = (int) Point.distance(x, y, xTarget, yTarget);
            // System.out.println("5");

            // IF THE livingThing IS [ NOT ITSELF, ALIVE, NOT BIGGER, CLOSER (than minDist)
            // ]
            if (!this.equals(livingThing) && isValidTarget(livingThing) && distanceToNewTarget < minDistance) { // performance
                // System.out.println("1");
                // IF YOU ALREADY HAVE A VALID TARGET:
                // if (getTarget() != null && getTarget().isAlive() && getSize() >=
                // livingThing.getSize()) {
                // System.out.println("2");

                // int oldTargetDistance = (int) Point.distance(x, y, getTarget().getCentre().x,
                // getTarget().getCentre().y);
                // IF THE DISTANCE TO THE NEWFOUND TARGET IS SMALLER/CLOSER THAN THE CURRENT
                // TARGET:
                // if (distanceToNewTarget < oldTargetDistance) {
                // minDistance = oldTargetDistance;
                // newTarget = getTarget();
                // // System.out.println("3");

                // } else {
                // minDistance = distanceToNewTarget;
                // newTarget = livingThing;
                // }
                // } else {
                // minDistance = distanceToNewTarget;
                // newTarget = livingThing;

                // }
                // }

                minDistance = distanceToNewTarget;
                bestTarget = livingThing;
            }

            // }
        }
        if (bestTarget == null)
            System.out.println("none found");
        return bestTarget;

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
        if (getTarget() == null || !getTarget().isAlive() || getTarget().getSize() > getSize()) {
            setTarget(null);
            unlockTarget();
            // System.out.println(2);

        } else {
            lockTarget();
            // System.out.println(3);

        }
    }
}
