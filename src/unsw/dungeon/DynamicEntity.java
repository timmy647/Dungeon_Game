package unsw.dungeon;

public abstract class DynamicEntity extends Entity {
    private Dungeon dungeon;

    public DynamicEntity(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public abstract int moveto(Entity e);

    public Entity findEntity(int x,int y){
        return dungeon.findEntity(x, y);
    }
    
    public boolean moveUp() {
        if (getY() > 0) {
            Entity e = findEntity(getX(), getY() - 1);
            if (e == null){
                setPosition(getX(),getY() - 1);
                dungeon.notifyGoalExit();
                dungeon.notifyGoalBoulder();
                return true;
            }else{
                if (e.moveto(this) == 0){
                    dungeon.notifyGoalExit();
                    dungeon.notifyGoalBoulder();
                    return true;
                }
            }  
        }
        return false;
    }

    public boolean moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            Entity e = findEntity(getX(), getY() + 1);
            if (e == null){
                setPosition(getX(),getY() + 1);
                dungeon.notifyGoalExit();
                dungeon.notifyGoalBoulder();
                return true;
            }else{
                if (e.moveto(this) == 0){
                    dungeon.notifyGoalExit();
                    dungeon.notifyGoalBoulder();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveLeft() {
        if (getX() > 0) {
            Entity e = findEntity(getX() - 1, getY());
            if (e == null){
                setPosition(getX() - 1,getY());
                dungeon.notifyGoalExit();
                dungeon.notifyGoalBoulder();
                return true;
            }else{
                if (e.moveto(this) == 0){
                    dungeon.notifyGoalExit();
                    dungeon.notifyGoalBoulder();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            Entity e = findEntity(getX() + 1, getY());
            if (e == null){
                setPosition(getX() + 1,getY());
                dungeon.notifyGoalExit();
                dungeon.notifyGoalBoulder();
                return true;
            }else{
                if (e.moveto(this) == 0){
                    dungeon.notifyGoalExit();
                    dungeon.notifyGoalBoulder();
                    return true;
                }
            }
        }
        return false;
    }
}