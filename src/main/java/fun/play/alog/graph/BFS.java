package fun.play.alog.graph;

import java.io.FileNotFoundException;

import com.google.common.base.Joiner;

import fun.play.alog.Queue;
import fun.play.alog.Stack;

public class BFS implements Path{
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
	
	public boolean hasPath(int v){
		return marked[v];
	}

	public Iterable<Integer> pathTo(int v){
		if(!hasPath(v)) throw new IllegalStateException("no path to " + v); 
		
		Stack<Integer> stack = new Stack<>();
		for (int i = v; i != s; i=edgeTo[i]) {
			stack.push(i);
		}
		stack.push(this.s);
		return stack;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph(Graph.class.getResourceAsStream("mediumG.txt"));
		BFS bfs = new BFS(g, 3);
		Joiner joiner = Joiner.on("->");
		for (int v=0;v < g.V();v++) {
			if(bfs.hasPath(v))System.err.println(joiner.join(bfs.pathTo(v)));
		}
	}
}
