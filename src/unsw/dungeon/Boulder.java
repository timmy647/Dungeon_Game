package unsw.dungeon;

public class Boulder extends DynamicEntity {
    Dungeon dungeon;
    public Boulder(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.dungeon = dungeon;
        dungeon.addEntity(this);
        dungeon.notifyGoalBoulder();
    }

    public Entity findEntity(int x,int y){
        return dungeon.findEntity(x, y);
    }

    @Override
    public int moveto(Entity e){
        if (e instanceof Player){
            Player p = (Player)e;
            int x = this.getX();
            int y = this.getY();
            int xOff = p.getX() - this.getX();
            int yOff = p.getY() - this.getY();
            boolean result = false;
            if(     xOff ==  0 && yOff ==  1){
                result = moveUp();
            }
            else if(xOff ==  0 && yOff == -1){
                result = moveDown();
            }
            else if(xOff ==  1 && yOff ==  0){
                result = moveLeft();
            }
            else if(xOff == -1 && yOff ==  0){
                result = moveRight();
            }
            if (result == true){
                p.setPosition(x, y);
                return 0;
            }
        }
        return 1;
    }
}
