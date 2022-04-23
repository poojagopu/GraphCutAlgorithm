public class Vertex {
    public boolean isSource;
    public boolean isTarget;

    public boolean isSource() {
        return isSource;
    }

    public void setSource(boolean source) {
        isSource = source;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }

    public int getEdgeWeight() {
        return edgeWeight;
    }

    public void setEdgeWeight(int edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    public int edgeWeight;

    public Vertex(boolean isSource, boolean isTarget, int edgeWeight) {
        this.isSource = isSource;
        this.isTarget = isTarget;
        this.edgeWeight = edgeWeight;
    }
}
