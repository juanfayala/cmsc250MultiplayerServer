package edu.lawrence.pongserver.simulation;

import edu.lawrence.pongserver.physics.*;
import java.util.ArrayList;

public class Box {
    private ArrayList<LineSegment> walls;
    public double x;
    public double y;
    public double width;
    public double height;
    
    // Set outward to true if you want a box with outward pointed normals
    public Box(double x,double y,double width,double height,boolean outward)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        walls = new ArrayList<LineSegment>();
        if(outward) {
            walls.add(new LineSegment(new Point(x+width,y+height),new Point(x+width,y)));
            walls.add(new LineSegment(new Point(x,y),new Point(x,y+height)));
            walls.add(new LineSegment(new Point(x+width,y),new Point(x,y)));
            walls.add(new LineSegment(new Point(x,y+height),new Point(x+width,y+height)));
        } else {
            walls.add(new LineSegment(new Point(x+width,y),new Point(x+width,y+height)));
            walls.add(new LineSegment(new Point(x+width,y+height),new Point(x,y+height)));
            walls.add(new LineSegment(new Point(x,y),new Point(x+width,y)));
            walls.add(new LineSegment(new Point(x,y+height),new Point(x,y)));
        }
    }
    
    public Ray bounceRay(Ray in,double time)
    {
        // For each of the walls, check to see if the Ray intersects the wall
        Point intersection = null;
        for(int n = 0;n < walls.size();n++)
        {
            LineSegment seg = in.toSegment(time);
            intersection = walls.get(n).intersection(seg);
            if(intersection != null)
            {
                // If it intersects, find out when
                double t = in.getTime(intersection);
                // Reflect the Ray off the line segment
                Ray newRay = walls.get(n).reflect(seg,in.speed);
                // Figure out where we end up after the reflection.
                Point dest = newRay.endPoint(time-t);
                return new Ray(dest,newRay.v,in.speed);
            }
        }
        return null;
    }
    
    public void move(double deltaX,double deltaY)
    {
        for(int n = 0;n < walls.size();n++)
            walls.get(n).move(deltaX,deltaY);
        x += deltaX;
        y += deltaY;
    }
    
    public boolean contains(Point p)
    {
        if(p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height)
            return true;
        return false;
    }
}
