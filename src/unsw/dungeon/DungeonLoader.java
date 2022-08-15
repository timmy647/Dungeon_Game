package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        //load bosses first so they appear first
        for (int i = 0; i < jsonEntities.length(); i++) {
            if(jsonEntities.getJSONObject(i).getString("type") == "boss"){
                loadEntity(dungeon, jsonEntities.getJSONObject(i));
            }
        }

        for (int i = 0; i < jsonEntities.length(); i++) {
            if(jsonEntities.getJSONObject(i).getString("type") != "boss"){
                loadEntity(dungeon, jsonEntities.getJSONObject(i));
            }
        }

        /*ArrayList<JSONObject> bosses = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonEntities.length(); i++) {
            if(jsonEntities.getJSONObject(i).getString("type") == "boss"){
                bosses.add(jsonEntities.getJSONObject(i));
            }
            else{
                loadEntity(dungeon, jsonEntities.getJSONObject(i));
            }
        }
        //load bosses after so they appear on top
        for (JSONObject b:bosses){
            loadEntity(dungeon, b);
        }*/
        Goal goal = loadGoals(dungeon, json.getJSONObject("goal-condition"));
        dungeon.setGoal(goal);
        return dungeon;
    }

    public void loadArrow(Dungeon dungeon, int x, int y, String direction) {
        Arrow arrow = new Arrow(dungeon, x, y, direction);
        onLoad(arrow);
    }
    public Goal loadGoals(Dungeon dungeon, JSONObject json) {
        String goal = json.getString("goal");
        switch (goal) {
            case "exit":
                return dungeon.getGoalExit();
            case "enemies":
                return dungeon.getGoalEnemy();
            case "boulders":
                return dungeon.getGoalBoulder();
            case "treasure":
                return dungeon.getGoalTreasure();
            case "AND":
                GoalAnd AndGoal = new GoalAnd();
                JSONArray andgoals = json.getJSONArray("subgoals");
                for (int i = 0; i < andgoals.length(); i++) {
                    AndGoal.addGoal(loadGoals(dungeon, andgoals.getJSONObject(i)));
                }
                return AndGoal;
            case "OR":
                GoalOr OrGoal = new GoalOr();
                JSONArray orgoals = json.getJSONArray("subgoals");
                for (int i = 0; i < orgoals.length(); i++) {
                    OrGoal.addGoal(loadGoals(dungeon, orgoals.getJSONObject(i)));
                }
            return OrGoal;
        }
        return null;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(dungeon, x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "door":
            id = json.getInt("id");
            Door door = new Door(dungeon, x, y, id);
            onLoad(door);
            entity = door;
            break;
        case "key":
            id = json.getInt("id");
            Key key = new Key(dungeon, x, y, id);
            onLoad(key);
            entity = key;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            FloorSwitch floorswitch = new FloorSwitch(dungeon, x, y);
            onLoad(floorswitch);
            entity = floorswitch;
            break;
        case "portal":
            id = json.getInt("id");
            Portal portal = new Portal(dungeon, x, y, id);
            onLoad(portal);
            entity = portal;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "archer":
            Archer archer = new Archer(dungeon, x, y);
            onLoad(archer);
            entity = archer;
            break;
        case "boss":
            Boss boss = new Boss(dungeon, x, y);
            onLoad(boss);
            entity = boss;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "wand":
            Wand wand = new Wand(dungeon, x, y);
            onLoad(wand);
            entity = wand;
            break;
        case "invincibility":
            InvincibilityPotion potion = new InvincibilityPotion(dungeon, x, y);
            onLoad(potion);
            entity = potion;
            break;
        }
        
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    // TODO Create additional abstract methods for the other entities
    public abstract void onLoad(Exit exit);
    public abstract void onLoad(Treasure treasure);
    public abstract void onLoad(Door door);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(FloorSwitch floorswitch);
    public abstract void onLoad(Portal portal);
    public abstract void onLoad(Enemy enmey);
    public abstract void onLoad(Archer archer);
    public abstract void onLoad(Arrow arrow);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Wand wand);
    public abstract void onLoad(InvincibilityPotion potion);
    public abstract void onLoad(Boss boss);
}
