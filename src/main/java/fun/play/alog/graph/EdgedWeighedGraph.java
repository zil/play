package fun.play.alog.graph;

import fun.play.alog.LinkedList;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by li on 8/12/17.
 */
public class EdgedWeighedGraph {
    private int V;
    private int E;
    private final LinkedList<Edge>[] adj;

    public EdgedWeighedGraph(InputStream in) {
        try(Scanner scanner = new Scanner(in)){
            V = scanner.nextInt();
            E = scanner.nextInt();
            this.adj = (LinkedList<Edge>[]) new LinkedList[V];
            for (int j = 0; j < V; j++) {
                this.adj[j] = new LinkedList<>();
            }
            scanner.nextLine();
            for (int i = 0; i < E; i++) {

                int v = scanner.nextInt();
                scanner.skip("\\s+");
                int w = scanner.nextInt();
                scanner.skip("\\s+");
                double weight = scanner.nextDouble();

                if(v > V - 1 || w > V - 1){
                    System.err.printf("illegal edge[u=%d,v=%d]\n",v,w);
                    continue;
                }
                addEdge(new Edge(v,w,weight));
                scanner.nextLine();
            }
        }catch (Exception e) {
            throw new IllegalStateException("图的内容有误:",e);
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
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
    }
}
