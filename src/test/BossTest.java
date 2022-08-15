package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.Dungeon;

import unsw.dungeon.Player;
import unsw.dungeon.Enemy;
import unsw.dungeon.Archer;
import unsw.dungeon.Arrow;
import unsw.dungeon.Boss;
import unsw.dungeon.Boulder;

import unsw.dungeon.Wall;
import unsw.dungeon.Wand;
import unsw.dungeon.Door;
import unsw.dungeon.Exit;
import unsw.dungeon.Portal;
import unsw.dungeon.FloorSwitch;

import unsw.dungeon.InvincibilityPotion;
import unsw.dungeon.Key;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;

public class BossTest {
    @Test
    public void BossMove(){
        Dungeon dungeon = new Dungeon(1, 1);
        Boss b = new Boss(dungeon, 0, 0);
        assert b.moveUp() == false:"boss can't move ";
        assert b.moveDown() == false:"boss can't move ";
        assert b.moveLeft() == false:"boss can't move ";
        assert b.moveRight() == false:"boss can't move ";
        Dungeon dungeon2 = new Dungeon(3, 3);
        b = new Boss(dungeon2, 1, 1);
        Boss b1 = new Boss(dungeon2, 0, 1);
        Boss b2 = new Boss(dungeon2, 2, 1);
        Boss b3 = new Boss(dungeon2, 1, 0);
        Boss b4 = new Boss(dungeon2, 1, 2);
        assert b.moveUp() == false:"boss can't move1 ";
        assert b.moveDown() == false:"boss can't move2 ";
        assert b.moveLeft() == false:"boss can't move3 ";
        assert b.moveRight() == false:"boss can't move4 ";
    }
    @Test
    public void BossApproach(){
        Dungeon dungeon = new Dungeon(3, 3);
        Boss b = new Boss(dungeon, 1, 1);
        b.runaway();
        Player p = new Player(dungeon, 0, 1);
        b.setPosition(1,1);
        b.approach();
        assert b.getX() == 0 && b.getY() == 1:"boss moved";
        p = new Player(dungeon, 2, 1);
        b.setPosition(1,1);
        b.approach();
        assert b.getX() == 2 && b.getY() == 1:"boss moved";
        p = new Player(dungeon, 1, 0);
        b.setPosition(1,1);
        b.approach();
        assert b.getX() == 1 && b.getY() == 0:"boss moved";
        p = new Player(dungeon, 1, 2);
        b.setPosition(1,1);
        b.approach();
        assert b.getX() == 1 && b.getY() == 2:"boss moved";
    }
    @Test
    public void Boss(){
        Dungeon dungeon = new Dungeon(12, 12);
        Boss b = new Boss(dungeon, 5, 5);
        b.approach();
        assert (b.getX() == 5) && (b.getY() == 5):"boss doesn't move";
        Player p = new Player(dungeon, 5, 4);
        Player pi = new Player(dungeon, 5, 6);
        pi.setInvincibity(true);
        assert p.moveDown() == false:"player can't move into boss";
        assert dungeon.findBoss().size() == 1:"1 boss";
        assert (b.moveUp() == true) && (p.getExists() == false):"testing normal player died";
        b.moveDown();
        assert (b.moveDown() == true) && (pi.getExists() == false):"testing invincible player died";
        b.setPosition(5, 5);
        p = new Player(dungeon, 5, 3);
        Wall w = new Wall(dungeon, 5, 4);
        b.approach();
        assert (b.getX() == 5) && (b.getY() == 4):"boss flies over wall";
        b.approach();
        assert (b.getX() == 5) && (b.getY() == 3):"boss kills";
        b.setPosition(5, 5);
        Wall w1 = new Wall(dungeon, 5, 6);
        Wall w2 = new Wall(dungeon, 4, 5);
        Wall w3 = new Wall(dungeon, 6, 5);
        assert b.moveDown() == true:"boss down";
        b.setPosition(5, 5);
        assert b.moveLeft() == true:"boss down";
        b.setPosition(5, 5);
        assert b.moveRight() == true:"boss down";
        b.setPosition(5, 5);
        Arrow a = new Arrow(dungeon, 5, 4, "down");
        assert b.getHealth() == 10:"boss is at 10";
        a.move();
        assert b.getHealth() == 9:"boss is at 9";
        assert a.getExists() == false:"fireball destroyed";
        dungeon.removeEntity(w);
        p = new Player(dungeon, 5, 4);
        p.setSword(50);
        p.setPosition(5, 4);
        b.setPosition(5, 5);
        p.hitSwordHelper(5, 5);
        assert (b.getX() == 5) && (b.getY() == 6):"boss is knoced back";
        assert b.getHealth() == 8:"boss is at 8";
        p.setPosition(5, 6);
        b.setPosition(5, 5);
        p.hitSwordHelper(5, 5);
        assert (b.getX() == 5) && (b.getY() == 4):"boss is knoced back";
        assert b.getHealth() == 7:"boss is at 7";
        p.setPosition(4, 5);
        b.setPosition(5, 5);
        p.hitSwordHelper(5, 5);
        assert (b.getX() == 6) && (b.getY() == 5):"boss is knoced back";
        assert b.getHealth() == 6:"boss is at 6";
        p.setPosition(6, 5);
        b.setPosition(5, 5);
        p.hitSwordHelper(5, 5);
        assert (b.getX() == 4) && (b.getY() == 5):"boss is knoced back";
        assert b.getHealth() == 5:"boss is at 5";
        b.setPosition(0, 0);
        p.setPosition(0, 1);
        p.hitSwordHelper(0, 0);
        assert (b.getX() == 0) && (b.getY() == 0):"boss is not knocked back";
        p.hitSwordHelper(0, 0);
        p.hitSwordHelper(0, 0);
        p.hitSwordHelper(0, 0);
        assert (b.getAlive() == true) && (b.getExists() == true):"boss is still alive";
        p.hitSwordHelper(0, 0);
        assert (b.getAlive() == false) && (b.getExists() == false):"boss is dead";

    }
}