import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Inventory extends World
{
    private int availableSlots = 10;

    private GameWorld gameWorld;
    
    private ArrayList<Item> ownedItems = new ArrayList<Item>();
    private ArrayList<Item> craftItems = new ArrayList<Item>();;
    private ArrayList<Recipe> knownRecieps;

    public Inventory(GameWorld gw)
    {    
        super(799, 262, 1);

        addObject(new Button("buttons/back.png", () -> {
            Greenfoot.setWorld(gameWorld);
        }), getWidth() / 2, getHeight() - 35);
        addObject(new Button("buttons/craft.png", () -> {
            for (Recipe recipe : knownRecieps) {
                if (recipe != null) recipe.Craft(craftItems);
            }
        }), getWidth() / 2, getHeight() - 75);
        
        gameWorld = gw; 
        knownRecieps = new ArrayList<Recipe>();
    }

    public ArrayList<Recipe> getKnownRecipes() { return knownRecieps; }

    public void addRecipe(Recipe recipeToAdd) {
        knownRecieps.add(recipeToAdd);
        
    }

    public void addItem(Item item) {
        if(ownedItems.size() == availableSlots){ 
            return; //Нет места в инвентаре
        }
        // Уникальный предмет, место в инвентаре есть
        ownedItems.add(item);
        addObject(item, 0, 0);
    }

    public void removeItem(Item item) {
        int index = ownedItems.indexOf(item);
        if (index != -1) {
            //Удаляем из массива и сдвигаем элементы в нем
            removeObject(ownedItems.get(index));
            ownedItems.remove(index);
        }
        
    }

    public boolean addToCraft(Item item) {
        int index = craftItems.indexOf(item);
        if (index == -1) {
            craftItems.add(item);
            return true;
        }
        
        return false;
    }

    public boolean removeFromCraft(Item item) {
        int index = craftItems.indexOf(item);
        if (index != -1) {
            craftItems.remove(index);
            return true;
        }
        
        return false;
    }

    public void updateDisplay() {
        for (int i = 0; i < availableSlots; i++) {
            if (ownedItems.size() > (i) && ownedItems.get(i) != null) {
                ownedItems.get(i).setLocation(
                getWidth() * (i + 1) / (availableSlots + 1), 
                getHeight() / 2 - 35);
            }
        }
    }
}