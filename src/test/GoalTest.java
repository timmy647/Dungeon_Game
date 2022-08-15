package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.Boulder;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Exit;
import unsw.dungeon.FloorSwitch;
import unsw.dungeon.GameOver;
import unsw.dungeon.GoalAnd;
import unsw.dungeon.GoalBoulder;
import unsw.dungeon.GoalEnemy;
import unsw.dungeon.GoalExit;
import unsw.dungeon.GoalOr;
import unsw.dungeon.GoalTreasure;
import unsw.dungeon.Player;
import unsw.dungeon.Treasure;

public class GoalTest {
    @Test
    public void ExitGoalTest() {
        Dungeon d = new Dungeon(5, 5);
        Player player = new Player(d, 0, 0);
        Exit exit = new Exit(d, 1, 0);
        GoalExit g = d.getGoalExit();
        assert g.getStatus().get() == false : "Testing status for false case";
        assert g.check() == false : "Testing Exit Goals for false case";
        player.moveRight();
        assert g.check() == true : "Testing Exit Goals for true case";
    }

    @Test
    public void EnemyGoalTest() {
        Dungeon d = new Dungeon(5, 5);
        GoalEnemy g1 = d.getGoalEnemy();
        assert g1.check() == true : "Testing Enemy Goals for false case1";
        Enemy e = new Enemy(d, 1, 1);
        GoalEnemy g = d.getGoalEnemy();
        assert g.getStatus().get() == false : "Testing status for false case";
        assert g.check() == false : "Testing Enemy Goals for false case2";
        e.gotKilled();
        assert g.check() == true : "Testing Enemy Goals for true case";
    }

    @Test
    public void BoulderGoalTest() {
        Dungeon d = new Dungeon(5, 5);
        Boulder b = new Boulder(d, 2, 2);
        FloorSwitch s = new FloorSwitch(d, 1, 1);
        GoalBoulder g = d.getGoalBoulder();
        assert g.getStatus().get() == false : "Testing status for false case";
        assert g.check() == false : "Testing Boulder Goals for false case";
        Boulder b2 = new Boulder(d, 1, 1);
        assert g.check() == true : "Testing Boulder Goals for true case";
    }

    @Test
    public void TreasureGoalTest() {
        Dungeon d = new Dungeon(5, 5);
        GoalTreasure g = d.getGoalTreasure();
        assert g.check() == true : "Testing status without treasure";
        Treasure t = new Treasure(d, 1, 1);
        assert g.getStatus().get() == false : "Testing status for false case";
        assert g.check() == false : "Testing Treasure Goals for false case";
        d.removeEntity(t);
        assert g.check() == true : "Testing Treasure Goals for true case";
    }

    @Test
    public void ANDGoad() {
        Dungeon d = new Dungeon(5, 5);
        Player player = new Player(d, 0, 0);
        Exit exit = new Exit(d, 1, 0);
        GoalExit g1 = d.getGoalExit();
        Enemy e = new Enemy(d, 1, 1);
        GoalEnemy g2 = d.getGoalEnemy();
        GoalAnd g = new GoalAnd();
        g.addGoal(g1);
        g.addGoal(g2);
        assert g.getStatus().get() == false : "Testing status for false case";
        assert g.check() == false : "Testing AND Goals for no true goal";
        player.moveRight();
        assert g.check() == false : "Testing AND Goals for 1 true and 1 false";
        e.gotKilled();
        assert g.check() == true : "Testing AND Goals for all trues";
        g.getGoals();
    }

    @Test
    public void ORGoal() {
        Dungeon d = new Dungeon(5, 5);
        Player player = new Player(d, 0, 0);
        Exit exit = new Exit(d, 1, 0);
        GoalExit g1 = d.getGoalExit();
        Enemy e = new Enemy(d, 1, 1);
        GoalEnemy g2 = d.getGoalEnemy();
        GoalOr g = new GoalOr();
        g.addGoal(g1);
        g.addGoal(g2);
        assert g.getStatus().get() == false : "Testing status for false case";
        assert g.check() == false : "Testing AND Goals for no true goal";
        player.moveRight();
        assert g.check() == true : "Testing AND Goals for 1 true and 1 false";
        e.gotKilled();
        assert g.check() == true : "Testing AND Goals for all trues";
        g.getGoals();
    }

    @Test
    public void GameoverTest() {
        Dungeon d = new Dungeon(5, 5);
        Player player = new Player(d, 0, 0);
        Enemy e = new Enemy(d, 1, 1);
        GameOver go = d.getGameOver();
        d.setGoal(go);
        assert d.getGoal() == go:"goal set";
        assert d.getGameOver() instanceof GameOver:"ameover set";
        assert go.getStatus().get() == false : "Testing gameover for false case";
        assert go.check() == false : "Testing no game not over";//
        player.setPosition(0, 1);
        e.approach();
        assert go.check() == true : "Testing gameover for true case";
    }
}