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
import unsw.dungeon.TestArrow;
import unsw.dungeon.TestRobot;
import unsw.dungeon.Treasure;

public class MovementTest {
    @Test
    public void moveEmptyWallFsExit(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon, 0, 0);
        Wall wall = new Wall(dungeon, 1, 0);
        FloorSwitch floorSwitch = new FloorSwitch(dungeon, 0, 1);
        player.moveDown();
        assert player.getX() == 0 && player.getY() == 1 : "Testing player movement downwards through floorswitch";
        player.moveRight();
        assert player.getX() == 1 && player.getY() == 1 : "Testing player movement to the right";
        player.moveLeft();
        assert player.getX() == 0 && player.getY() == 1 : "Testing player movement to the left";
        player.moveUp();
        assert player.getX() == 0 && player.getY() == 0 : "Testing player movement upwards";
        assert player.moveRight() == false:"testing false means no movement";
        assert player.getX() == 0 && player.getY() == 0 : "Testing player cannot move into walls";
        Exit exit = new Exit(dungeon, 0, 2);
        player.moveDown();
        player.moveDown();
        assert player.getX() == 0 && player.getY() == 2 : "Testing player movement into exit";
        TestRobot t = new TestRobot(dungeon, 0, 0);
        TestArrow ta = new TestArrow(dungeon, 0, 0);
        assert t.moveto(player) == 1:"testrobot returns 1";
        assert ta.moveto(player) == 1:"testArrow returns 1";
    }

    @Test
    public void moveBoundary(){
        Dungeon dungeon = new Dungeon(1, 1);
        Player player = new Player(dungeon, 0, 0);
        assert player.moveDown() == false: "testing boundaries down";
        assert player.moveLeft() == false: "testing boundaries up";
        assert player.moveRight() == false: "testing boundaries right";
        assert player.moveUp() == false: "testing boundaries left";
        assert player.getX() == 0 && player.getY() == 0 : "Testing player is in same position";
    }

    @Test
    public void moveItem(){
        Dungeon dungeon = new Dungeon(4, 4);
        Player player = new Player(dungeon, 0, 0);
        Sword sword1 = new Sword(dungeon, 0, 1);
        Sword sword2 = new Sword(dungeon, 0, 3);
        Treasure treasure1 = new Treasure(dungeon, 2, 1);
        Treasure treasure2 = new Treasure(dungeon, 2, 2);
        Treasure treasure3 = new Treasure(dungeon, 2, 3);
        //key is done later
        InvincibilityPotion potion1 = new InvincibilityPotion(dungeon, 3, 1);
        InvincibilityPotion potion2 = new InvincibilityPotion(dungeon, 3, 3);
        assert dungeon.getEntities().size() == 8:"8 entities";

        //collect sword
        assert player.moveDown() == true:"test collect sword";
        player.moveDown();
        assert dungeon.findEntity(0, 1) == null:"test sword1 not there";
        assert player.getSword() == 5:"test correct number of swords";
        assert player.moveDown() == false:"second sword is not taken";
        assert (dungeon.findEntity(0, 3) instanceof Sword) == true:"test sword2 is there";
        player.moveUp();

        //collect treasure
        player.moveRight();
        assert player.moveRight() == true:"test collect T1";
        assert player.moveDown() == true:"test collect T2";
        assert dungeon.findEntity(2, 1) == null:"test T1 not there";
        assert player.moveDown() == true:"test collect T3";
        assert player.getTreasure() == 3:"test correct number of treasure";

        //collect potion
        assert player.getDuration() == 0:"test duration false";
        assert player.moveRight() == true:"test collect potion2";
        player.moveUp();
        assert dungeon.findEntity(3, 3) == null:"test potion2 not there";
        assert player.getInvincibity() == false:"test invincible false1";
        player.setInvincibity(true);
        assert player.getInvincibity() == true:"test invincible false2";
        assert player.getDuration() == 10000:"test duration 10000";
        assert player.moveUp() == false:"test collect potion1 failed";
        player.setInvincibity(false);
        assert player.moveUp() == true:"test collect potion1 success";
        assert player.getDuration() == 20000:"test duration 20000";
        for (int i=0;i < 200;i++){
            player.tickDuration(25);
        }
        assert player.getInvincibity() == true:"test invincible false3";
        assert player.getDuration() == 15000:"test duration 15000";
        for (int i=0;i < 2000;i++){
            player.tickDuration(25);
        }
        assert player.getInvincibity() == false:"test invincible false4";
        assert player.getDuration() == 0:"test duration reset";
    }

    @Test
    public void movewand(){
        Dungeon dungeon = new Dungeon(4, 4);
        Player player = new Player(dungeon, 0, 0);
        Wand wand = new Wand(dungeon, 0, 1);
        Wand wand2 = new Wand(dungeon, 0, 2);
        assert wand.getExists() == true:"wand exists";
        assert player.moveDown() == true:"player moved";
        assert player.getWand() == 5:"player has correct number of wands";
        assert wand.getExists() == false:"wand removed";
        assert player.moveDown() == false:"player cannot move";
        assert player.getWand() == 5:"player has correct number of wands";
        Enemy enemy = new Enemy(dungeon, 1, 2);
        assert enemy.moveLeft() == false:"enemy van't move";
        
    }

    @Test
    public void moveDoor(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon, 1, 1);
        Door doorLeft = new Door(dungeon, 0, 1, 37);
        Door doorRight = new Door(dungeon, 2, 1, 88);
        Key keyLeft = new Key(dungeon, 0, 2, 37);
        Key keyRight = new Key(dungeon, 2, 2, 88);
        
        player.moveRight();
        assert player.getX() == 1 && player.getY() == 1 : "Testing player blocked by right door";
        player.moveLeft();
        assert player.getX() == 1 && player.getY() == 1 : "Testing player blocked by left door";
        player.moveDown();
        player.moveRight();
        assert player.findkey(88) == true: "testing 88 key is found";
        player.moveUp();
        assert player.getX() == 2 && player.getY() == 1 : "Testing player moved into right door";
        player.moveDown(); player.moveLeft(); player.moveLeft();
        assert player.findkey(37) == true: "testing 37 key is found";
        player.moveUp();
        assert player.getX() == 0 && player.getY() == 1 : "Testing player moved into left door";
        player.moveRight();
        player.moveRight();
        assert dungeon.findEntity(0, 2) == null && dungeon.findEntity(2, 2) == null: "testing keys removed";
        assert doorLeft.getOpen() == true && doorRight.getOpen() == true: "testing doors are open";
    }

    @Test
    public void moveBoulder(){
        Dungeon dungeon = new Dungeon(12, 3);
        Player player = new Player(dungeon, 0, 2);
        Boulder[] boulders = new Boulder[12];
        for(int i=0;i<12;i++){
            boulders[i] = new Boulder(dungeon, i, 1);
        }
        Wall wall = new Wall(dungeon, 0, 0);
        Boulder boulder = new Boulder(dungeon, 1, 0);
        Door door = new Door(dungeon, 2, 0, 1);
        Portal portal = new Portal(dungeon, 3, 0, 1);
        Enemy enemy = new Enemy(dungeon, 4, 0);
        Treasure treasure = new Treasure(dungeon, 5, 0);
        Key key = new Key(dungeon, 6, 0, 1);
        Sword sword = new Sword(dungeon, 7, 0);
        InvincibilityPotion potion = new InvincibilityPotion(dungeon, 8, 0);
        FloorSwitch floorSwitch = new FloorSwitch(dungeon, 9, 0);
        Door unlockedDoor = new Door(dungeon, 10, 0, 2);
        unlockedDoor.setOpen();
        assert player.moveUp() == false:"boulder can't go through wall";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through boulder";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through door";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through portal";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through enemy";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through treasure";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through key";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through sword";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through potion";
        player.moveRight();
        assert player.moveUp() == true:"boulder can go through floorswitch";
        player.moveDown();
        player.moveRight();
        assert player.moveUp() == true:"boulder can go through open door";
        player.moveDown();
        player.moveRight();
        assert player.moveUp() == true:"boulder can go through empty space";
        player.moveRight();
        assert player.moveUp() == false:"boulder can't go through boundary";
    }

    @Test
    public void moveBoulderDirections(){
        Dungeon dungeon = new Dungeon(5, 5);
        Player p1 = new Player(dungeon, 2, 2);
        Boulder b1 = new Boulder(dungeon, 2, 2);
        Boulder b2 = new Boulder(dungeon, 2, 1);
        Boulder b3 = new Boulder(dungeon, 1, 2);
        Boulder b4 = new Boulder(dungeon, 3, 2);
        Boulder b5 = new Boulder(dungeon, 2, 3);
        assert b1.moveDown() == false:"boulder cannot move";
        assert b1.moveUp() == false:"boulder cannot move";
        assert b1.moveLeft() == false:"boulder cannot move";
        assert b1.moveRight() == false:"boulder cannot move";
        dungeon.removeEntity(b1);
        assert p1.moveDown() == true:"boulder1 can move";
        p1.moveUp();
        assert p1.moveUp() == true:"boulder2 can move";
        p1.moveDown();
        assert p1.moveLeft() == true:"boulder3 can move";
        p1.moveRight();
        assert p1.moveRight() == true:"boulder4 can move";
    }

    @Test
    public void moveEnemy(){
        Dungeon dungeon = new Dungeon(12, 3);
        Enemy player = new Enemy(dungeon, 0, 2);
        Wall wall = new Wall(dungeon, 0, 1);
        Boulder boulder = new Boulder(dungeon, 1, 1);
        Door door = new Door(dungeon, 2, 1, 1);
        Portal portal = new Portal(dungeon, 3, 1, 1);
        Enemy enemy = new Enemy(dungeon, 4, 1);
        Treasure treasure = new Treasure(dungeon, 5, 1);
        Key key = new Key(dungeon, 6, 1, 1);
        Sword sword = new Sword(dungeon, 7, 1);
        InvincibilityPotion potion = new InvincibilityPotion(dungeon, 8, 1);
        FloorSwitch floorSwitch = new FloorSwitch(dungeon, 9, 1);
        Door unlockedDoor = new Door(dungeon, 10, 1, 2);
        Player dead = new Player(dungeon, 11, 1);
        unlockedDoor.setOpen();
        assert player.moveUp() == false:"enemy can't go through wall";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through boulder";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through door";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through portal";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through enemy";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through treasure";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through key";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through sword";
        player.moveRight();
        assert player.moveUp() == false:"enemy can't go through potion";
        player.moveRight();
        assert player.moveUp() == true:"enemy can go through floorswitch";
        player.moveDown();
        player.moveRight();
        assert player.moveUp() == true:"enemy can go through open door";
        player.moveDown();
        player.moveRight();
        assert player.moveUp() == true:"enemy can go through player";
        assert player.moveRight() == false:"enemy can't go through boundary";
        assert player.moveUp() == true:"enemy can go through empty space";
    }

    @Test
    public void moveArcher(){
        Dungeon dungeon = new Dungeon(12, 3);
        Archer player = new Archer(dungeon, 0, 2);
        Wall wall = new Wall(dungeon, 0, 1);
        Boulder boulder = new Boulder(dungeon, 1, 1);
        Door door = new Door(dungeon, 2, 1, 1);
        Portal portal = new Portal(dungeon, 3, 1, 1);
        Enemy enemy = new Enemy(dungeon, 4, 1);
        Treasure treasure = new Treasure(dungeon, 5, 1);
        Key key = new Key(dungeon, 6, 1, 1);
        Sword sword = new Sword(dungeon, 7, 1);
        InvincibilityPotion potion = new InvincibilityPotion(dungeon, 8, 1);
        FloorSwitch floorSwitch = new FloorSwitch(dungeon, 9, 1);
        Door unlockedDoor = new Door(dungeon, 10, 1, 2);
        Player dead = new Player(dungeon, 11, 1);
        unlockedDoor.setOpen();
        assert player.moveUp() == false:"Archer can't go through wall";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through boulder";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through door";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through portal";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through enemy";
        assert enemy.moveDown() == false:"enemy can't go through archer";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through treasure";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through key";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through sword";
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through potion";
        player.moveRight();
        assert player.moveUp() == true:"Archer can go through floorswitch";
        player.moveDown();
        player.moveRight();
        assert player.moveUp() == true:"Archer can go through open door";
        player.moveDown();
        player.moveRight();
        assert player.moveUp() == false:"Archer can't go through player";
        assert dead.moveDown() == false:"player can't go through archer";
        assert player.moveRight() == false:"archer can't go through boundary";
    }

    @Test
    public void moveArrow(){
        Dungeon dungeon = new Dungeon(12, 3);
        Arrow player = new Arrow(dungeon, 0, 2,"up");
        Arrow arrow = new Arrow(dungeon, 11, 0,"up");
        Wall wall = new Wall(dungeon, 0, 1);
        Boulder boulder = new Boulder(dungeon, 1, 1);
        Door door = new Door(dungeon, 2, 1, 1);
        Portal portal = new Portal(dungeon, 3, 1, 1);
        Enemy enemy = new Enemy(dungeon, 4, 1);
        Treasure treasure = new Treasure(dungeon, 5, 1);
        Key key = new Key(dungeon, 6, 1, 1);
        Sword sword = new Sword(dungeon, 7, 1);
        InvincibilityPotion potion = new InvincibilityPotion(dungeon, 8, 1);
        FloorSwitch floorSwitch = new FloorSwitch(dungeon, 9, 1);
        Door unlockedDoor = new Door(dungeon, 10, 1, 2);
        Player dead = new Player(dungeon, 11, 1);
        unlockedDoor.setOpen();
        assert player.moveUp() == false:"Arrow can't go through wall";
        player.moveRight();
        assert player.moveUp() == false:"Arrow can't go through boulder";
        player.moveRight();
        assert player.moveUp() == false:"Arrow can't go through door";
        player.moveRight();
        assert player.moveUp() == false:"Arrow can't go through portal";
        player.moveRight();
        assert enemy.moveDown() == false:"enemy can't go through arrow";
        assert player.moveUp() == true:"Arrow can go through enemy";
        assert player.moveDown() == true:"Arrow can go through empty space";
        player.moveRight();
        assert player.moveUp() == false:"Arrow can't go through treasure";
        player.moveRight();
        assert player.moveUp() == false:"Arrow can't go through key";
        player.moveRight();
        assert player.moveUp() == false:"Arrow can't go through sword";
        player.moveRight();
        assert player.moveUp() == false:"Arrow can't go through potion";
        player.moveRight();
        assert player.moveUp() == true:"Arrow can go through floorswitch";
        player.moveDown();
        player.moveRight();
        assert player.moveUp() == true:"Arrow can go through open door";
        player.moveDown();
        player.moveRight();
        assert dead.moveDown() == false:"player can't go through arrow";
        assert player.moveUp() == true:"Arrow can go through player";
        assert player.moveRight() == false:"Arrow can't go through boundary";
        assert player.moveUp() == false:"Arrow can't go through arrow";
        Archer a = new Archer(dungeon, 11, 2);
        assert player.moveDown() == true:"arrow can go through archer";
    }

    @Test
    public void multipleEntities(){
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 0, 0);
        FloorSwitch floorSwitch = new FloorSwitch(dungeon, 1, 0);
        Door unlockedDoor = new Door(dungeon, 0, 1, 2);
        unlockedDoor.setOpen();
        Boulder boulder = new Boulder(dungeon, 1, 0);
        Enemy enemy = new Enemy(dungeon, 0, 1);
        assert (dungeon.findEntity(1, 0) instanceof Boulder) == true: "finding boulder in switch";
        assert (dungeon.findEntity(0, 1) instanceof Enemy) == true: "finding enemy in door";
        player.addSword();
        player.hitSword();
        assert player.getSword() == 4:"player's swordvalue drops by 1";
        assert (dungeon.findEntity(0, 1) instanceof Enemy) == false: "not finding enemy in door";
        assert (dungeon.findEntity(0, 1) instanceof Door) == true: "finding door";
        player.moveRight();
        assert (dungeon.findEntity(1, 0) instanceof Player) == true: "finding Player on switch";
        assert (dungeon.findEntity(2, 0) instanceof Boulder) == true: "finding boulder beside switch";
        player.moveDown();
        assert (dungeon.findEntity(1, 0) instanceof FloorSwitch) == true: "finding switch";
    }
}