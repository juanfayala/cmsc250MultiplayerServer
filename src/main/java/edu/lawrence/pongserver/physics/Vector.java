package edu.lawrence.pongserver.physics;

/** A simple class to represent vectors. Includes convenience
 *  methods for standard vector operations. **/
public class Vector {
    public double dX;
    public double dY;
    
    public Vector(double dX,double dY)
    {
        this.dX = dX;
        this.dY = dY;
    }
    
    public void normalize()
    {
        double length = this.length();
        dX /= length;
        dY /= length;
    }
    
    public double length()
    {
        return Math.sqrt(dotProduct(this,this));
    }
    
    static public double crossProduct(Vector one,Vector two)
    {
        return one.dX*two.dY-two.dX*one.dY;
    }
    
    static public double dotProduct(Vector one,Vector two)
    {
        return one.dX*two.dX + one.dY*two.dY;
    }
}
