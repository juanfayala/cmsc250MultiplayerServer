package edu.lawrence.pongserver.simulation;

import edu.lawrence.pongserver.physics.*;

public class Ball {
    private Ray r;
    private boolean special;
    
    public Ball(double startX,double startY,double dX,double dY,boolean special)
    {
        this.special=special;
        if(special==true){
        Vector v = new Vector(dX,dY);
        double speed = v.length();
         v.normalize();
        r = new Ray(new Point(startX,startY),v,speed);
        }
        else{
            
        }
    }
    
    public Ray getRay()
    {
        return r;
    }
    
    public void setRay(Ray r)
    {
        if(special) {
            r.v.dY =0.0;
            r.v.normalize();
        }
        this.r = r;
    }
    
    public void move(double time)
    {
        r = new Ray(r.endPoint(time),r.v,r.speed);
        System.out.println(r);
    }
}
