package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;

public class PortalTest {
    
    @Test
    public void teleportEntityTest() {
        Dungeon d = new Dungeon(5, 5);
        Portal p1 = new Portal(d, 1, 1, 1);
        Portal p2 = new Portal(d, 4, 4, 1);
        Player player = new Player(d, 0, 1);
        player.moveRight();
        assert d.findPortal(100, 4, 4) == null:"testing findportal";
        assert player.getX() == 4 && player.getY() == 4 : "Testing Portal";
    }

    @Test
    public void teleportPlayer(){
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 0, 0);
        Portal p1 = new Portal(dungeon, 1, 0, 1);
        Portal p2 = new Portal(dungeon, 4, 4, 1);
        assert player.moveRight() == true:"player can move into portal";
        assert player.getX() == 4 && player.getY() == 4 :"player is in portal";
    }

}