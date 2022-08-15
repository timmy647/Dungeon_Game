package unsw.dungeon;
//import java.util.concurrent.TimeUnit;

public class InvincibilityPotion extends Entity{
    private Dungeon dungeon;
    private int effectTime;    //make time object?

    public InvincibilityPotion(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.effectTime = 10000;
        dungeon.addEntity(this);
    }

    @Override
    public int moveto(Entity e){
        if ((e instanceof Player) && (((Player)e).getInvincibity() == false)){
            Player p = (Player)e;
            p.setPosition(getX(), getY());
            dungeon.removeEntity(this);
            p.setDuration(p.getDuration() + effectTime);
            return 0;
        }
        return 1;
    }
}