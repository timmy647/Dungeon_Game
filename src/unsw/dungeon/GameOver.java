package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GameOver implements Goal {
    BooleanProperty status;
    public GameOver() {
        status = new SimpleBooleanProperty();
        status.set(false);
    }
    public void update(boolean alive) {
        this.status.set(!alive);
    }
    public boolean check() {
        return status.get();
    }
    public BooleanProperty getStatus() {
        return status;
    }
}