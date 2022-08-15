package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GoalEnemy implements Goal {
    BooleanProperty status;
    List<Enemy> enemies;
    List<Archer> archers;
    List<Boss> boss;
    public GoalEnemy() {
        status = new SimpleBooleanProperty();
        enemies = null;
        archers = null;
        boss = null;
        status.set(false);
    }

    public void update(List<Enemy> enemies, List<Archer> archers, List<Boss> boss) {
        this.enemies = enemies;
        this.archers = archers;
        this.boss = boss;
        status.set(check());
    }

    @Override
    public boolean check() {
        if (enemies == null && archers == null && boss == null) {
            return true;
        } else {
            return (enemies.isEmpty() && archers.isEmpty() && boss.isEmpty());
        }
    }

    public BooleanProperty getStatus() {
        return status;
    }

}