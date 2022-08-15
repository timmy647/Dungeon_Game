package unsw.dungeon;

public class Portal extends Entity {
    private Dungeon dungeon;
    private int id;

    public Portal(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.id = id;
        dungeon.addEntity(this);
    }

    public int getid() {
        return id;
    }

    @Override
    public int moveto(Entity e) {
        System.out.println(e.getClass());
        if (e instanceof Player) {
            Portal p = dungeon.findPortal(id, getX(), getY());
            e.setPosition(p.getX(), p.getY());
            return 0;
        }
        return 1;
    }

}