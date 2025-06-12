import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bobber here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bobber extends Actor
{
    private int speed = 0;
    private final int LIFT_SPEED = -5;
    private final int GRAVITY_SPEED = 3;
    
    private Container container;
    private int halfContainerHeight;
    
    private int initPostionX = -1;
    private int initPostionY = -1;
    private int halfWidth;
    private int halfHeight;
    private int shakingRadius = 3;
    
    public Bobber() {
        setImage("bobber.png");
        
        halfWidth = getImage().getWidth() / 2;
        halfHeight = getImage().getHeight() / 2;
    }
    
    public Bobber(Container container) {
        setImage("bobber.png");
        this.container = container;
        halfContainerHeight = container.getImage().getHeight() / 2;
    }
    
    public void act()
    {
        if (container != null) {
            handleInput();
            moveVertically();
            checkBoundaries();
        }
    }
    
    public boolean checkIntersection(Actor actor) {
        return intersects(actor);
    }
    
    private void handleInput() {
        if (Greenfoot.isKeyDown("space")) {
            speed = LIFT_SPEED;
        } else {
            speed = GRAVITY_SPEED;
        }
    }
    
    private void moveVertically() {
        setLocation(getX(), getY() + speed);
    }
    
    private void checkBoundaries() {
        if (getY() <= container.getY() - halfContainerHeight) {
            setLocation(getX(), container.getY() - halfContainerHeight);
            speed = 0;
        }
        else if (getY() >= container.getY() + halfContainerHeight) {
            setLocation(getX(), container.getY() + halfContainerHeight);
            speed = 0;
        }
    }
    
    public void shake() {
        if (initPostionX == -1 || initPostionY == -1) {
            initPostionX = getX();
            initPostionY = getY();
        }
        
        int randomXSpeed = Greenfoot.getRandomNumber(5);

        if (Greenfoot.getRandomNumber(2) == 0) {
            randomXSpeed = -randomXSpeed;
        }
        
        int randomYSpeed = Greenfoot.getRandomNumber(5);
        
        if (Greenfoot.getRandomNumber(2) == 0) {
            randomYSpeed = -randomYSpeed;
        }
        
        if ((initPostionX - shakingRadius * halfWidth <= getX() + randomXSpeed) &&
        (initPostionX + shakingRadius * halfWidth >= getX() + randomXSpeed) &&
        (initPostionY - shakingRadius * halfHeight <= getY() + randomYSpeed) &&
        (initPostionY + shakingRadius * halfHeight >= getY() + randomYSpeed)) {
            setLocation(getX() + randomXSpeed, getY() + randomYSpeed);
        }
    }
}
