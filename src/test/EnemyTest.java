package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Archer;
import unsw.dungeon.Arrow;
import unsw.dungeon.Boss;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;

public class EnemyTest {
    @Test
    public void EnemyTests() {
        Dungeon d = new Dungeon(5, 5);
        Enemy enemy = new Enemy(d, 0, 2);
        Enemy e2 = new Enemy(d, 2, 0);
        Enemy e3 = new Enemy(d, 2, 4);
        Enemy e4 = new Enemy(d, 4, 2);
        enemy.approach();
        assert enemy.getX() == 0 && enemy.getY() == 2 : "Testing Enemy Approach when no player";
        Player player = new Player(d, 2, 2);
        enemy.approach();
        e2.approach();
        e3.approach();
        e4.approach();
        assert enemy.getX() == 1 && enemy.getY() == 2 : "Testing Enemy Approach";
        assert e2.getX() == 2 && e2.getY() == 1 : "Testing Enemy Approach";
        assert e3.getX() == 2 && e3.getY() == 3 : "Testing Enemy Approach";
        assert e4.getX() == 3 && e4.getY() == 2 : "Testing Enemy Approach";
        enemy.approach();
        assert player.getAlive() == false : "Testing Enemy Attack";
        enemy.gotKilled();
        assert enemy.getAlive() == false : "Testing Enemy Got Killed";
    }

    @Test
    public void EnemyRunAway() {
        Dungeon d = new Dungeon(5, 5);
        Player player = new Player(d, 2, 2);
        Enemy e1 = new Enemy(d, 1, 2);
        Enemy e2 = new Enemy(d, 2, 1);
        Enemy e3 = new Enemy(d, 2, 3);
        Enemy e4 = new Enemy(d, 3, 2);
        e1.runaway();
        e2.runaway();
        e3.runaway();
        e4.runaway();
        System.out.println(e1.getX()+","+e1.getY());
        assert e1.getX() == 0 && e1.getY() == 2 : "Testing Enemy Runaway";
        assert e2.getX() == 2 && e2.getY() == 0 : "Testing Enemy Runaway";
        assert e3.getX() == 2 && e3.getY() == 4 : "Testing Enemy Runaway";
        assert e4.getX() == 4 && e4.getY() == 2 : "Testing Enemy Runaway";
    }

    @Test
    public void DungeonFoeMovement() {
        Dungeon d = new Dungeon(5, 5);
        Player player = new Player(d, 2, 2);
        player.setInvincibity(true);
        Enemy e1 = new Enemy(d, 1, 2);
        Enemy e2 = new Enemy(d, 2, 1);
        Enemy e3 = new Enemy(d, 2, 3);
        Enemy e4 = new Enemy(d, 3, 2);
        d.moveEnemies();
        assert e1.getX() == 0 && e1.getY() == 2 : "Testing Enemy Runaway";
        assert e2.getX() == 2 && e2.getY() == 0 : "Testing Enemy Runaway";
        assert e3.getX() == 2 && e3.getY() == 4 : "Testing Enemy Runaway";
        assert e4.getX() == 4 && e4.getY() == 2 : "Testing Enemy Runaway";
        player.setInvincibity(false);
        d.moveEnemies();
        assert e1.getX() == 1 && e1.getY() == 2 : "Testing Enemy approach";
        assert e2.getX() == 2 && e2.getY() == 1 : "Testing Enemy approach";
        assert e3.getX() == 2 && e3.getY() == 3 : "Testing Enemy approach";
        assert e4.getX() == 3 && e4.getY() == 2 : "Testing Enemy approach";
    }

    @Test
    public void ArcherTests() {
        Dungeon d = new Dungeon(5, 5);
        Archer enemy = new Archer(d, 0, 2);
        Archer e2 = new Archer(d, 2, 0);
        Archer e3 = new Archer(d, 2, 4);
        Archer e4 = new Archer(d, 4, 2);
        enemy.approach();
        assert enemy.getX() == 0 && enemy.getY() == 2 : "Testing Archer Approach when no player";
        Player player = new Player(d, 2, 2);
        enemy.approach();
        e2.approach();
        e3.approach();
        e4.approach();
        assert enemy.getX() == 1 && enemy.getY() == 2 : "Testing Archer Approach";
        assert e2.getX() == 2 && e2.getY() == 1 : "Testing Archer Approach";
        assert e3.getX() == 2 && e3.getY() == 3 : "Testing Archer Approach";
        assert e4.getX() == 3 && e4.getY() == 2 : "Testing Archer Approach";
        enemy.approach();
        assert player.getAlive() == true : "Testing Archer not killing";
        enemy.gotKilled();
        assert enemy.getAlive() == false : "Testing Archer Got Killed";
    }

