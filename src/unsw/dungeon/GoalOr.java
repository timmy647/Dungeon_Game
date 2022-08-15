package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GoalOr implements Goal {
    BooleanProperty status;
    List<Goal> goals;
    public GoalOr() {
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
            if (g.check() == true) {
                return true;
            }
        }
        return false;
    }

    public BooleanProperty getStatus() {
        return status;
    }
}