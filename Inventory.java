import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Inventory extends World
{
    private int availableSlots = 4;
    private final int maxSlots = 8;
    private final int slotsPerRow = 4;
    private final int craftSlots = 3;

    private GameWorld gameWorld;
    
    private Item[] ownedItems = new Item[maxSlots];
    private Item[] craftItems = new Item[craftSlots];
    private Recipe[] knownRecieps;

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
        knownRecieps = new Recipe[gameWorld.getAvaliableRecipes().length];
    }

    public Recipe[] getKnownRecipes() { return knownRecieps; }

    public void addRecipe(Recipe recipeToAdd) {
        for (int i = 0; i < knownRecieps.length; i++) {
            if (knownRecieps[i] == null) knownRecieps[i] = recipeToAdd;
        }
    }

    public void addItem(Item item) {
        for (int i = 0; i < availableSlots; i++) {
            if (ownedItems[i] == item) {
                ownedItems[i].setQuantity(ownedItems[i].getQuantity() + 1);
                return;
            }
            else if (ownedItems[i] == null) {
                item.setQuantity(1);
                ownedItems[i] = item;
                addObject(ownedItems[i], 0, 0);
                return;
            }
        }
        //Нет места в инвентаре
    }

    public void removeItem(Item item) {
        for (int i = 0; i < availableSlots; i++) {
            if (ownedItems[i] == item) {
                ownedItems[i].setQuantity(ownedItems[i].getQuantity() - 1);
                //Удаляем из массива и сдвигаем элементы в нем
                if (ownedItems[i].getQuantity() == 0) {
                    removeObject(ownedItems[i]);
                    for (int j = i + 1; j < availableSlots; j++) {
                        ownedItems[j - 1] = ownedItems[j];
                        ownedItems[j] = null;
                    }
                }
                return;
            }
        }
    }

    public boolean addToCraft(Item item) {
        for (int i = 0; i < craftSlots; i++) {
            if (craftItems[i] == null) {
                craftItems[i] = item;
                return true;
            }
        }
        return false;
    }

    public boolean removeFromCraft(Item item) {
        for (int i = 0; i < craftSlots; i++) {
            if (craftItems[i] == item) {
                craftItems[i] = null;
                return true;
            }
        }
        return false;
    }

    public void updateDisplay() {
        for (int i = 0; i < availableSlots; i++) {
            if (ownedItems[i] == null) return;
            ownedItems[i].setLocation(getWidth() * (i + 1) / (slotsPerRow + 1), 
            (getHeight() - 200) * 1 / (maxSlots / slotsPerRow));
            drawQuantity(ownedItems[i], i);
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
        ), getWidth() * (position + 1) / (slotsPerRow + 1) + 50,  (getHeight() - 200) * 1 / (maxSlots / slotsPerRow) - 50);
    }
}