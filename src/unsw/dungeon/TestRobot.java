package unsw.dungeon;

public class TestRobot extends DynamicEntity {
    private Dungeon dungeon;

    public TestRobot(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.dungeon = dungeon;
    }

    @Override
    public int moveto(Entity e) {
        return 1;
    }
}