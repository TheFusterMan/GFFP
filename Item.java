
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Item extends Actor
{
    private String name;
    private GameWorld.ItemRaritiy rarity;
    private int quantity = 0;

    private int mouseOnTransparency = 218;
    private int mouseOffTransparency = 255;
    private int selectedTransparency = 128;
    
    private boolean isSelected = false;

    
    public Item(String name, String rarity) {
        this.name = name;
        
        switch(rarity){
            case "TRASH":
                this.rarity = GameWorld.ItemRaritiy.TRASH;
                break;
            case "COMMON":
                this.rarity = GameWorld.ItemRaritiy.COMMON;
                break;
            case "RARE":
                this.rarity = GameWorld.ItemRaritiy.RARE;
                break;
            case "LEGENDARY":
                this.rarity = GameWorld.ItemRaritiy.LEGENDARY;
                break;
        }
        
        setImage("items/" + name + ".png");
    }
    
    public Item(String name, GameWorld.ItemRaritiy rarity) {
        this.name = name;
        this.rarity = rarity;
        setImage("items/" + name + ".png");
    }

    public String getName() { return name; }
    public GameWorld.ItemRaritiy getRarity() { return rarity; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; } 
    
    public void increaseQuantity() { this.quantity++; }
    public void decreaseQuantity() { this.quantity--; }

    public void onClick(int buttonDesc) {
        Inventory inventory = (Inventory) getWorld();

        if (buttonDesc == 1) {
            if (isSelected) {
                isSelected = false;
                inventory.removeFromCraft(this);
            }
            else {
                isSelected = true;
                inventory.addToCraft(this);
            }
        }
        else if (buttonDesc == 3) {
            inventory.removeFromCraft(this);
            inventory.removeItem(this);
            inventory.updateDisplay();
        }
    }

    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();

        if (Greenfoot.mouseClicked(this)) {
            onClick(mouse.getButton());
        }

        if (isSelected) {
            getImage().setTransparency(selectedTransparency);
            return;
        }
        
        if (Greenfoot.mouseMoved(this)) {
            getImage().setTransparency(mouseOnTransparency);
        }
        
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)) {
            getImage().setTransparency(mouseOffTransparency);
        }
    }
}
