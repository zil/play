package fun.play.alog.graph;

import fun.play.alog.LinkedList;

/**
 * Created by li on 8/12/17.
 */
public class EdgedWeighedGraph {
    private final int V;
    private int E;
    private final LinkedList<Edge>[] adj;

    public EdgedWeighedGraph(Graph g) {
        this.V = g.V();
        this.E = 0;
        this.adj = (LinkedList<Edge>[]) new LinkedList[V];
        for(int v = 0; v < V; v++){
            this.adj[v] = new LinkedList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e){
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
    }
}
