package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GoalTreasure implements Goal {
    BooleanProperty status;
    List<Treasure> treasures;
    public GoalTreasure() {
        status = new SimpleBooleanProperty();
        treasures = null;
        status.set(false);
    }

    public void update(List<Treasure> treasures) {
        this.treasures = treasures;
        status.set(check());
    }

    @Override
    public boolean check() {
        if (treasures == null) {
            return true;
        } else {
            return treasures.isEmpty();
        }
    }

    public BooleanProperty getStatus() {
        return status;
    }
}