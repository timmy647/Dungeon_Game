package unsw.dungeon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends DynamicEntity {

    private Dungeon dungeon;

    public boolean alive;

    private IntegerProperty treasure;
    private IntegerProperty sword;
    private IntegerProperty wand;
    private StringProperty weapon; 
    private List<Integer> keys;
    private BooleanProperty invincible;
    private IntegerProperty duration;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.dungeon = dungeon;
        this.treasure = new SimpleIntegerProperty(0);
        this.sword = new SimpleIntegerProperty(0);
        this.wand = new SimpleIntegerProperty(0);
        this.weapon = new SimpleStringProperty("sword");
        this.keys = new ArrayList<Integer>();
        this.invincible = new SimpleBooleanProperty(false);
        this.alive = true;
        this.duration = new SimpleIntegerProperty(0);
        dungeon.addEntity(this);
        dungeon.setPlayer(this);
    }

    public void gotKilled() {
        alive = false;
        dungeon.notifyGameOver();
        dungeon.removeEntity(this);
        //dungeon.setPlayer(null);
    }


    public Entity findEntity(int x,int y){
        return dungeon.findEntity(x, y);
    }

    public boolean getAlive() {
        return alive;
    }

    @Override
    public int moveto(Entity e){
        //TODO let the game know that the game is over
        //kills player
        if(getInvincibity() == false && (e instanceof Enemy || e instanceof Arrow)){
            gotKilled();
            e.setPosition(getX(), getY());
            return 0;
        }
        if (e instanceof Boss){
            gotKilled();
            e.setPosition(getX(), getY());
            return 0;
        }
        if (e instanceof TestRobot){
            e.setPosition(getX(), getY());
            return 0;
        }
        if (e instanceof TestArrow){
            return 2;
        }
        return 1;
    }

    public IntegerProperty treasure(){
        return treasure;
    }

    public int getTreasure() {
		return treasure().get();
	}

	public void setTreasure(int treasure) {
		this.treasure().set(treasure);
    }

    public void addTreasure(){
        setTreasure(getTreasure() + 1);
    }

    public StringProperty weapon(){
        return weapon;
    }

    public String getWeapon(){
        return weapon().get();
    }

    public void setWeapon(String weapon){
        this.weapon().set(weapon);
    }

    public void changeWeapon(){
        if (getWeapon() == "sword")
            setWeapon("wand");
        else if(getWeapon() == "wand")
            setWeapon("sword");
    }

    public IntegerProperty wand(){
        return wand;
    }

    public int getWand(){
        return wand().get();
    }

    public void setWand(int wand){
        this.wand().set(wand);
    }

    public void addWand(){
        setWand(5);
    }

    public void fireWandHelper(DungeonController dungeonController,int x, int y,String direction){
        if (getWand() > 0){
            dungeonController.DynamicAddArrow(dungeon, x, y, direction);
            setWand(getWand() - 1);
        }
    }

    public IntegerProperty sword(){
        return sword;
    }

	public int getSword() {
		return sword().get();
	}

	public void setSword(int sword) {
		this.sword().set(sword);
    }
    
    public void addSword(){
        setSword(5);
    }

    public void hitSword(){ // UNUSED FOR TESTING ONLY
        hitSwordHelper(getX() + 1, getY());
        hitSwordHelper(getX() - 1, getY());
        hitSwordHelper(getX(), getY() + 1);
        hitSwordHelper(getX(), getY() - 1);
    }

    public void hitSwordHelper(int x, int y){
        if (findEntity(x, y) instanceof Foe && getSword() > 0){
            ((Foe)findEntity(x, y)).gotHurt();
            setSword(getSword() - 1);
        }
    }

    public void attack(DungeonController dungeonController,String direction){
        if (direction == "up"){
            if (getWeapon() == "sword"){
                hitSwordHelper(getX(), getY() - 1);
            }
            else if(getWeapon() == "wand"){
                fireWandHelper(dungeonController, getX(), getY() - 1, direction);
            }
        }
        else if (direction == "down"){
            if (getWeapon() == "sword"){
                hitSwordHelper(getX(), getY() + 1);
            }
            else if(getWeapon() == "wand"){
                fireWandHelper(dungeonController, getX(), getY() + 1, direction);
            }
        }
        else if (direction == "left"){
            if (getWeapon() == "sword"){
                hitSwordHelper(getX() - 1, getY());
            }
            else if(getWeapon() == "wand"){
                fireWandHelper(dungeonController, getX() - 1, getY(), direction);
            }
        }
        else if (direction == "right"){
            if (getWeapon() == "sword"){
                hitSwordHelper(getX() + 1, getY());
            }
            else if(getWeapon() == "wand"){
                fireWandHelper(dungeonController, getX() + 1, getY(), direction);
            }
        }
    }

	public List<Integer> getKeys() {
		return keys;
	}

    public void addKey(Key key){
        getKeys().add(key.getId());
    }

    public boolean findkey(int id){
        for (int k:keys){
            if (k == id){
                return true;
            }
        }
        return false;
    }
    
    public BooleanProperty invincible(){
        return invincible;
    }

    public boolean getInvincibity() {
		return invincible().get();
	}

	public void setInvincibity(boolean invincible) {
		this.invincible().set(invincible);
    }

    public IntegerProperty duration(){
        return duration;
    }

    public int getDuration() {
        return duration().get();
    }

    public void setDuration(int duration) {
        this.duration().set(duration);
    }

    public void tickDuration(int time){
        if(getDuration() > 0){
            setDuration(getDuration() - time);
            setInvincibity(true);
        }
        else if(getDuration() <= 0){
            setDuration(0);
            setInvincibity(false);
        }
    }
    

}
