package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GoalExit implements Goal {
    BooleanProperty status;
    Player player;
    Exit exit;
    public GoalExit() {
        status = new SimpleBooleanProperty();
        player = null;
        this.status.set(false);
    }

    public void update(Player player, Exit exit) {
        this.exit = exit;
        this.player = player; 
        status.set(check());
    }
    
    @Override
    public boolean check() {
        if (player == null || exit == null) {
            return false;
        }
        if (player.getX() == exit.getX() && player.getY() == exit.getY()) {
            return true;
        }
        return false;
    }

    public BooleanProperty getStatus() {
        return status;
    }
}