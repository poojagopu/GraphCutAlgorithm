public class Node {
    public PixelPos position;
    public int[] edgeWeights;
    public PixelPos[] neighbours;
    //public boolean isSourcePixel;

    public Node(int rowPos, int colPos, int leftWeight, int rightWeight, int topWeight, int bottomWeight,
                boolean isSourcePixel, boolean isTargetPixel, PixelPos left, PixelPos right, PixelPos top,
                PixelPos bottom) {
        this.position = new PixelPos(rowPos, colPos, isSourcePixel, isTargetPixel);

        this.edgeWeights = new int[4];
        this.edgeWeights[0] = leftWeight;
        this.edgeWeights[1] = rightWeight;
        this.edgeWeights[2] = topWeight;
        this.edgeWeights[3] = bottomWeight;

        this.neighbours = new PixelPos[4];
        this.neighbours[0] = left;//new PixelPos(rowPos - 1, colPos, isSourcePixel, isTargetPixel);
        this.neighbours[1] = right;//new PixelPos(rowPos + 1, colPos, isSourcePixel, isTargetPixel);
        this.neighbours[2] = top;//null;//new PixelPos(rowPos, colPos + 1);
        this.neighbours[3] = bottom;//null;//new PixelPos(rowPos, colPos - 1);

        //this.isSourcePixel = isSourcePixel;
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

class PixelPos {
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
    boolean isSource;

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

    boolean isTarget;
    public PixelPos(int rowPos, int colPos, boolean isSource, boolean isTarget) {
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.isSource = isSource;
        this.isTarget = isTarget;
    }

    @Override
    public String toString(){
        return "{\"Row\":" + this.rowPos + ",\"Col\":" + this.colPos +"}";
    }
}
