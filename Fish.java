import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fish here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fish extends Actor
{
    private int minSpeed = 2;
    private int maxSpeed = 8;
    private int speed = minSpeed + maxSpeed / 2;
    
    private Container container;
    private int halfContainerHeight;
    
    public Fish() {
        setImage("BobberFish.png");
    }
    
    public Fish(Container container) {
        setImage("BobberFish.png");
        this.container = container;
        halfContainerHeight = container.getImage().getHeight() / 2;
    }
    
    public void act()
    {
        /*if (container != null) {
            moveVertically();
            checkBoundaries();
        }*/
    }
    
    private void moveVertically() {
        setLocation(getX(), getY() + speed);
    }
    
    private void checkBoundaries() {
        if (getY() <= container.getY() - halfContainerHeight) {
            setLocation(getX(), container.getY() - halfContainerHeight);
            changeDirectionAndSpeed();
        }
        else if (getY() >= container.getY() + halfContainerHeight) {
            setLocation(getX(), container.getY() + halfContainerHeight);
            changeDirectionAndSpeed();
        }
    }
    
    private void changeDirectionAndSpeed() {
        int randomSpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed + 1) + minSpeed;

        if (Greenfoot.getRandomNumber(2) == 0) speed = randomSpeed;
        else speed = -randomSpeed;
    }
}
