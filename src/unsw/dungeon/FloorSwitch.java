package unsw.dungeon;

public class FloorSwitch extends Entity{
    Dungeon dungeon;
    public FloorSwitch(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
        dungeon.notifyGoalBoulder();
    }

    @Override
    public int moveto(Entity e){
        e.setPosition(getX(), getY());
        return 0;
    }
}