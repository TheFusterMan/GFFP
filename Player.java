import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Player extends Actor
{
    private final double experienceToLevelUp = 1000;
    private double experience = 0;
    private int level = 1;

    private Inventory inventory;

    public Player(Inventory inventory) {
        this.inventory = inventory;
    }
    
    public void act()
    {
        GameWorld world = (GameWorld) getWorld();
        
        if (Greenfoot.isKeyDown("space") && world.getGameState() == GameWorld.GameState.IDLE) {
            world.spawnBobber();
            world.setGameState(GameWorld.GameState.WAITING_FOR_BITE);
        }
    }

    public void addExperience(Item item) {
        GameWorld world = (GameWorld) getWorld();

        if (item.getRarity() == GameWorld.ItemRaritiy.TRASH) experience += 5 / level;
        else if (item.getRarity() == GameWorld.ItemRaritiy.COMMON) experience += 20 / level;
        else if (item.getRarity() == GameWorld.ItemRaritiy.RARE) experience += 100 / level;
        else if (item.getRarity() == GameWorld.ItemRaritiy.LEGENDARY) experience += 1000 / level;

        while (experience >= experienceToLevelUp) {
            experience -= experienceToLevelUp;
            level += 1;
            world.updateLevelingProgressBar(0);
            world.updateLevelIcon(level);
        }

        world.updateLevelingProgressBar(experience / experienceToLevelUp);
    }

    public void learnRecipesOnLevelUp(Recipe[] avaliableRecipes) {
        for (Recipe toLearn : avaliableRecipes) {
            if (level >= toLearn.getRequiredLevel()) {
                boolean isKnown = false;
                for (Recipe known : inventory.getKnownRecipes()) {
                    if (toLearn == known) isKnown = true;
                }
                if (isKnown == false) inventory.addRecipe(toLearn);
            }
        }
    }

    public int getLevel() { return level; }
}