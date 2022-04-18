import java.awt.*;
import java.util.List;

public class Plant extends LivingThing{

    private static final int BASE_SIZE = 2;
    private static final int BASE_SPEED = 0;

    public Plant(int x, int y){
        super(x,y);
        setSize(BASE_SIZE);
        setSpeed(BASE_SPEED);
        setCircle(new Circle(getSize(), getCentre(), Color.green));
    }


    @Override
    public void moveToEat() {

    }

    @Override
    public boolean collide(LivingThing livingThing) {
        return true;
    }

    @Override
    public void grow() {
        setSize(BASE_SIZE + getAge()/2000);
        getCircle().setSize(getSize());
    }
    //just teting


    @Override
    public void live() {
        setAge(getAge()+1);
       
    }
}
