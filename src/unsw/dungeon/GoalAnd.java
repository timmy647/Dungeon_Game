package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GoalAnd implements Goal {
    BooleanProperty status;
    List<Goal> goals;
    public GoalAnd() {
        status = new SimpleBooleanProperty();
        this.status.set(false);
        this.goals = new ArrayList<Goal>();
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
        status.set(check());
    }

    @Override
    public boolean check() {
        for (Goal g : goals) {
            if (g.check() == false) {
                return false;
            }
        }
        return true;
    }

    public BooleanProperty getStatus() {
        return status;
    }
    
}