import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WinScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WinScreen extends World
{
    private Menu menu;

    public WinScreen(Menu menu, GameWorld world)
    {    
        super(1024, 574, 1); 
        this.menu = menu;
        addObject(new Button("buttons/menu.png", () -> {
            Greenfoot.setWorld(menu);
            world.turnMusicOn(false);
        }), getWidth() / 2, getHeight() - 100);
    }


}
