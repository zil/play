package fun.play.alog.graph;

import fun.play.alog.Stack;

import java.util.PriorityQueue;

/**
 * Created by li on 8/12/17.
 */
public class LazyPrimMST {
    private boolean marked[];
    private Stack<Edge> mst;
    private PriorityQueue<Edge> pq;

    public LazyPrimMST(EdgedWeighedGraph g) {
        this.marked = new boolean[g.V()];
        mst = new Stack<>();
        pq = new PriorityQueue<>(g.E());
        visit(g,0);
        while (!pq.isEmpty()){
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);
            if(marked[v] && marked[w])continue;

            mst.push(e);
            if(!marked[v])visit(g,v);
            if(!marked[w])visit(g,w);
        }
    }

    private void visit(EdgedWeighedGraph g, int v) {
        marked[v] = true;
        for(Edge e: g.adj(v)){
            if(!marked[e.other(v)])pq.add(e);
        }
    }

    public Iterable<Edge> edges(){
        return mst;
    }

    public static void main(String[] args) {
        EdgedWeighedGraph edgedWeighedGraph = new EdgedWeighedGraph(LazyPrimMST.class.getResourceAsStream("/mediumEWG.txt"));
        LazyPrimMST lazyPrimMST = new LazyPrimMST(edgedWeighedGraph);
        for(Edge e: lazyPrimMST.edges()){
            System.out.println(e);
        }
    }
}
