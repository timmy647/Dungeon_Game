package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public interface Goal {
    public boolean check();
    public BooleanProperty getStatus();
}