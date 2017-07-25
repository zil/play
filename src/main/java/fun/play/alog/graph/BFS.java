package fun.play.alog.graph;

import java.io.FileNotFoundException;

import com.google.common.base.Joiner;

import fun.play.alog.Queue;
import fun.play.alog.Stack;

public class BFS {
	private final int s;
	private boolean marked[];
	private int edgeTo[];
	
	public BFS(Graph g,int source) {
		this.s = source;
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		bfs(g);
	}
	
	private void bfs(Graph g) {
		Queue<Integer> queue = new Queue<>();
		queue.add(s);
		marked[s] = true;
		while(queue.isNotEmpty()){
			Integer s = queue.take();
			
			for (int v : g.adj(s)) {
				if(!marked[v]){
					marked[v] = true;
					edgeTo[v] = s;
					queue.add(v);
				}
			}
		}
	}
	
	public boolean hasPathTo(int v){
		return marked[v];
	}

	public String pathTo(int v){
		if(!hasPathTo(v)) throw new IllegalStateException("no path to " + v); 
		
		Stack<Integer> stack = new Stack<>();
		for (int i = v; i != s; i=edgeTo[i]) {
			stack.push(i);
		}
		stack.push(this.s);
		return Joiner.on("->").join(stack);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph(Graph.class.getResourceAsStream("mediumG.txt"));
		BFS bfs = new BFS(g, 3);
		for (int v=1;v < g.V();v++) {
			if(bfs.hasPathTo(v))System.err.println(bfs.pathTo(v));
		}
	}
}
