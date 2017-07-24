package fun.play.alog.graph;

import java.io.InputStream;
import java.util.Scanner;

import fun.play.alog.LinkedList;

public class Graph {
	private LinkedList<Integer>[] adj;
	
	public Graph(InputStream in) {
		try(Scanner scanner = new Scanner(in)){
			int V = scanner.nextInt();
			int E = scanner.nextInt();
			init(V);
			for (int i = 0; i < E; i++) {
				int u = scanner.nextInt();
				scanner.skip("\\s+");
				int v = scanner.nextInt();

				if(u > V - 1 || v > V - 1){
					System.err.printf("illegal edge[u=%d,v=%d]\n",u,v);
					continue;
				}
				addEdge(u, v);
				scanner.nextLine();
			}
		}catch (Exception e) {
			throw new IllegalStateException("图的内容有误:"+e.getMessage());
		}
	}
	
	public Graph(int vertex) {
		init(vertex);
	}

	@SuppressWarnings("unchecked")
	private void init(int vertex) {
		adj = new LinkedList[vertex];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new LinkedList<>();
		}
	}
	
	public void addEdge(int u,int v){
		adj[u].add(v);
	}
	
	public Iterable<Integer> adj(int v){
		return adj[v];
	}
	
	public static void main(String[] args) {
		Graph graph = new Graph(System.in);
		System.err.println("vertex from 0:" + graph.adj(0));
	}
	
}
