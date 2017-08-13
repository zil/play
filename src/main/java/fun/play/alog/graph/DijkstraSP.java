package fun.play.alog.graph;

import fun.play.alog.Stack;

import java.util.PriorityQueue;

/**
 * Created by li on 8/13/17.
 */
public class DijkstraSP {
    private double dist[];
    private PriorityQueue<DirectedEdge> pq;
    private DirectedEdge edgeTo[];

    public DijkstraSP(EdgedWeighedDiGraph g, int s) {
        int V = g.V();
        dist = new double[V];
        for (int i = 0; i < V; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
        }
        dist[s] = 0.0;

        edgeTo = new DirectedEdge[V];
        pq = new PriorityQueue<>();
        visit(g, s);
        while (!pq.isEmpty()) {
            DirectedEdge de = pq.poll();
            if (dist[de.from()] + de.weight() < dist[de.to()]) {
                dist[de.to()] = dist[de.from()] + de.weight();
                edgeTo[de.to()] = de;
                visit(g, de.to());
            }
        }
    }

    public static void main(String[] args) {
        EdgedWeighedDiGraph ewd = new EdgedWeighedDiGraph(DijkstraSP.class.getResourceAsStream("/mediumEWD.txt"));
        DijkstraSP dijkstraSP = new DijkstraSP(ewd, 30);
        Iterable<DirectedEdge> path = dijkstraSP.path(150);
        for (DirectedEdge edge : path) {
            System.out.println(edge);
        }
    }

    private void visit(EdgedWeighedDiGraph g, int s) {
        for (DirectedEdge e : g.adj(s)) {
            if (dist[e.to()] == Double.POSITIVE_INFINITY) pq.add(e);
        }
    }

    public boolean hasPath(int v) {
        return Double.POSITIVE_INFINITY != dist(v);
    }

    public double dist(int v) {
        return dist[v];
    }

    public Iterable<DirectedEdge> path(int v) {
        Stack<DirectedEdge> edges = new Stack<>();
        DirectedEdge t = edgeTo[v];
        while (null != t) {
            if (edges.size() > 100) break;
            edges.push(t);
            t = edgeTo[t.from()];
        }
        return edges;
    }
}
