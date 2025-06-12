
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Recipe extends Actor
{
    private Item[] ingredients;
    private Item resultItem;
    private int resultQuantity;
    private int levelRequired;
    private GameWorld gameWorld;

    public Recipe(GameWorld gameWorld, Item[] ingredients, Item resultItem, int resultQuantity, int levelRequired) {
        this.gameWorld = gameWorld;
        this.ingredients = ingredients;
        this.resultItem = resultItem;
        this.resultQuantity = resultQuantity;
        this.levelRequired = levelRequired;
    }

    public Item[] getIngredients() { return ingredients; }
    public int getRequiredLevel() { return levelRequired; }
    
    public boolean Craft(Item[] givenIngredients) {
        for (int i = 0; i < givenIngredients.length; i++) {
            boolean isReallyNeeded = false;
            for (int j = 0; j < ingredients.length; j++) {
                if (givenIngredients[i] == ingredients[j]) {
                    isReallyNeeded = true;
                }
            }
            if (!isReallyNeeded) return false;
        }
        
        Inventory inventory = gameWorld.getInventory();

        for (Item spentItems : givenIngredients) inventory.removeItem(spentItems);
        inventory.addItem(resultItem);
        inventory.updateDisplay();
        return true;
    }
}