    @Test
    public void ArcherRunAway() {
        Dungeon d = new Dungeon(5, 5);
        Player player = new Player(d, 2, 2);
        Archer e1 = new Archer(d, 1, 2);
        Archer e2 = new Archer(d, 2, 1);
        Archer e3 = new Archer(d, 2, 3);
        Archer e4 = new Archer(d, 3, 2);
        System.out.printf("%d %d\n",d.getPlayer().getX(),d.getPlayer().getY());
        assert e1.playerVicinity() == true:"test vicinity1";
        assert e2.playerVicinity() == true:"test vicinity2";
        assert e3.playerVicinity() == true:"test vicinity3";
        assert e4.playerVicinity() == true:"test vicinity4";
        if (e1.playerVicinity() == true)
            e1.runaway();
        if (e2.playerVicinity() == true)
            e2.runaway();
        if (e3.playerVicinity() == true)
            e3.runaway();
        if (e4.playerVicinity() == true)
            e4.runaway();
        assert e1.getX() == 0 && e1.getY() == 2 : "Testing Archer Runaway";
        assert e2.getX() == 2 && e2.getY() == 0 : "Testing Archer Runaway";
        assert e3.getX() == 2 && e3.getY() == 4 : "Testing Archer Runaway";
        assert e4.getX() == 4 && e4.getY() == 2 : "Testing Archer Runaway";
        if (e1.playerVicinity() == true)
            e1.runaway();
        if (e2.playerVicinity() == true)
            e2.runaway();
        if (e3.playerVicinity() == true)
            e3.runaway();
        if (e4.playerVicinity() == true)
            e4.runaway();
        assert e1.getX() == 0 && e1.getY() == 2 : "Testing Archer not moving";
        assert e2.getX() == 2 && e2.getY() == 0 : "Testing Archer not moving";
        assert e3.getX() == 2 && e3.getY() == 4 : "Testing Archer not moving";
        assert e4.getX() == 4 && e4.getY() == 2 : "Testing Archer not moving";
    }


    @Test
    public void ArrowTest(){
        Dungeon d = new Dungeon(5, 5);
        Arrow aUp = new Arrow(d, 2, 2,"up");
        Arrow aDown = new Arrow(d, 2, 2,"down");
        Arrow aLeft = new Arrow(d, 2, 2,"left");
        Arrow aRight = new Arrow(d, 2, 2,"right");

        Player e1 = new Player(d, 0, 2);
        Archer e2 = new Archer(d, 2, 0);
        Player e3 = new Player(d, 2, 4); 
               e3.setInvincibity(true);
        Enemy e4 = new Enemy(d, 4, 2);

        d.moveArrows();
        assert e1.getAlive() == true : "Testing Player lives";
        assert e2.getAlive() == true : "Testing Rcher lives";
        assert e3.getAlive() == true : "Testing Archer lives";
        assert e4.getAlive() == true : "Testing Enemy lives";
        assert e1.getExists() == true : "Testing Player lives";
        assert e2.getExists() == true : "Testing Rcher lives";
        assert e3.getExists() == true : "Testing Archer lives";
        assert e4.getExists() == true : "Testing Enemy lives";
        assert (d.findArcher()).size() == 1:"testing 1 archer left";
        assert aUp.getExists() == true:"testing arrow wasn't destroyed";
        assert aDown.getExists() == true:"testing arrowd wasn't destroyed";
        assert aLeft.getExists() == true:"testing arrow wasn't destroyed";
        assert aRight.getExists() == true:"testing arrow wasn't destroyed";

        d.moveArrows();
        assert e1.getAlive() == false : "Testing Player died";
        assert e2.getAlive() == false : "Testing Rcher died";
        assert e3.getAlive() == true : "Testing Invincible lived";
        assert e4.getAlive() == false : "Testing Enemy died";
        assert e1.getExists() == false : "Testing Player died";
        assert e2.getExists() == false : "Testing Rcher died";
        assert e3.getExists() == true : "Testing Invincible lived";
        assert e4.getExists() == false : "Testing Enemy died";
        assert (d.findArcher()).size() == 0:"testing 0 archers left";

        assert aDown.getExists() == false:"testing arrow was destroyed";
        

        d.moveArrows();
        assert aUp.getExists() == false:"testing arrow was destroyed";
        assert aDown.getExists() == false:"testing arrow was destroyed";
        assert aLeft.getExists() == false:"testing arrow was destroyed";
        assert aRight.getExists() == false:"testing arrow was destroyed";
    }

