package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public interface Foe {
    public void gotHurt();
    public void approach();
    public void runaway();
    public void BFSUtil(int v, int object, boolean visited[]);
    public LinkedList<Integer> BFS(int v);
    public void addEdge(int v, int w);
    public void addEdges();
}