//import java.io.File;
//import java.io.FileInputStream;
import java.io.*;
import java.util.ArrayList;



/**
 * Write a description of class IncludesUploader here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IncludesUploader  
{
     
    
    public IncludesUploader(){
   
    //recipes = new FileInputStream("includes/recipes.txt"); 
    
        
    
    }
    
    public ArrayList<Item> uploadItems(){
        ArrayList<Item> allItems = new ArrayList<Item>();
        
        try(BufferedReader items = new BufferedReader(new FileReader("includes/items.txt"))) {
            //чтение построчно
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
        
        /*
        try(BufferedReader recipes = new BufferedReader(new FileReader("includes/recipes.txt"))) {
            //чтение построчно
            String s;
            while((s = recipes.readLine()) != null){
                         
                
            }
        }
         catch(IOException ex){
                     
            System.out.println(ex.getMessage());
        }
        
        */
        return allItems;
    }
    
    
    
}
