package fun.play.alog.graph;

import com.google.common.base.Joiner;

import fun.play.alog.Stack;

public class DFS implements Path{
	private final int s;
	private boolean marked[];
	private int edgeTo[];
	
	public DFS(Graph g,int s) {
		this.s = s;
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		dfs(g,s);
	}

	private void dfs(Graph g, int v) {
		marked[v] = true;		
		for (int u : g.adj(v)) {
			if(!marked[u]){
				edgeTo[u] = v;
				dfs(g, u);
			}
		}
		
	}

	@Override
	public boolean hasPath(int v) {
		return marked[v];
	}

	@Override
	public Iterable<Integer> pathTo(int v) {
		if(!hasPath(v))throw new IllegalStateException("no path to " + v); 
		
		Stack<Integer> stack = new Stack<>();
		for (int i = v; i != this.s; i = edgeTo[i]) {
			stack.push(i);
		}
		stack.push(this.s);
		return stack;
	}
	
	Joiner joiner = Joiner.on("->");
	public String pathStr(int v) {
		return joiner.join(pathTo(v));
	}
	
	public static void main(String[] args) {
		Graph g = new Graph(Graph.class.getResourceAsStream("mediumG.txt"));
		DFS dfs = new DFS(g, 0);
		for (int i = 0; i < g.V(); i++) {
			if(dfs.hasPath(i))System.err.println(dfs.pathStr(i));
		}
	}
}
