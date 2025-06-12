import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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

    public enum ItemRaritiy {
        TRASH,
        COMMON,
        RARE,
        LEGENDARY
    }
    
    private GameState currentGameState = GameState.IDLE;
    private Bobber bobber;
    
    private ProgressBar catchingProgressBar;
    private ProgressBar levelingProgressBar;
    private Container minigameContainer;
    private Bobber minigameBobberIcon;
    private Fish minigameFishIcon;
    
    private int timeLeftInFrames = 0;
    private int timeLeftInSeconds;
    private final int FPS = 60;

    //При добавлении новых элементов необходимо сохранять порядок редкости
    private final Item[] avaliableItems = {
        new Item("Fish Bones", ItemRaritiy.TRASH, "items/fish_bones.png"),
        new Item("Old Boot", ItemRaritiy.TRASH, "items/old_boot.png"),
        new Item("Common Carp", ItemRaritiy.COMMON, "items/common_carp.png"),
        new Item("Small Perch", ItemRaritiy.RARE, "items/small_perch.png"),
        new Item("Shiny Stone", ItemRaritiy.LEGENDARY, "items/shiny_stone.png"),
    };
    //Рецепт обязательно должен включать 3 компонента
    private final Recipe[] availableRecipes = {
        new Recipe(
            this,
            new Item[] { avaliableItems[0], avaliableItems[1], avaliableItems[2] },
            new Item("Test Item", ItemRaritiy.RARE, "items/shiny_stone.png"),
            1,
            1),
        //идеи: бафф на опыт, улучшение для инвентаря, сеть-ловушка (автоматическая)
    };
    private int trashRarityIndex = 0, commonRarityIndex, rareRarityIndex, legendaryRarityIndex; //можно не указывать, автоматически определяются при создании мира

    private Icon levelIcon = new Icon("icons/level_1.png");
    
    public GameState getGameState() { return currentGameState; }
    public void setGameState(GameState newState) { currentGameState = newState; }
    
    public GameWorld(Menu menu)
    {    
        super(800, 600, 1); 
        this.menu = menu;
        this.inventory = new Inventory(this);
        this.player = new Player(inventory);
        player.learnRecipesOnLevelUp(availableRecipes);


        findRarityIdeces();
        
        addObject(new Button("buttons/menu.png", () -> {
            Greenfoot.setWorld(menu);
        }), 100, 30);
        addObject(new Button("buttons/inventory.png", () -> {
            Greenfoot.setWorld(inventory);
            inventory.updateDisplay();
        }), getWidth() - 90, 90);
        addObject(player, getWidth() / 2, 10);

        levelingProgressBar = new ProgressBar(200, 15, 0);
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
                
                if (minigameBobberIcon.checkIntersection(minigameContainer.getFish())) catchingProgressBar.changeProgress(catchingProgressBar.getProgress()+0.005);
                else catchingProgressBar.changeProgress(catchingProgressBar.getProgress()-0.005);
                
                if (catchingProgressBar.getProgress() >= 1) setGameState(GameState.FISH_CAUGHT);
                else if (catchingProgressBar.getProgress() <= 0) setGameState(GameState.FISH_ESCAPED);
                break;
            case FISH_CAUGHT:                
                removeObject(catchingProgressBar);
                removeObject(minigameContainer.getFish());
                removeObject(minigameBobberIcon);
                removeObject(minigameContainer);
                removeObject(bobber);
                
                minigameContainer = null;
                
                Item caughtItem = getRandomItem();
                inventory.addItem(caughtItem);
                player.addExperience(caughtItem);
                
                setGameState(GameState.IDLE);
                break;
            case FISH_ESCAPED:
                //И ТУТ ТОЖЕ НАДО ВСЕ УДАЛЯТЬ
                // Показать сообщение, подождать, вернуться в IDLE
                removeObject(catchingProgressBar);
                removeObject(minigameContainer.getFish());
                removeObject(minigameBobberIcon);
                removeObject(minigameContainer);
                removeObject(bobber);
                
                minigameContainer = null;
                setGameState(GameState.IDLE);
                break;
        }
    }
    
    public void startMinigame() {
        minigameContainer = new Container();
        addObject(minigameContainer, getWidth() / 2, getHeight() / 2);
        
        //minigameFishIcon = new Fish(minigameContainer);
        minigameBobberIcon = new Bobber(minigameContainer);
        //addObject(minigameFishIcon, getWidth() / 2, getHeight() / 2);
        addObject(minigameBobberIcon, getWidth() / 2, getHeight() / 2);
    
        catchingProgressBar = new ProgressBar(200, 10, 0.35);
        addObject(catchingProgressBar, getWidth() / 2, getHeight() * 3 / 4);
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
        for (int i = trashRarityIndex; i < avaliableItems.length; i++) {
            if (avaliableItems[i].getRarity() == ItemRaritiy.COMMON) {
                commonRarityIndex = i;
                break;
            }
        }
        for (int i = commonRarityIndex; i < avaliableItems.length; i++) {
            if (avaliableItems[i].getRarity() == ItemRaritiy.RARE) {
                rareRarityIndex = i;
                break;
            }
        }
        for (int i = rareRarityIndex; i < avaliableItems.length; i++) {
            if (avaliableItems[i].getRarity() == ItemRaritiy.LEGENDARY) {
                legendaryRarityIndex = i;
                break;
            }
        }
    }

    private Item getRandomItem() {
        int rarityCategory = Greenfoot.getRandomNumber(100) + 1;

        if (rarityCategory <= 50) {
            return avaliableItems[Greenfoot.getRandomNumber(commonRarityIndex - trashRarityIndex) + trashRarityIndex];
        }
        else if (rarityCategory <= 85) {
            return avaliableItems[Greenfoot.getRandomNumber(rareRarityIndex - commonRarityIndex) + commonRarityIndex];
        }
        else if (rarityCategory <= 99) {
            return avaliableItems[Greenfoot.getRandomNumber(legendaryRarityIndex - rareRarityIndex) + rareRarityIndex];
        }
        else {
            return avaliableItems[Greenfoot.getRandomNumber(avaliableItems.length - legendaryRarityIndex) + legendaryRarityIndex];
        }
    }

    public Recipe[] getAvaliableRecipes() { return availableRecipes; }
    public Inventory getInventory() { return inventory; }
	public Player getPlayer() { return player; }

    public void updateLevelingProgressBar(double value) { levelingProgressBar.changeProgress(value); }
    public void updateLevelIcon(int level) {
        if (level == 2) levelIcon.setImage("icons/level_2.png");
        else if (level == 3) levelIcon.setImage("icons/level_3.png");
        else if (level == 4) levelIcon.setImage("icons/level_4.png");
        else if (level == 5) levelIcon.setImage("icons/level_5.png");
    }
}