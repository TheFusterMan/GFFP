
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Recipe extends Actor
{
    private ArrayList<Item> ingredients;
    private Item resultItem;
    private int levelRequired;
    private GameWorld gameWorld;

    public Recipe(GameWorld gameWorld, ArrayList<Item> ingredients, Item resultItem, int levelRequired) {
        this.gameWorld = gameWorld;
        this.ingredients = ingredients;
        this.resultItem = resultItem;
        this.levelRequired = levelRequired;
    }

    public ArrayList<Item> getIngredients() { return ingredients; }
    public int getRequiredLevel() { return levelRequired; }
    
    public boolean Craft(ArrayList<Item> givenIngredients) {
        for (int i = 0; i < givenIngredients.size(); i++) {
            boolean isReallyNeeded = false;
            for (int j = 0; j < ingredients.size(); j++) {
                if (givenIngredients.get(i) == ingredients.get(j)) {
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
