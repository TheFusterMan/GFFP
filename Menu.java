
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MonitorScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World
{
    private GameWorld gameWorld;
    private Instructions instructions;

    final int logoOffset = 90;
    final int firstButtonOffset = 30;
    final int spaceBetweenButtons = 50;

    public Menu()
    {    
        super(800, 600, 1); 

        gameWorld = new GameWorld(this);
        instructions = new Instructions(this);

        addObject(new Button("logo.png", () -> {
                        System.out.println("Авторы:\nПожидаев Дмитрий\nМанжелей Никита\nБ9123-09.03.04 (3,4)");
                }), this.getWidth() / 2, this.getHeight() / 2 - logoOffset);
        addObject(new Button("buttons/start.png", () -> {
                        Greenfoot.setWorld(gameWorld);
                        gameWorld.turnMusicOn(true);

                }), this.getWidth() / 2, this.getHeight() / 2 + firstButtonOffset);
        addObject(new Button("buttons/help.png", () -> {
                        Greenfoot.setWorld(instructions);
                }), this.getWidth() / 2, this.getHeight() / 2 + firstButtonOffset + spaceBetweenButtons);
        addObject(new Button("buttons/exit.png", () -> {
                        Greenfoot.stop();
                }), this.getWidth() / 2, this.getHeight() / 2 + firstButtonOffset + 2 * spaceBetweenButtons);
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
