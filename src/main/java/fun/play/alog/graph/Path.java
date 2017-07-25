package fun.play.alog.graph;

public interface Path {
	boolean hasPath(int v);
	Iterable<Integer> pathTo(int v);
}
