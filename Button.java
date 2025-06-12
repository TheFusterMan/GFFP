
import greenfoot.*;

@FunctionalInterface
interface IAction {
    void execute(); 
}

public class Button extends Actor
{   
    private IAction action;
    
    protected int mouseOnTransparency = 218;
    protected int mouseOffTransparency = 255;
    
    public Button(String imgName, IAction action) {   
        setImage(imgName);
        this.action = action;
    }
    
    public void onClick() {
        action.execute();
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            onClick();
        }
        
        if (Greenfoot.mouseMoved(this)) {
            getImage().setTransparency(mouseOnTransparency);
        }
        
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)) {
            getImage().setTransparency(mouseOffTransparency);
        }
    }
}
