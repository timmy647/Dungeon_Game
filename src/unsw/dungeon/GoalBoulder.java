package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GoalBoulder implements Goal {
    BooleanProperty status;
    List<FloorSwitch> switches;
    List<Boulder> boulders;

    public GoalBoulder() {
        status = new SimpleBooleanProperty();
        boulders = null;
        switches = null;
        status.set(false);
    }

    public void update(List<FloorSwitch> switches, List<Boulder> boulders) {
        this.switches = switches;
        this.boulders = boulders;
        status.set(check());
    }

    @Override
    public boolean check() {
        if (boulders == null || switches == null || switches.isEmpty()) {
            return true;
        }
        for (FloorSwitch s : switches) {
            boolean found = false;
            for (Boulder b : boulders) {
                if (s.getX() == b.getX() && s.getY() == b.getY()) {
                    found = true;
                }
            }
            if (found == false) {
                return false;
            }
        }
        return true;
    }

    public BooleanProperty getStatus() {
        return status;
    }
}