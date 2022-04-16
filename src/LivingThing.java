import java.awt.*;

public abstract class LivingThing {
    private Point centre;
    private int size;
    private int speed;
    private boolean alive;
    private Circle circle;
    private int age;
    private int baseSize;
    private int deathCounter;

    public int getBaseSize() {
        return baseSize;
    }

    public void setBaseSize(int baseSize) {
        this.baseSize = baseSize;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    protected LivingThing(int x, int y) {
        centre = new Point(x, y);
        alive = true;
        setCentre(new Point(x, y));
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getDeathCounter() {
        return deathCounter;
    }

    public void setDeathCounter(int deathCounter) {
        this.deathCounter = deathCounter;
    }

    /**
     * makes the living thing move to its target
     */
    public abstract void moveToEat();

    /**
     * checks whether the object is colliding with passes Living Thing
     * @param livingThing the Living Thing with whom collision is being checked
     * @return  true if Collision is occurring, false otherwise
     */
    public abstract boolean collide(LivingThing livingThing);

    public abstract void live();

    /**
     * makes the living thing grow, shown by increase in size
     */
    public abstract void grow();

}
