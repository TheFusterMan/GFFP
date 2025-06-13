//import java.io.File;
//import java.io.FileInputStream;
import greenfoot.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class IncludesUploader  
{   
    GameWorld gameWorld;

    public IncludesUploader(GameWorld gw) {
        gameWorld = gw;
    }
    //шаблон: название_предмета;РЕДКОСТЬ
    //необходимо поддерживать порядок по возрастанию редкости сверху-вниз 
    public ArrayList<Item> uploadItems(){
        ArrayList<Item> allItems = new ArrayList<Item>();
        
        try(BufferedReader items = new BufferedReader(new FileReader("includes/items.txt"))) {
            String s;
            String[] temp;
            Item buffer;
            while((s = items.readLine()) != null){
                temp = s.split(";");
                buffer = new Item(temp[0], temp[1]);        
                allItems.add(buffer);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 

        return allItems;
    }
    
    //шаблон: требуемый_уровень;id_предмета_1;...;;id_предмета_N;;id_результирующего_предмета
    //все предметы, используемый в крафте, должны быть в файле items.txt
    public ArrayList<Recipe> uploadRecipes() {
        ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
        
        try(BufferedReader recipes = new BufferedReader(new FileReader("includes/recipes.txt"))) {
            String s;
            String[] temp, ingredients;
            Recipe buffer;
            while((s = recipes.readLine()) != null){
                temp = s.split("=");
                ingredients = temp[0].split(";");
                ArrayList<String> itemsForCraft = new ArrayList<String>();
                
                for (int i = 0; i < ingredients.length; i++) {
                    itemsForCraft.add(ingredients[i]);
                }

                buffer = new Recipe(gameWorld, itemsForCraft, temp[1], Integer.parseInt(temp[2]));        
                allRecipes.add(buffer);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 

        return allRecipes;
    }
}
