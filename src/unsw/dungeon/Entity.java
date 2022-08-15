package unsw.dungeon;

import javafx.scene.image.ImageView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty exists;
    //private ImageView view;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.exists = new SimpleBooleanProperty(true);
        //this.view = null;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public BooleanProperty exists(){
        return exists;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }


    public abstract int moveto(Entity e);

    public void setX(int x) {
        x().set(x);
    }

    public void setY(int y) {
        y().set(y);
    }
    
    public void setPosition(int x,int y){
        x().set(x);
        y().set(y);
    }

    public boolean getExists() {
        return exists().get();
    }

    public void setExists(Boolean exists) {
        exists().set(exists);
    }
}
