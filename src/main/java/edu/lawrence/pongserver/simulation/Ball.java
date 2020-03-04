package edu.lawrence.pongserver.simulation;

import edu.lawrence.pongserver.physics.*;

public class Ball {
    private Ray r;
    
    public Ball(double startX,double startY,double dX,double dY)
    {
        Vector v = new Vector(dX,dY);
        double speed = v.length();
        r = new Ray(new Point(startX,startY),v,speed);
    }
    
    public Ray getRay()
    {
        return r;
    }
    
    public void setRay(Ray r)
    {
        this.r = r;
    }
    
    public void move(double time)
    {
        r = new Ray(r.endPoint(time),r.v,r.speed);
    }
}
