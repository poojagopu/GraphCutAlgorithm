public class Adjacency {
    public PixelPos position;
    public int[] edgeWeights;
    public PixelPos[] neighbours;

    public Adjacency(int rowPos, int colPos, int leftWeight, int rightWeight, int topWeight, int bottomWeight){
        this.position = new PixelPos(rowPos, colPos);
        this.edgeWeights = new int[4];
        this.edgeWeights[0] = leftWeight;
        this.edgeWeights[1] = rightWeight;
        this.edgeWeights[2] = topWeight;
        this.edgeWeights[3] = bottomWeight;
        this.neighbours = new PixelPos[4];
        neighbours[0] = new PixelPos(rowPos - 1, colPos);
        neighbours[1] = new PixelPos(rowPos + 1, colPos);
        neighbours[2] = new PixelPos(rowPos, colPos + 1);
        neighbours[3] = new PixelPos(rowPos, colPos + 1);
    }

    @Override
    public String toString() {
        return "{\"PixelPos\":{\"Row\":" + position.getRowPos() + ",\"Col\":" + position.getColPos() + "}," +
                "\"Left\":" + edgeWeights[0] + ",\"Right\":" + edgeWeights[0] + ",\"Top\":" + edgeWeights[0] + "," +
                "\"Bottom\":" + edgeWeights[0] + ",\"Neighbours\":{\"Left\":" + neighbours[0].toString() +
                ",\"Right\":" + neighbours[1].toString() + ",\"Top\":" + neighbours[2].toString() +
                ",\"Bottom\":" + neighbours[3].toString() + ",}}";
    }
}

class PixelPos{
    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public void setColPos(int colPos) {
        this.colPos = colPos;
    }

    int rowPos;
    int colPos;
    PixelPos(int rowPos, int colPos) {
        this.rowPos = rowPos;
        this.colPos = colPos;
    }

    @Override
    public String toString(){
        return "{\"Row\":" + this.rowPos + ",\"Col\":" + this.colPos +"}";
    }
}
