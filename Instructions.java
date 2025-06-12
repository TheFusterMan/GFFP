import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instructions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instructions extends World
{
    private Menu menu;
    
    public Instructions(Menu menu)
    {    
        super(800, 600, 1); 
        
        this.menu = menu;
        addObject(new Button("buttons/exit.png", () -> {
            Greenfoot.setWorld(menu);
        }), 100, 30);
    }
}