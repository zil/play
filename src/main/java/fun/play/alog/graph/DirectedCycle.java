package fun.play.alog.graph;

import com.google.common.base.Joiner;

import fun.play.alog.Stack;

public class DirectedCycle {
	private int[] edgeTo;
	private boolean[] marked;
	private boolean[] onStack;
	private Stack<Integer> cycle;
	
	public DirectedCycle(DirectedGraph graph) {
		edgeTo = new int[graph.V()];
		marked = new boolean[graph.V()];
		onStack = new boolean[graph.V()];
		
		for (int v = 0; v < graph.V(); v++) {
			if(!marked[v])dfs(graph,v);
		}
	}

	private void dfs(DirectedGraph G, int v) {
		if(hasCycle())return;
		
		marked[v]=true;
		onStack[v]=true;
		
		for (int w: G.adj(v)) {
			if(!marked[w]){
				edgeTo[w] = v;
				dfs(G, w);
				continue;// miss this line,debug in tear.
			}
			
			if(onStack[w]){
				this.cycle = new Stack<>();
				for(int i = v;i != w;i=edgeTo[i]){
					this.cycle.push(i);
				}
				this.cycle.push(w);
				this.cycle.push(v);
			}
		}
		
		onStack[v]=false;
	}

	public boolean hasCycle() {
		return null != cycle;
	}
	
	public Iterable<Integer> cycle(){
		return cycle;
	}
	
	public static void main(String[] args) {
		DirectedGraph graph = new DirectedGraph(DirectedGraph.class.getResourceAsStream("mediumDG.txt"));
		DirectedCycle dc = new DirectedCycle(graph);
		Joiner j = Joiner.on(" -> ");
		
		if(dc.hasCycle())System.err.println(j.join(dc.cycle()));
	}
}