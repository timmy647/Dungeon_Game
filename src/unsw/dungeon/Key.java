package unsw.dungeon;


public class Key extends Entity{
    private Dungeon dungeon;
    private int id;
    
    public Key(Dungeon dungeon, int x, int y, int id) {// NEW PARAMETER
        super(x, y);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int moveto(Entity e){
        if (e instanceof Player){
            Player p = (Player)e;
            p.addKey(this); //CHECK LOGIC adding an object to one list and removing it from another may cause problems
            p.setPosition(getX(), getY());
            dungeon.removeEntity(this);
            return 0;
        }
        return 1;
    }
}