    @Test
    public void ArcherFireTest(){
        Dungeon dungeon = new Dungeon(5, 5);
        Player p = new Player(dungeon, 0, 2);
        Archer a = new Archer(dungeon, 2, 0);
        Arrow f = null;
        a.moveLeft();
        f = FireTestHelper(dungeon, a, f);
        assert f == null:"testing fireball not cast";
        a.moveLeft();
        f = FireTestHelper(dungeon, a, f);
        assert f.getExists() == false:"testing fireball cast and destroyed";
        assert p.getAlive() == false:"testing fireball killed player";

        a.setPosition(2, 2);
        p = new Player(dungeon, 0, 2);
        f = FireTestHelper(dungeon, a, f);
        assert f.getExists() == false:"testing fireball cast and destroyed";
        assert p.getAlive() == false:"testing fireball killed player";
        p = new Player(dungeon, 2, 0);
        f = FireTestHelper(dungeon, a, f);
        assert f.getExists() == false:"testing fireball cast and destroyed";
        assert p.getAlive() == false:"testing fireball killed player";
        p = new Player(dungeon, 4, 2);
        f = FireTestHelper(dungeon, a, f);
        assert f.getExists() == false:"testing fireball cast and destroyed";
        assert p.getAlive() == false:"testing fireball killed player";
    }

    public Arrow FireTestHelper(Dungeon dungeon, Archer a, Arrow f){
        if (a.lineOfFire() != "false"){
            f = new Arrow(dungeon, a.getX(), a.getY(), a.lineOfFire());
            for (int i=0;i<5;i++)
                f.move();
            return f;
        }
        return null;
    }

    @Test
    public void BossKnockbackTest(){
        Dungeon dungeon = new Dungeon(5, 5);
        Player p = new Player(dungeon, 2, 2);
        Boss e1 = new Boss(dungeon, 2, 1);
        Boss e2 = new Boss(dungeon, 1, 2);
        Boss e3 = new Boss(dungeon, 2, 3);
        Boss e4 = new Boss(dungeon, 3, 2);
        p.addSword();
        p.hitSwordHelper(2, 1);
        assert dungeon.findEntity(2, 1) == null:"bos not in previous position";
        assert dungeon.findEntity(2, 0) instanceof Boss:"bos in opposite position";
        p.hitSwordHelper(1, 2);
        assert dungeon.findEntity(1, 2) == null:"bos not in previous position";
        assert dungeon.findEntity(0, 2) instanceof Boss:"bos in opposite position";
        p.hitSwordHelper(2, 3);
        assert dungeon.findEntity(2, 3) == null:"bos not in previous position";
        assert dungeon.findEntity(2, 4) instanceof Boss:"bos in opposite position";
        p.hitSwordHelper(3, 2);
        assert dungeon.findEntity(3, 2) == null:"bos not in previous position";
        assert dungeon.findEntity(4, 2) instanceof Boss:"bos in opposite position";
        assert e1.getHealth() == 9:"boss health = 9";
        assert e2.getHealth() == 9:"boss health = 9";
        assert e3.getHealth() == 9:"boss health = 9";
        assert e4.getHealth() == 9:"boss health = 9";

        p.hitSwordHelper(2, 1);
        assert dungeon.findEntity(2, 1) == null:"bos not in previous position";
        assert dungeon.findEntity(2, 0) instanceof Boss:"bos in opposite position";
        p.hitSwordHelper(1, 2);
        assert dungeon.findEntity(1, 2) == null:"bos not in previous position";
        assert dungeon.findEntity(0, 2) instanceof Boss:"bos in opposite position";
        p.hitSwordHelper(2, 3);
        assert dungeon.findEntity(2, 3) == null:"bos not in previous position";
        assert dungeon.findEntity(2, 4) instanceof Boss:"bos in opposite position";
        p.hitSwordHelper(3, 2);
        assert dungeon.findEntity(3, 2) == null:"bos not in previous position";
        assert dungeon.findEntity(4, 2) instanceof Boss:"bos in opposite position";
        assert e1.getHealth() == 9:"boss health = 9";
        assert e2.getHealth() == 9:"boss health = 9";
        assert e3.getHealth() == 9:"boss health = 9";
        assert e4.getHealth() == 9:"boss health = 9";
    }
}