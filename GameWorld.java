import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class GameWorld extends World
{
    private Menu menu;
    private Inventory inventory;
    private Player player;
    
    public enum GameState {
        IDLE,
        WAITING_FOR_BITE,
        FISHING_MINIGAME,
        FISH_CAUGHT,
        FISH_ESCAPED
    }

    static public enum ItemRaritiy {
        TRASH,
        COMMON,
        RARE,
        LEGENDARY
    }
    
    private GameState currentGameState = GameState.IDLE;
    private Bobber bobber;
    GreenfootSound backgroundMusic = new GreenfootSound("music/gone_fishing.wav");
    
    private ProgressBar catchingProgressBar;
    private ProgressBar levelingProgressBar;
    private BobberBar minigameContainer;
    private Bobber minigameBobberIcon;
    
    private int timeLeftInFrames = 0;
    private int timeLeftInSeconds;
    private final int FPS = 60;
    private ArrayList<Item> avaliableItems = new ArrayList<Item>();
    private ArrayList<Recipe> availableRecipes = new ArrayList<Recipe>();
    
    private int trashRarityIndex = 0, commonRarityIndex, rareRarityIndex, legendaryRarityIndex; //можно не указывать, автоматически определяются при создании мира

    private Icon levelIcon = new Icon("icons/level_1.png");
    
    public GameState getGameState() { return currentGameState; }
    public void setGameState(GameState newState) { currentGameState = newState; }
    
    public GameWorld(Menu menu)
    {    
        super(800, 600, 1); 
        this.menu = menu;
        this.inventory = new Inventory(this);
        this.player = new Player(inventory, this);
        setPaintOrder(Bobber.class, Fish.class, Icon.class, BobberBar.class);
        
        IncludesUploader IU = new IncludesUploader(this);
        avaliableItems = IU.uploadItems();
        availableRecipes = IU.uploadRecipes();   
            
            
        player.learnRecipesOnLevelUp(availableRecipes);
        findRarityIdeces();
        
        addObject(new Button("buttons/menu.png", () -> {
            Greenfoot.setWorld(menu);
            this.turnMusicOn(false);
        }), 100, 30);
        addObject(new Button("buttons/inventory.png", () -> {
            Greenfoot.setWorld(inventory);
            inventory.updateDisplay();
        }), getWidth() - 90, 90);
        //addObject(player, getWidth() / 2, 10);

        levelingProgressBar = new ProgressBar(200, 15, 0, true);
        addObject(levelingProgressBar, getWidth() / 2, 25);
        addObject(levelIcon, getWidth() / 2, 50);
    }
    
    public void act() {
        switch (currentGameState) {
            case IDLE:
                break;
            case WAITING_FOR_BITE:
                spawnFish();
                break;
            case FISHING_MINIGAME:
                bobber.shake();
                
                if (minigameContainer == null) {
                    startMinigame();
                }
                
                if (minigameBobberIcon.checkIntersection(minigameContainer.getFish())) 
                    catchingProgressBar.changeProgress(catchingProgressBar.getProgress()+0.005);
                else catchingProgressBar.changeProgress(catchingProgressBar.getProgress()-0.005);
                
                if (catchingProgressBar.getProgress() >= 1) setGameState(GameState.FISH_CAUGHT);
                else if (catchingProgressBar.getProgress() <= 0) setGameState(GameState.FISH_ESCAPED);
                break;
            case FISH_CAUGHT:                
                removeObject(catchingProgressBar);
                minigameContainer.destructor();
                removeObject(minigameBobberIcon);
                removeObject(minigameContainer);
                removeObject(bobber);
                
                minigameContainer = null;
                
                Item caughtItem = getRandomItem().getCopy();
                inventory.addItem(caughtItem);
                player.addExperience(caughtItem);
                
                setGameState(GameState.IDLE);
                break;
            case FISH_ESCAPED:
                removeObject(catchingProgressBar);
                minigameContainer.destructor();
                removeObject(minigameBobberIcon);
                removeObject(minigameContainer);
                removeObject(bobber);
                
                minigameContainer = null;
                setGameState(GameState.IDLE);
                break;
        }
        player.act();
    }
    
    public void startMinigame() {
        minigameContainer = new BobberBar();
        addObject(minigameContainer, getWidth() / 2, getHeight() / 2);
        
        minigameBobberIcon = new Bobber(minigameContainer);
        addObject(minigameBobberIcon, getWidth() / 2, getHeight() / 2);
    
        catchingProgressBar = new ProgressBar(10, 225, 0.35, false);
        addObject(catchingProgressBar, getWidth() / 2 + 33, getHeight() / 2);
    }
    
    public void spawnBobber() {
        bobber = new Bobber();
        addObject(bobber, 150, 450);
    }
    
    public void spawnFish() {
        if (timeLeftInFrames > 0) {
            timeLeftInFrames -= 1;
            if (timeLeftInFrames <= 0) {
                setGameState(GameState.FISHING_MINIGAME);
            }
        }
        else {
            timeLeftInSeconds = Greenfoot.getRandomNumber(5);
            timeLeftInFrames = timeLeftInSeconds * FPS;
        }
    }

    private void findRarityIdeces() {
        for (int i = trashRarityIndex; i < avaliableItems.size(); i++) {
            if (avaliableItems.get(i).getRarity() == ItemRaritiy.COMMON) {
                commonRarityIndex = i;
                break;
            }
        }
        for (int i = commonRarityIndex; i < avaliableItems.size(); i++) {
            if (avaliableItems.get(i).getRarity() == ItemRaritiy.RARE) {
                rareRarityIndex = i;
                break;
            }
        }
        for (int i = rareRarityIndex; i < avaliableItems.size(); i++) {
            if (avaliableItems.get(i).getRarity() == ItemRaritiy.LEGENDARY) {
                legendaryRarityIndex = i;
                break;
            }
        }
    }

    private Item getRandomItem() {
        int rarityCategory = Greenfoot.getRandomNumber(100) + 1;

        if (rarityCategory <= 50) {
            return avaliableItems.get(Greenfoot.getRandomNumber(commonRarityIndex - trashRarityIndex) + trashRarityIndex);
        }
        else if (rarityCategory <= 85) {
            return avaliableItems.get(Greenfoot.getRandomNumber(rareRarityIndex - commonRarityIndex) + commonRarityIndex);
        }
        else if (rarityCategory <= 99) {
            return avaliableItems.get(Greenfoot.getRandomNumber(legendaryRarityIndex - rareRarityIndex) + rareRarityIndex);
        }
        else {
            return avaliableItems.get(Greenfoot.getRandomNumber(avaliableItems.size() - legendaryRarityIndex) + legendaryRarityIndex);
        }
    }

    public ArrayList<Item> getAvaliableItems() { return avaliableItems; }
    public ArrayList<Recipe> getAvaliableRecipes() { return availableRecipes; }
    public Inventory getInventory() { return inventory; }
    public Player getPlayer() { return player; }

    public void updateLevelingProgressBar(double value) { levelingProgressBar.changeProgress(value); }
    public void updateLevelIcon(int level) {
        if (level == 2) levelIcon.setImage("icons/level_2.png");
        else if (level == 3) levelIcon.setImage("icons/level_3.png");
        else if (level == 4) levelIcon.setImage("icons/level_4.png");
        else if (level == 5) levelIcon.setImage("icons/level_5.png");
    }

    public void turnMusicOn(boolean isOn) {
        if (isOn) backgroundMusic.playLoop();
        else backgroundMusic.stop();
    }

    public void getToWinScreen() {
        Greenfoot.setWorld(new WinScreen(menu, this));
    }

    static boolean stringEqual(String first, String Second){
        if(first.length() != Second.length()) return false;
        for(int i = 0; i < first.length(); i++) {
            if(first.charAt(i) != Second.charAt(i)) return false;
        }
        return true;
    }
}