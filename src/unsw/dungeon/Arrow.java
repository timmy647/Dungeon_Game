package unsw.dungeon;

public class Arrow extends DynamicEntity{

    private Dungeon dungeon;

    private String direction;
    
    public Arrow(Dungeon dungeon, int x, int y, String direction) {
        super(dungeon,x, y);
        this.dungeon = dungeon;
        this.direction = direction;
        dungeon.addEntity(this);
    }

    public void move(){
        if (direction == "up"){
            if (moveUp() == false){
                dungeon.removeEntity(this);
            }
        }
        else if (direction == "down"){
            if (moveDown() == false){
                dungeon.removeEntity(this);
            }
        }
        else if (direction == "left"){
            if (moveLeft() == false){
                dungeon.removeEntity(this);
            }
        }
        else if (direction == "right"){
            if (moveRight() == false){
                dungeon.removeEntity(this);
            }
        }
    }

    @Override
    public int moveto(Entity e) {
        return 1;
    }
}