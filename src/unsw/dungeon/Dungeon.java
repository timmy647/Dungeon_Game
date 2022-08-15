/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;




/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;

    private ArrayList<ArrayList<Integer>> adj; 
    private int path[];
    private boolean found;
    private int num;
    
    public Goal goal;
    public GoalExit goal1;
    public GoalEnemy goal2;
    public GoalBoulder goal3;
    public GoalTreasure goal4;
    public GameOver gameover;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.num = (width) * (height);
        this.goal = null;
        this.goal1 = new GoalExit();
        this.goal2 = new GoalEnemy();
        this.goal3 = new GoalBoulder();
        this.goal4 = new GoalTreasure();
        this.gameover = new GameOver();
    }
    public GameOver getGameOver() {
        return gameover;
    }
    public void setGoal(Goal goal) {
        this.goal = goal;
    }
    public Goal getGoal() {
        return goal;
    }
    public GoalExit getGoalExit() {
        return goal1;
    }
    public GoalEnemy getGoalEnemy() {
        return goal2;
    }
    public GoalBoulder getGoalBoulder() {
        return goal3;
    }
    public GoalTreasure getGoalTreasure() {
        return goal4;
    }
    public void notifyGameOver() {
        gameover.update(player.getAlive());
    }
    public void notifyGoalExit() {
        goal1.update(getPlayer(), findExit());
    }
    public void notifyGoalEnemy() {
        goal2.update(findEnemy(), findArcher(), findBoss());
    }
    public void notifyGoalBoulder() {
        goal3.update(findSwitch(), findBoulder());
    }
    public void notifyGoalTreasure() {
        goal4.update(findTreasure());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }


    public void removeEntity(Entity entity) {
        entities.remove(entity);
        entity.setExists(false);
        goal4.update(findTreasure());
    }

    public Entity findEntity(int x,int y){
        //TODO handle multiple entities on same tile
        for (Entity e:entities){
            if (e.getX() == x && e.getY() == y){
                if ((e instanceof FloorSwitch) || (e instanceof Door)){
                    for (Entity s:entities){
                        if (s.getX() == x && s.getY() == y && 
                            !((s instanceof FloorSwitch) || (s instanceof Door))){
                            return s;
                        }
                    }
                }
                return e;
            }
        }
        return null;
    }

    public Portal findPortal(int id, int x, int y) {
        for (Entity p : entities) {
            if  (p instanceof Portal && ((Portal)p).getid() == id   && 
                !(((Portal)p).getX()  == x  && ((Portal)p).getY()  == y)) {
                return ((Portal)p);
            }
        }
        return null;
    }

    public Exit findExit() {
        for (Entity p : entities) {
            if  (p instanceof Exit) {
                return ((Exit) p);
            }
        }
        return null;
    }

    public List<Enemy> findEnemy() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        for (Entity p : entities) {
            if  (p instanceof Enemy) {
                enemies.add((Enemy) p);
            }
        }
        return enemies;
    }

    public List<Archer> findArcher() {
        List<Archer> archers = new ArrayList<Archer>();
        for (Entity p : entities) {
            if  (p instanceof Archer) {
                archers.add((Archer) p);
            }
        }
        return archers;
    }

    public List<Boss> findBoss() {
        List<Boss> boss = new ArrayList<Boss>();
        for (Entity p : entities) {
            if  (p instanceof Boss) {
                boss.add((Boss) p);
            }
        }
        return boss;
    }

    public List<Arrow> findArrow() {
        List<Arrow> arrows = new ArrayList<Arrow>();
        for (Entity p : entities) {
            if  (p instanceof Arrow) {
                arrows.add((Arrow) p);
            }
        }
        return arrows;
    }

    public void moveEnemies(){
        List<Enemy> enemies = findEnemy();
        for (Enemy e:enemies){
            if(player.getInvincibity() == true){
                e.runaway();
            }
            else{
                e.approach();
            }
        }
    }
    
    public void moveArchers(DungeonController dungeonController){
        List<Archer> archers = findArcher();
        for (Archer a:archers){
            if(player.getInvincibity() == true || a.playerVicinity() == true){
                a.runaway();
                a.fire(dungeonController);
            }
            else{
                a.approach();
                a.fire(dungeonController);
            }
        }
    }

    public void moveBosses(DungeonController dungeonController){
        List<Boss> bosses = findBoss();
        for (Boss b:bosses){
            b.moveAndFire(dungeonController);
        }
    }

    public void moveArrows(){
        List<Arrow> arrows = findArrow();
        for (Arrow a:arrows){
            a.move();
        }
    }

    public List<FloorSwitch> findSwitch() {
        List<FloorSwitch> switches = new ArrayList<FloorSwitch>();
        for (Entity p : entities) {
            if  (p instanceof FloorSwitch) {
                switches.add((FloorSwitch) p);
            }
        }
        return switches;
    }

    public List<Boulder> findBoulder() {
        List<Boulder> boulders = new ArrayList<Boulder>();
        for (Entity p : entities) {
            if  (p instanceof Boulder) {
                boulders.add((Boulder) p);
            }
        }
        return boulders;
    }

    public List<Treasure> findTreasure() {
        List<Treasure> treasures = new ArrayList<Treasure>();
        for (Entity p : entities) {
            if  (p instanceof Treasure) {
                treasures.add((Treasure) p);
            }
        }
        return treasures;
    }
}
