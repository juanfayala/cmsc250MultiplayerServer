package edu.lawrence.pongserver.simulation;

import cmsc250.mazerunnerserver.Constants;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import edu.lawrence.pongserver.physics.*;

public class Simulation implements Constants {
    private Box outer;
    private Ball movingBall1;
        private Ball movingBall2;
            private Ball movingBall3;
             private Ball movingBall4;
              private Ball movingBall5;
    private Box boxOne;
    private Box boxTwo;
    private Lock lock;
    private double boxY;
    private int player;
    // Will be set to either 0 -> no win, 1 -> p1 wins, 2 -> p2 wins
    private int playerWin = 0;
    
    // Constructs shape objs
    public Simulation(int dX,int dY)
    {
        outer = new Box(0,0,WIDTH,HEIGHT,false); // Window border constraints
        //ball = new Ball(WIDTH/2,WIDTH/2,dX,dY);
        movingBall1 = new Ball((WIDTH/2)+250,WIDTH/2,dX,dY, true);
        movingBall2 = new Ball((WIDTH/2)-250,WIDTH/2,dX,dY, true);
        movingBall3 = new Ball(WIDTH/2,WIDTH/2,dX,dY, true);
       movingBall4 = new Ball((WIDTH/2)-150, WIDTH/2, dX,dY, true); 
       movingBall5 = new Ball((WIDTH/2)+150,WIDTH/2,dX,dY, true);
       
       
        boxOne = new Box(MARGIN,MARGIN, THICKNESS, LENGTH,true);
        boxTwo = new Box(WIDTH - MARGIN - THICKNESS,MARGIN, LENGTH, THICKNESS,true);
        lock = new ReentrantLock();
    }
    
    /* If I understand correctly, this is where we would add our moving obstacles
    *  Also where we check if the location of the ball is within a box?
    *  We could also update the lives Label here
    */
    public void evolve(double time)
    {
        lock.lock();
        
        Ray newLoc = boxOne.bounceRay(movingBall1.getRay(), time);
        if(newLoc != null)
            movingBall1.setRay(newLoc);
        else {
            newLoc = boxTwo.bounceRay(movingBall1.getRay(), time);
            if(newLoc != null)
                movingBall1.setRay(newLoc);
            else {
                newLoc = outer.bounceRay(movingBall1.getRay(), time);
                if(newLoc != null)
                    movingBall1.setRay(newLoc);
                else
                    movingBall1.move(time);
            } 
        }
              Ray newLoc1 = boxOne.bounceRay(movingBall2.getRay(), time);
        if(newLoc1 != null)
            movingBall2.setRay(newLoc1);
        else {
            newLoc1 = boxTwo.bounceRay(movingBall2.getRay(), time);
            if(newLoc1 != null)
                movingBall2.setRay(newLoc1);
            else {
                newLoc1 = outer.bounceRay(movingBall2.getRay(), time);
                if(newLoc1 != null)
                    movingBall2.setRay(newLoc1);
                else
                    movingBall2.move(time);
            }  
        } 
          Ray newLoc2 = boxOne.bounceRay(movingBall3.getRay(), time);
        if(newLoc2 != null)
            movingBall3.setRay(newLoc2);
        else {
            newLoc2 = boxTwo.bounceRay(movingBall3.getRay(), time);
            if(newLoc2 != null)
                movingBall3.setRay(newLoc2);
            else {
                newLoc2 = outer.bounceRay(movingBall3.getRay(), time);
                if(newLoc2 != null)
                    movingBall3.setRay(newLoc2);
                else
                    movingBall3.move(time);
            }  
        } 
        Ray newLoc3 = boxOne.bounceRay(movingBall4.getRay(), time);
        if(newLoc3 != null)
            movingBall4.setRay(newLoc3);
        else {
            newLoc3 = boxTwo.bounceRay(movingBall4.getRay(), time);
            if(newLoc3 != null)
                movingBall4.setRay(newLoc3);
            else {
                newLoc3 = outer.bounceRay(movingBall4.getRay(), time);
                if(newLoc3 != null)
                    movingBall4.setRay(newLoc3);
                else
                    movingBall4.move(time);
            }  
        } 
          Ray newLoc4 = boxOne.bounceRay(movingBall5.getRay(), time);
        if(newLoc4 != null)
            movingBall5.setRay(newLoc4);
        else {
            newLoc4 = boxTwo.bounceRay(movingBall5.getRay(), time);
            if(newLoc4 != null)
                movingBall4.setRay(newLoc4);
            else {
                newLoc4 = outer.bounceRay(movingBall5.getRay(), time);
                if(newLoc4 != null)
                    movingBall5.setRay(newLoc4);
                else
                    movingBall5.move(time);
            }  
        } 
        lock.unlock();
    }
     
    // Player movement logic
    public void moveBox(int box,int deltaX,int deltaY)
    {
        lock.lock();
        
        player = box;
        
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
        
        boxY = mover.y;
        
        // Move the box
        mover.move(dX,dY);
        
     // removed code that was previously here
     
        lock.unlock();
    }
    public String getGameState() {
        Point ballLoc =   movingBall1.getRay().origin;
        Point ballLoc1 =   movingBall2.getRay().origin;
         Point ballLoc2 =   movingBall3.getRay().origin;
         Point ballLoc3 = movingBall4.getRay().origin;
         Point ballLoc4 = movingBall5.getRay().origin;
        if(boxY > HEIGHT - 50)
            playerWin = player;
        // Changed game state return, to return the x coordinates of the box. 
        return (Double.toString(boxOne.y) + ' ' + boxTwo.y + ' ' + boxOne.x + ' ' + boxTwo.x + ' ' + playerWin+' '
                +ballLoc.x + ' ' + ballLoc.y + ' '+ballLoc1.x+' ' +ballLoc1.y+' '
                +ballLoc2.x+' '+ballLoc2.y+' '+ballLoc3.x+' '+ballLoc3.y+' '
                +ballLoc4.x+' '+ballLoc4.y);
    }
}
