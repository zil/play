package fun.play.alog.graph;

/**
 * Created by li on 8/12/17.
 */
public class Edge implements Comparable<Edge>{
    private int v;
    private int w;
    private double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either(){return v;}
    public int other(int vertex){
        if(v == vertex){
            return w;
        }else if(w == vertex){
            return v;
        }else {
            throw new IllegalArgumentException("no vertex for this edge:" + this);
        }
    }


    @Override
    public int compareTo(Edge that) {
        if(this.weight > that.weight)return 1;
        else if (this.weight < that.weight)return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return String.format("%d->%d %.2f",v,w,weight);
    }
}
