public class Edge {
    public int sourceVertex;
    public int targetVertex;

    public int getSourceVertex() {
        return sourceVertex;
    }

    public void setSourceVertex(int sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public int getTargetVertex() {
        return targetVertex;
    }

    public void setTargetVertex(int targetVertex) {
        this.targetVertex = targetVertex;
    }

    public Edge(int sourceVertex, int targetVertex) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
    }
}
