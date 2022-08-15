package unsw.dungeon;

public class Exit extends Entity {
    Dungeon dungeon;
    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
    }

    public int moveto(Entity e){
        e.setPosition(getX(), getY());
        //TODO design the exit with the goals
        return 0;
    }
}