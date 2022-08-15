package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Archer extends DynamicEntity implements Foe{
    private Dungeon dungeon;
    public boolean alive;

    private ArrayList<ArrayList<Integer>> adj; 
    private int path[];
    private boolean found;
    private int num;

    public GoalEnemy goal2;

    public Archer(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.alive = true;
        this.dungeon = dungeon;
        this.num = (dungeon.getWidth()) * (dungeon.getHeight());
        dungeon.addEntity(this);
        dungeon.notifyGoalEnemy();
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void gotHurt(){
        gotKilled();
    }

    public void gotKilled() {
        setAlive(false);
        dungeon.removeEntity(this);
        dungeon.notifyGoalEnemy();
    }

    public boolean playerVicinity(){
        if( ((getX() == dungeon.getPlayer().getX()) && (getY() == dungeon.getPlayer().getY() - 1)) ||
            ((getX() == dungeon.getPlayer().getX()) && (getY() == dungeon.getPlayer().getY() + 1)) ||
            ((getX() == dungeon.getPlayer().getX() - 1) && (getY() == dungeon.getPlayer().getY())) ||
            ((getX() == dungeon.getPlayer().getX() + 1) && (getY() == dungeon.getPlayer().getY()))    ){
                return true;
            }
            return false;
    }
    
    @Override
    public int moveto(Entity e) {
        if (e instanceof Player && ((Player) e).getInvincibity() == true) {
            gotHurt();
            e.setPosition(getX(), getY());
            return 0;
        }
        if (e instanceof Arrow) {
            gotHurt();
            e.setPosition(getX(), getY());
            return 0;
        }
        return 1;
    }

    public String lineOfFire(){
        TestArrow arrowUp = new TestArrow(dungeon, getX(), getY());
        TestArrow arrowDown = new TestArrow(dungeon, getX(), getY());
        TestArrow arrowLeft = new TestArrow(dungeon, getX(), getY());
        TestArrow arrowRight = new TestArrow(dungeon, getX(), getY());
        if (arrowUp.findPlayer("up") == true){
            return "up";
        }
        if (arrowDown.findPlayer("down") == true){
            return "down";
        }
        if (arrowLeft.findPlayer("left") == true){
            return "left";
        }
        if (arrowRight.findPlayer("right") == true){
            return "right";
        }
        return "false";
    }

    public void fire(DungeonController dungeonController){
        String direction = lineOfFire();
        if (direction == "up"){
            dungeonController.DynamicAddArrow(dungeon, getX(), getY() - 1, direction);
        }
        else if (direction == "down"){
            dungeonController.DynamicAddArrow(dungeon, getX(), getY() + 1, direction);
        }
        else if (direction == "left"){
            dungeonController.DynamicAddArrow(dungeon, getX() - 1, getY(), direction);
        }
        else if (direction == "right"){
            dungeonController.DynamicAddArrow(dungeon, getX() + 1, getY(), direction);
        }
    }

    ///Movement functions//////////////////////////////////////////////////////

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
        int position = getX()+dungeon.getWidth()*getY();
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
        }
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
        TestRobot testRobot = new TestRobot(dungeon, 0, 0);
        for (int i=0; i<(dungeon.getWidth()); i++) {
            for (int j=0; j<(dungeon.getHeight()); j++) {
                testRobot.setX(i);
                testRobot.setY(j);
                if (testRobot.moveRight() == true) {
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*j+1);
                }
                testRobot.setX(i);
                testRobot.setY(j);
                if (testRobot.moveLeft() == true) {
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*j-1);
                }
                testRobot.setX(i);
                testRobot.setY(j);
                if (testRobot.moveUp() == true) {
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*(j-1));
                }
                testRobot.setX(i);
                testRobot.setY(j);
                if (testRobot.moveDown() == true) {
                    addEdge(i+(dungeon.getWidth())*j, i+(dungeon.getWidth())*(j+1));
                }
            }
        }
    }
}