import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

class Circle
{
    private int size;
    private Point center;
    private Color color;

    Circle(int iSize, Point location, Color C)
    {
        setSize(iSize);
        setLocation(location);
        setColor(C);
    }

    void setSize(int iSize) {
        if (iSize > 1) {
            size = iSize;
        } else {
            size = 1;
        }
    }

    void setLocation(Point Pcenter) {
        center = Pcenter;
    }

    void setColor(Color Ccolor) {
        color = Ccolor;
    }

    int getSize()
    {
        return size;
    }

    Point getCenter()
    {
        return center;
    }

    Color getColor()
    {
        return color;
    }


    public void draw(Graphics g)
    {
        g.setColor(getColor());
        g.fillOval(getCenter().x - getSize() ,getCenter().y - getSize(),getSize()*2,getSize()*2);
    }
}