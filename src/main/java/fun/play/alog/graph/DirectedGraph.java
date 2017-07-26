package fun.play.alog.graph;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import fun.play.alog.LinkedList;

public class DirectedGraph {
	private LinkedList<Integer>[] adj;
	private int E = 0;
	private int[] indegree;
	
	public DirectedGraph(InputStream in) {
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
			e.printStackTrace();
			throw new IllegalStateException("图的内容有误:"+e.getMessage());
		}
	}
	
	public DirectedGraph(int vertex) {
		init(vertex);
	}

	@SuppressWarnings("unchecked")
	private void init(int vertex) {
		indegree = new int[vertex];
		adj = new LinkedList[vertex];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new LinkedList<>();
		}
	}
	
	public void addEdge(int u,int v){
		adj[u].add(v);
		indegree[v]++;
		E++;
	}
	
	public Iterable<Integer> adj(int v){
		return adj[v];
	}
	
	public int inDegree(int v){
		return indegree[v];
	}
	
	public int outDegree(int v){
		return adj[v].size();
	}
	
	public int E() {
		return this.E;
	}
	
	public int V(){
		return this.adj.length;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < adj.length; i++) {
			if(adj[i].isEmpty())continue;
			
			sb.append(i).append(":" + adj[i]).append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		DirectedGraph graph = new DirectedGraph(DirectedGraph.class.getResourceAsStream("mediumDG.txt"));
		System.err.println(graph);
	}
	
}
