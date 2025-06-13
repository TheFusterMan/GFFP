
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Recipe extends Actor
{
    private ArrayList<String> ingredients;
    private String resultItem;
    private int levelRequired;
    private GameWorld gameWorld;

    public Recipe(GameWorld gameWorld, ArrayList<String> ingredients, String resultItem, int levelRequired) {
        this.gameWorld = gameWorld;
        this.ingredients = ingredients;
        this.resultItem = resultItem;
        this.levelRequired = levelRequired;
    }

    public ArrayList<String> getIngredients() { return ingredients; }
    public int getRequiredLevel() { return levelRequired; }
    
    public boolean Craft(ArrayList<Item> givenIngredients) {

        for (int i = 0; i < givenIngredients.size(); i++) {
            for (int j = i; j < givenIngredients.size(); j++) {
                if(givenIngredients.get(i) == givenIngredients.get(j)){
                    return false;
                }
                
            }
        
        }
        for (int i = 0; i < givenIngredients.size(); i++) {
            boolean isReallyNeeded = false;
            for (int j = 0; j < ingredients.size(); j++) {
                if (GameWorld.stringEqual(givenIngredients.get(i).getName(), ingredients.get(j))) {
                    isReallyNeeded = true;
                    break;
                } 
                
            }
            if (!isReallyNeeded) return false;
        }
        
        
        
        Inventory inventory = gameWorld.getInventory();

        for (Item spentItems : givenIngredients) inventory.removeItem(spentItems);

        for(Item item : gameWorld.getAvaliableItems()) {
            if (GameWorld.stringEqual(item.getName(), resultItem)) {
                inventory.addItem(item);
                inventory.updateDisplay();
                return true;
            }
        }
        return false;
    }
}
