package unsw.dungeon;

public class TestArrow extends DynamicEntity {
    private Dungeon dungeon;

    public TestArrow(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.dungeon = dungeon;
    }

    public int moveUp(int result) {
        if (getY() > 0) {
            Entity e = findEntity(getX(), getY() - 1);
            if (e == null){
                setPosition(getX(),getY() - 1);
                return 0;
            }else{
                return (e.moveto(this));
            }  
        }
        return 1;
    }

    public int moveDown(int result) {
        if (getY() < dungeon.getHeight() - 1) {
            Entity e = findEntity(getX(), getY() + 1);
            if (e == null){
                setPosition(getX(),getY() + 1);
                return 0;
            }else{
                return (e.moveto(this));
            }  
        }
        return 1;
    }

    public int moveLeft(int result) {
        if (getX() > 0) {
            Entity e = findEntity(getX() - 1, getY());
            if (e == null){
                setPosition(getX() - 1,getY());
                return 0;
            }else{
                return (e.moveto(this));
            }  
        }
        return 1;
    }

    public int moveRight(int result) {
        if (getX() < dungeon.getWidth() - 1) {
            Entity e = findEntity(getX() + 1, getY());
            if (e == null){
                setPosition(getX() + 1,getY());
                return 0;
            }else{
                return (e.moveto(this));
            }  
        }
        return 1;
    }

    //TODO implement player finding
    public boolean findPlayer(String direction){
        if (direction == "up"){
            int result = moveUp(0);
            while (result != 1){
                if(result == 2){
                    return true;
                }
                result = moveUp(0);
            }
        }
        else if (direction == "down"){
            int result = moveDown(0);
            while (result != 1){
                if(result == 2){
                    return true;
                }
                result = moveDown(0);
            }
        }
        else if (direction == "left"){
            int result = moveLeft(0);
            while (result != 1){
                if(result == 2){
                    return true;
                }
                result = moveLeft(0);
            }
        }
        else if (direction == "right"){
            int result = moveRight(0);
            while (result != 1){
                if(result == 2){
                    return true;
                }
                result = moveRight(0);
            }
        }
        return false;
    }


    @Override
    public int moveto(Entity e) {
        return 1;
    }
}