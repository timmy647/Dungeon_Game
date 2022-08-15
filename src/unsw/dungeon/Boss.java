package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Boss extends DynamicEntity implements Foe{
    private Dungeon dungeon;
    public boolean alive;
    private IntegerProperty health;

    private ArrayList<ArrayList<Integer>> adj; 
    private int path[];
    private boolean found;
    private int num;

    public GoalEnemy goal2;

    public Boss(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.alive = true;
        this.dungeon = dungeon;
        this.num = (dungeon.getWidth()) * (dungeon.getHeight());
        this.health =new SimpleIntegerProperty(10);
        dungeon.addEntity(this);
        dungeon.notifyGoalEnemy();
    }

    public IntegerProperty health(){
        return health;
    }

    public int getHealth() {
        return health().get();
    }

    public void setHealth(int health) {
        this.health().set(health);
    }
    
    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void knockBack(){
        Player p = dungeon.getPlayer();
        int xOff = p.getX() - this.getX();
        int yOff = p.getY() - this.getY();
        if(     xOff ==  0 && yOff ==  1){
            moveUp();
        }
        else if(xOff ==  0 && yOff == -1){
            moveDown();
        }
        else if(xOff ==  1 && yOff ==  0){
            moveLeft();
        }
        else if(xOff == -1 && yOff ==  0){
            moveRight();
        }
    }

    public void gotHurt(){
        setHealth(getHealth() - 1);
        knockBack();
        if(getHealth() <= 0){
            gotKilled();
        }
    }

    public void gotKilled() {
        setAlive(false);
        dungeon.removeEntity(this);
        dungeon.notifyGoalEnemy();
    }


    @Override
    public int moveto(Entity e) {
        if (e instanceof Arrow) {
            gotHurt();
            e.setPosition(getX(), getY());
            return 1;
        }
        else if (e instanceof Boss){
            return 10;
        }
        return 1;
    }

    public void moveAndFire(DungeonController dungeonController){
        approach();
        dungeonController.DynamicAddArrow(dungeon, getX(), getY(), "up");
        dungeonController.DynamicAddArrow(dungeon, getX(), getY(), "down");
        dungeonController.DynamicAddArrow(dungeon, getX(), getY(), "left");
        dungeonController.DynamicAddArrow(dungeon, getX(), getY(), "right");
    }




    //MOVE//////////////////////////////////////////////////////////////

    @Override
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
                else if (e.moveto(this) == 1){
                    setPosition(getX(),getY() - 1);
                    return true;
                }
                else if (e.moveto(this) == 10){
                    return false;
                }
            }  
        }
        return false;
    }

    @Override
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
                else if (e.moveto(this) == 1){
                    setPosition(getX(),getY() + 1);
                    return true;
                }
                else if (e.moveto(this) == 10){
                    return false;
                }
            }
        }
        return false;
    }

    @Override
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
                else if (e.moveto(this) == 1){
                    setPosition(getX() - 1,getY());
                    return true;
                }
                else if (e.moveto(this) == 10){
                    return false;
                }
            }
        }
        return false;
    }

    @Override
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
                else if (e.moveto(this) == 1){
                    setPosition(getX() + 1,getY());
                    return true;
                }
                else if (e.moveto(this) == 10){
                    return false;
                }
            }
        }
        return false;
    }


    //BFS///////////////////////////////////////////////////////////////
    public void approach() {
        int position = getX()+dungeon.getWidth()*getY();
        LinkedList<Integer> path1 = BFS(position);
        if (path1.isEmpty()==true) return;
        int next = path1.get(path1.size()-2);
        if (position + 1 == next) {
            this.moveRight();
        } else if (position - 1 == next) {
            this.moveLeft();
        } else if (position - dungeon.getWidth() == next) {
            this.moveUp();
        } else if (position + dungeon.getWidth() == next) {
            this.moveDown();
        }
    }
    
    public void runaway() {
        return ;
        /*int position = getX()+dungeon.getWidth()*getY();
        LinkedList<Integer> path1 = BFS(position);
        if (path1.isEmpty()==true) return;
        int next = path1.get(path1.size()-2);
        if (position + 1 == next) {
            this.moveLeft();
        } else if (position - 1 == next) {
            this.moveRight();
        } else if (position - dungeon.getWidth() == next) {
            this.moveDown();
        } else if (position + dungeon.getWidth() == next) {
            this.moveUp();
        }*/
    }

    public void BFSUtil(int v, int object, boolean visited[]) {
        visited[v] = true;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(v);
        while (queue.size() != 0) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    path[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    if (adj.get(u).get(i) == object) {
                        return;
                    }
                }
            }
        
        }
    }

    // x = digits and y = widths, coordinate = x + width*y
    public LinkedList<Integer> BFS(int v) {
        path = new int[num];
        boolean visited[] = new boolean[num];
        LinkedList<Integer> path2 = new LinkedList<Integer>();
        if (dungeon.getPlayer() == null) {
            return path2;
        }
        int object = (dungeon.getPlayer()).getX() + (dungeon.getWidth())*(dungeon.getPlayer()).getY();
        adj = new ArrayList<ArrayList<Integer>>(num);
        for (int i=0; i<num; ++i) { 
            path[i] = -1;
            visited[i] = false;
        }
        for (int i = 0; i < num; i++) {
            adj.add(new ArrayList<Integer>());
        }
        addEdges();
        found = false;
        BFSUtil(v, object, visited);
        
        path2.add(object);
        int tmp = object;
        while (path[tmp] != -1) {
            path2.add(path[tmp]);
            tmp = path[tmp];
        }
        return path2;
    }

    public void addEdge(int v, int w) { 
        adj.get(v).add(w);
    }

    public void addEdges() {
        for (int i=0; i<(dungeon.getWidth()); i++) {
            for (int j=0; j<(dungeon.getHeight()); j++) {
                if( i < (dungeon.getWidth() - 1)){//right
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*j+1);
                }
                if( i > 0 ){//left
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*j-1);
                }
                if ( j > 0 ){//up
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*(j-1));
                }
                if ( j < (dungeon.getHeight() - 1)){ //down
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*(j+1));
                }
            }
        }
    }
}