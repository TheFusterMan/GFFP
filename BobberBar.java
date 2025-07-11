import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Container here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BobberBar extends Actor
{
    int Height = 100;
    int Width = 100;
    private int minSpeed = 1;
    private int maxSpeed = 7;

    private int fishLocation = 0;
    private int fishTarget = 0;
    private int fishSpeed = 0;
    private Fish fish;
    private GameWorld world;
    //GreenfootImage Fish = new GreenfootImage("BobberFish.png");
    GreenfootImage bar = new GreenfootImage("minigame_container.png");
    Icon background = new Icon("bobberbar_Back.png");
    
    public BobberBar() {
        setImage(bar);
        Height = bar.getHeight();
        Width = bar.getWidth();
        fish = new Fish(this);
        
        //setImage("BobberBar_Background.png");
    }
    
    public void destructor(){
        world.removeObject(fish);
        world.removeObject(background);
    }
    
     public void addedToWorld(World world){
        this.world = (GameWorld) world;
        this.world.addObject(fish, getX()+2, getY() + Height/2);
        this.world.addObject(background, getX()-2, getY());
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
            fish.setLocation(fish.getX(), fish.getY() - fishSpeed);
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
