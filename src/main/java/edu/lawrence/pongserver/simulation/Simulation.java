package edu.lawrence.pongserver.simulation;

import cmsc250.mazerunnerserver.Constants;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import edu.lawrence.pongserver.physics.*;

public class Simulation implements Constants {
    private Box outer;
    private Ball ball;
    private Box boxOne;
    private Box boxTwo;
    private Lock lock;
    
    // Constructs shape objs
    public Simulation(int dX,int dY)
    {
        outer = new Box(0,0,WIDTH,HEIGHT,false); // Window border constraints
        boxOne = new Box(MARGIN,MARGIN, THICKNESS, LENGTH,true);
        boxTwo = new Box(WIDTH - MARGIN - THICKNESS,MARGIN, LENGTH, THICKNESS,true);
        lock = new ReentrantLock();
    }
    
    /* If I understand correctly, this is where we would add our moving obstacles
    *  Also where we check if the location of the ball is within a box?
    *  We could also update the lives Label here
    */

    
    // Player movement logic
    public void moveBox(int box,int deltaX,int deltaY)
    {
        lock.lock();
        
        // Determine which player is attempting to move
        Box mover = boxOne;
        if(box == 2)
            mover = boxTwo;
        
        double dX = deltaX;
        double dY = deltaY;
        
        // Keep within x axis
        if(mover.x + deltaX < 0)
          dX = -mover.x;
        // Keep the box within the window x axis border
        if(mover.x + mover.width + deltaX > outer.width)
          dX = outer.width - mover.width - mover.x;
       
        // Keep within y axis
        if(mover.y + deltaY < 0)
           dY = -mover.y;
        // Keep the box within the window y axis border
        if(mover.y + mover.height + deltaY > outer.height)
           dY = outer.height - mover.height - mover.y;
        
        // Move the box
        mover.move(dX,dY);
        
     // removed code that was previously here
     
        lock.unlock();
    }
    public String getGameState() {
        // Changed game state return, to return the x coordinates of the box. 
        return Double.toString(boxOne.y) + ' ' + boxTwo.y + ' ' + boxOne.x + ' ' + boxTwo.x;
    }
}
