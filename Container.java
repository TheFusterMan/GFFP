import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Container here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Container extends Actor
{
    int Height = 100;
    int Width = 100;
    private int minSpeed = 2;
    private int maxSpeed = 6;

    private int fishLocation = 0;
    private int fishTarget = 0;
    private int fishSpeed = 0;
    private Fish fish;
    
    //GreenfootImage Fish = new GreenfootImage("BobberFish.png");
    GreenfootImage bar = new GreenfootImage("minigame_container.png");
    
    public Container() {
        setImage(bar);
        Height = bar.getHeight();
        Width = bar.getWidth();
        fish = new Fish(this);
        
        //setImage("BobberBar_Background.png");
    }
    
     public void addedToWorld(World world){
        world.addObject(fish, getX(), getY() + Height/2);
    }
    
    public void act()
    {
        // Add your action code here.
        //drawBackground();
        moveToTarget();
        if((fishSpeed <= 0 && fishTarget >= fishLocation) || (fishSpeed >= 0 && fishTarget <= fishLocation)){
            newTarget();
        }
        //sleepFor(2);
    }


    private void draw(){
        
    }
    
    private void moveToTarget(){
        fishLocation += fishSpeed;
        /*
        if(fish.getY() < getY() - (Height/2)) fishLocation = Height;
        else if(fish.getY() > getY() + (Height/2)) fishLocation = 0;
        else fish.setLocation(getX(), fish.getY() - fishSpeed);
        */
        if(fishLocation > Height-1){
            fishLocation = Height-1;
        } else if(fishLocation < 1) {
            fishLocation = 1;
        } else {
            fish.setLocation(getX(), fish.getY() - fishSpeed);
        }
        
    }
    
    private void newTarget(){
        
        if(fishSpeed >= 0){
            fishTarget = Greenfoot.getRandomNumber(fishLocation) + 1;
        } else {
            fishTarget = Greenfoot.getRandomNumber(Height - fishLocation) + fishLocation - 1;
        }
        
        if(fishTarget < fishLocation){
            fishSpeed = -1 * (Greenfoot.getRandomNumber(maxSpeed - minSpeed + 1) + minSpeed);
        } else {
            fishSpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed + 1) + minSpeed;
        }
        
    }
    
    public Fish getFish(){
        return fish;
    }
    

}
