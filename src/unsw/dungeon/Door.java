package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {
    public int id;
    private BooleanProperty open;
    private Dungeon dungeon;

    public Door(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.id = id;
        this.open = new SimpleBooleanProperty(false);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
    }

    public int getId(){
        return id;
    }

    public BooleanProperty open(){
        return open;
    }

    public boolean getOpen(){
        return open.get();
    }

    public void setOpen(){
        open().set(true);
    }


    public boolean hasKey(Entity e){
        if (e instanceof Player){
            Player p = (Player)e;
            if (p.findkey(getId()) == true){
                setOpen();
                return true;
            }
        }
        return false;
    }

    @Override
    public int moveto(Entity e){
        if ((getOpen() == true) || (getOpen() == false && hasKey(e) == true)){
            e.setPosition(getX(), getY());
            return 0;
        }
        return 1;
    }

}