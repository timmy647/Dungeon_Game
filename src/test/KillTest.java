package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.Dungeon;
import unsw.dungeon.InvincibilityPotion;
import unsw.dungeon.Sword;
import unsw.dungeon.Player;
import unsw.dungeon.Enemy;
import unsw.dungeon.Archer;
import unsw.dungeon.Arrow;

public class KillTest {
    @Test
    public void swordKill(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon, 1, 1);
        Enemy e1 = new Enemy(dungeon, 0, 1);
        Enemy e2 = new Enemy(dungeon, 2, 2);
        assert player.getSword() == 0:"player's swordvalue is 0";
        player.hitSword();
        assert player.getSword() == 0:"player's swordvalue is still 0";
        assert (dungeon.findEntity(0, 1) instanceof Enemy) == true:"enemy1 adjacent to player is alive";
        assert (dungeon.findEntity(2, 2) instanceof Enemy) == true:"enemy2 not adjacent to player is alive";
        player.addSword();
        player.hitSword();
        assert dungeon.findEntity(0, 1) == null:"enemy1 adjacent to player is dead";
        assert (dungeon.findEntity(2, 2) instanceof Enemy) == true:"enemy not adjacent to player is alive";
        assert player.getSword() == 4:"player's swordvalue drops by 1";
        player.hitSword();
        assert player.getSword() == 4:"player's swordvalue drops by 1";
        Enemy e3 = new Enemy(dungeon, 0, 1);
        Archer a1 = new Archer(dungeon, 1, 0);
        assert a1.getAlive() == true:"archer adjacent to player is alive";
        player.hitSword();
        assert dungeon.findEntity(0, 1) == null:"enemy3 adjacent to player is dead";
        assert e3.getAlive() == false:"enemy3 adjacent to player is dead";
        assert dungeon.findEntity(1, 0) == null:"archer adjacent to player is dead";
        assert a1.getAlive() == false:"archer adjacent to player is dead";
        assert player.getSword() == 2:"player's swordvalue drops by 1";
        assert (dungeon.findEntity(2, 2) instanceof Enemy) == true:"enemy not adjacent to player is alive";
    }

    @Test
    public void potionKill(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon, 1, 1);
        Enemy e1 = new Enemy(dungeon, 1, 0);
        Archer e2 = new Archer(dungeon, 2, 2);
        assert player.moveUp() == false:"player can't go through enemy";
        player.setInvincibity(true);
        assert e1.moveDown() == false:"enemy not go theough player";
        assert player.moveUp() == true:"player can go through enemy";
        assert (dungeon.findEntity(1, 0) instanceof Player) == true:"player is in enemies location";
        assert e1.getAlive() == false:"enemy is dead";
        assert e2.getAlive() == true:"2nd enemy is alive";
        player.moveDown();
        assert dungeon.findEntity(1, 0) == null:"enemy adjacent to player is dead";
        player.moveDown();
        player.moveRight();
        assert e2.getAlive() == false:"archer is dead";
    }
    @Test
    public void enemyKill(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon, 1, 1);
        Enemy e1 = new Enemy(dungeon, 1, 0);
        player.setInvincibity(true);
        assert e1.moveDown() == false:"enmy can't go through player";
        player.setInvincibity(false);
        assert e1.moveDown() == true:"enmy can go through player";
        assert (dungeon.findEntity(1, 1) instanceof Enemy) == true:"enmy is in player's location";
        e1.moveDown();
        assert dungeon.findEntity(1, 1) == null:"player adjacent to enemy is dead";
    }

    @Test
    public void WeaponsTest(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player p = new Player(dungeon, 1, 1);
        assert p.getWeapon() == "sword":"wt-1";
        p.changeWeapon();
        assert p.getWeapon() == "wand":"wt-2";
        p.changeWeapon();
        assert p.getWeapon() == "sword":"wt-3";
        
    }
}