import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Inventory extends World
{
    private int availableSlots = 4;
    private final int maxSlots = 8;
    private final int slotsPerRow = 4;
    private final int craftSlots = 3;

    private GameWorld gameWorld;
    
    private ArrayList<Item> ownedItems = new ArrayList<Item>();
    private ArrayList<Item> craftItems = new ArrayList<Item>();;
    private ArrayList<Recipe> knownRecieps;

    public Inventory(GameWorld gw)
    {    
        super(600, 400, 1);

        addObject(new Button("buttons/back.png", () -> {
            Greenfoot.setWorld(gameWorld);
        }), getWidth() / 2, getHeight() - 25);
        addObject(new Button("buttons/craft.png", () -> {
            for (Recipe recipe : knownRecieps) {
                if (recipe != null) recipe.Craft(craftItems);
            }
        }), getWidth() / 2, getHeight() - 75);
        
        gameWorld = gw; 
        knownRecieps = new ArrayList<Recipe>(/*gameWorld.getAvaliableRecipes().size()*/);
        /*
        for(int i = 0; i < ownedItems.size(); i++){
            ownedItems.set(i, null);
        }
        for(int i = 0; i < craftItems.size(); i++){
            craftItems.set(i, null);
        }
        for(int i = 0; i < knownRecieps.size(); i++){
            craftItems.set(i, null);
        }*/
    }

    public ArrayList<Recipe> getKnownRecipes() { return knownRecieps; }

    public void addRecipe(Recipe recipeToAdd) {
        knownRecieps.add(recipeToAdd);
        
    }

    public void addItem(Item item) {
        int index = ownedItems.indexOf(item);
        if (index != -1) {
            ownedItems.get(index).increaseQuantity();
            return;
        }
        if(ownedItems.size() == maxSlots){ 
            return; //Нет места в инвентаре
        }
        // Уникальный предмет, место в инвентаре есть
        item.setQuantity(1);
        ownedItems.add(item);
        //index = ownedItems.indexOf(item);
        addObject(item, 0, 0);
        
        
    }

    public void removeItem(Item item) {
        int index = ownedItems.indexOf(item);
        if (index != -1) {
            ownedItems.get(index).decreaseQuantity();
            //Удаляем из массива и сдвигаем элементы в нем
            if (ownedItems.get(index).getQuantity() == 0) {
                removeObject(ownedItems.get(index));
                ownedItems.remove(index);
                /*
                for (int j = i + 1; j < availableSlots; j++) {
                    ownedItems[j - 1] = ownedItems[j];
                    ownedItems[j] = null;
                }*/
            }
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
        for (int i = 0; i < ownedItems.size(); i++) {
            //if (craftItems.get(i) == null) return;
            ownedItems.get(i).setLocation(getWidth() * (i + 1) / (slotsPerRow + 1), 
            (getHeight() - 200) * 1 / (maxSlots / slotsPerRow));
            drawQuantity(ownedItems.get(i), i);
        }
    }

    public void drawQuantity(Item item, int position) {
        String strNumber = Integer.toString(item.getQuantity());
        addObject(new Icon(
            new GreenfootImage(
                strNumber, 
                32, 
                Color.BLACK, 
                Color.WHITE)
        ), getWidth() * (position + 1) / (slotsPerRow + 1),  (getHeight() - 200) * 1 / (maxSlots / slotsPerRow) - 50);
    }
}