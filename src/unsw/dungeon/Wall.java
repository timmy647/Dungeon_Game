package unsw.dungeon;

public class Wall extends Entity {
    Dungeon dungeon;
    public Wall(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
    }

    @Override
    public int moveto(Entity e){
        return 1;
    }

}
