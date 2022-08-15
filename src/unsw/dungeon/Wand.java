package unsw.dungeon;

public class Wand extends Entity{
    private Dungeon dungeon;
    
    public Wand(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
    }
    
    @Override
    public int moveto(Entity e){
        if ((e instanceof Player) && (((Player)e).getWand() == 0)){
            Player p = (Player)e;
            p.setPosition(getX(), getY());
            p.addWand();
            dungeon.removeEntity(this);
            return 0;
        }
        return 1;
    }
}