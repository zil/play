package fun.play.alog.graph;

/**
 * Created by li on 8/12/17.
 */
public class DirectedEdge implements Comparable<DirectedEdge>{
    private int v;
    private int w;
    private double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from(){return v;}
    public int to(){ return w; }
    public double weight(){return weight;}

    @Override
    public int compareTo(DirectedEdge that) {
        if(this.weight > that.weight)return 1;
        else if (this.weight < that.weight)return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return String.format("%d->%d %.2f", v,w,weight);
    }
}
