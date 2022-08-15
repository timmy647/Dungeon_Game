package unsw.dungeon;

public class Treasure extends Entity {
    Dungeon dungeon;

    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
        dungeon.notifyGoalTreasure();
    }
    @Override
    public int moveto(Entity e){
        if (e instanceof Player){
            Player p = (Player)e;
            p.addTreasure();
            p.setPosition(getX(), getY());
            dungeon.removeEntity(this);
            return 0;
        }
        return 1;
    }
}