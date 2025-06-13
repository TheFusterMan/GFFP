//import java.io.File;
//import java.io.FileInputStream;
import greenfoot.*;
import java.io.*;
import java.util.ArrayList;


public class IncludesUploader  
{   
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
    
    //шаблон: требуемый_уровень;предмет_1;...;предмет_N;результирующий_предмет
    //все предметы, используемый в крафте, должны быть в файле items.txt
    public ArrayList<Item> uploadRecipes(){
        ArrayList<Item> allItems = new ArrayList<Item>();
            
        try(BufferedReader items = new BufferedReader(new FileReader("includes/recipes.txt"))) {
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
}
