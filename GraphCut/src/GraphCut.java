import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class GraphCut {
    public static void main(String[] args) throws IOException {
        Timestamp start= Timestamp.from(Instant.now());
        //System.out.println("Hello world!");
        ArrayList<ArrayList<RGB>> srcPixelValues = new ArrayList<>();
        ArrayList<ArrayList<RGB>> targetPixelValues = new ArrayList<>();
        ArrayList<ArrayList<RGB>> maskPixelValues = new ArrayList<>();

        /*//getting filenames from config
        Properties prop = new Properties();
        String fileName = "/Users/akshithreddyc/Desktop/Workplace/GraphCut/config/config.properties";
        //
        // InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        prop.load(new FileInputStream(fileName));
        String source = prop.getProperty("source");*/

        File src= new File("/Users/akshithreddyc/Desktop/Workplace/GraphCut/src.jpeg");
        File target= new File("/Users/akshithreddyc/Desktop/Workplace/GraphCut/target.jpeg");
        File mask= new File("/Users/akshithreddyc/Desktop/Workplace/GraphCut/mask.jpg");
        BufferedImage srcImg = ImageIO.read(src);
        BufferedImage targetImg = ImageIO.read(target);
        BufferedImage maskImg = ImageIO.read(mask);
        //System.out.println(targetImg.getHeight()+" "+targetImg.getWidth());//src: 353 271  target: 353 271
        for (int j = 0; j < srcImg.getWidth(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < srcImg.getHeight(); i++) {
                //Retrieving contents of a pixel
                int pixel = srcImg.getRGB(j,i);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            srcPixelValues.add(rowValues);
        }
        //System.out.println(srcImg.getHeight()+" "+srcImg.getWidth());
        for (int j = 0; j < targetImg.getWidth(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < targetImg.getHeight(); i++) {
                //Retrieving contents of a pixel
                int pixel = targetImg.getRGB(j,i);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            targetPixelValues.add(rowValues);
        }

        for (int j = 0; j < maskImg.getWidth(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < maskImg.getHeight(); i++) {
                //Retrieving contents of a pixel
                int pixel = maskImg.getRGB(j,i);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            maskPixelValues.add(rowValues);

        }
        //System.out.println(maskPixelValues.size()+" "+maskPixelValues.get(0).size());

        //edmondsKarp(buildGraph(targetPixelValues, srcPixelValues, maskPixelValues));

        adjacencyMatrix(targetPixelValues, srcPixelValues, maskPixelValues);

        Timestamp end= Timestamp.from(Instant.now());

        System.out.println(start + " "+ end);
    }

    public static ArrayList<Node> buildGraph(ArrayList<ArrayList<RGB>> target, ArrayList<ArrayList<RGB>> src,
                                                  ArrayList<ArrayList<RGB>> mask) {
        //adjacency list
        ArrayList<Node> adjList = new ArrayList<>();
        int leftWeight = 0, rightWeight = 0, topWeight = 0, bottomWeight = 0;
        boolean isSource, isTarget;
        PixelPos leftPixel = new PixelPos(-1, -1, false,false),
                rightPixel = new PixelPos(-1, -1, false,false),
                topPixel = new PixelPos(-1, -1, false,false),
                bottomPixel = new PixelPos(-1, -1, false,false);
        //int edgeWeight = 0;
        //calculating edge weights for 4 neighbors of each pixel
        //and if the node is from source or target and building an adjacency list
        for(int i = 0; i < src.size(); i++){
            for(int j = 0; j < src.get(i).size(); j++){
                //left neighbor
                if(j - 1 >= 0) {
                    leftWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i).get(j - 1).difference(target.get(i).get(j - 1)), 2);

                    leftPixel = new PixelPos(i, j - 1, mask.get(i).get(j - 1).isWhite(),
                            mask.get(i).get(j - 1).isBlack());
                }
                //right neighbor
                if(j + 1 < src.get(i).size()) {
                    rightWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i).get(j + 1).difference(target.get(i).get(j + 1)), 2);

                    rightPixel = new PixelPos(i, j + 1, mask.get(i).get(j + 1).isWhite(),
                            mask.get(i).get(j + 1).isBlack());
                }
                //top
                if(i - 1 >= 0) {
                    topWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i - 1).get(j).difference(target.get(i - 1).get(j)), 2);

                    topPixel = new PixelPos(i - 1, j, mask.get(i - 1).get(j).isWhite(),
                            mask.get(i - 1).get(j).isBlack());
                }
                //bottom
                if(i + 1 < src.size()) {
                    bottomWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i + 1).get(j).difference(target.get(i + 1).get(j)), 2);

                    bottomPixel = new PixelPos(i + 1, j, mask.get(i + 1).get(j).isWhite(),
                            mask.get(i + 1).get(j).isBlack());
                }

                /*if(leftWeight < 0 || rightWeight < 0 || topWeight < 0 || bottomWeight < 0){
                    System.out.println(i+" "+j);
                }*/

                isSource = mask.get(i).get(j).isWhite();
                isTarget = mask.get(i).get(j).isBlack();

                adjList.add(new Node(i, j, leftWeight, rightWeight, topWeight, bottomWeight, isSource, isTarget,
                        leftPixel, rightPixel, topPixel, bottomPixel));
            }
        }

        System.out.println(adjList);

        return adjList;
    }

    public static void adjacencyMatrix(ArrayList<ArrayList<RGB>> target, ArrayList<ArrayList<RGB>> src,
                                             ArrayList<ArrayList<RGB>> mask) {
        //adjacency list
        ArrayList<Node> adjList = new ArrayList<>();
        int pixelNumber = 0;
        Vertex[][] adjMatrix = new Vertex[95663][95663];
        int leftWeight = 0, rightWeight = 0, topWeight = 0, bottomWeight = 0;
        boolean isSource, isTarget;
        Vertex leftPixel = new Vertex(false,false,0),
                rightPixel = new Vertex(false,false,0),
                topPixel = new Vertex(false,false,0),
                bottomPixel = new Vertex(false,false,0);
        //int edgeWeight = 0;
        //calculating edge weights for 4 neighbors of each pixel
        //and if the node is from source or target and building an adjacency list
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                //left neighbor
                if(j - 1 >= 0) {
                    leftWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i).get(j - 1).difference(target.get(i).get(j - 1)), 2);

                    leftPixel = new Vertex( mask.get(i).get(j - 1).isWhite(),mask.get(i).get(j - 1).isBlack(),leftWeight);

                    adjMatrix[i * 3 + j][pixelNumber - 1] = leftPixel;
                }
                //right neighbor
                if(j + 1 < 3) {
                    rightWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i).get(j + 1).difference(target.get(i).get(j + 1)), 2);

                    rightPixel = new Vertex( mask.get(i).get(j + 1).isWhite(),mask.get(i).get(j + 1).isBlack(),rightWeight);

                    adjMatrix[i * 3 + j][pixelNumber + 1] = rightPixel;
                }
                //top
                if(i - 1 >= 0) {
                    topWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i - 1).get(j).difference(target.get(i - 1).get(j)), 2);

                    topPixel = new Vertex( mask.get(i - 1).get(j).isWhite(),mask.get(i - 1).get(j).isBlack(),topWeight);

                    adjMatrix[i * 3 + j][pixelNumber - 3] = topPixel;
                }
                //bottom
                if(i + 1 < 3) {
                    bottomWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i + 1).get(j).difference(target.get(i + 1).get(j)), 2);

                    bottomPixel = new Vertex( mask.get(i + 1).get(j).isWhite(),mask.get(i + 1).get(j).isBlack(),bottomWeight);

                    adjMatrix[i * 3 + j][pixelNumber + 3] = bottomPixel;
                }

                /*if(leftWeight < 0 || rightWeight < 0 || topWeight < 0 || bottomWeight < 0){
                    System.out.println(i+" "+j);
                }*/
                pixelNumber++;
                isSource = mask.get(i).get(j).isWhite();
                isTarget = mask.get(i).get(j).isBlack();

                //System.out.println(adjMatrix[i][j]);

                //adjList.add(new Node(i, j, leftWeight, rightWeight, topWeight, bottomWeight, isSource, isTarget,
                        //leftPixel, rightPixel, topPixel, bottomPixel));
            }
        }

        //System.out.println(adjList);
        System.out.println(adjMatrix);
        //return adjList;
    }

    public static void edmondsKarp(ArrayList<Node> graph) {
        while (true) {
            final Queue<Node> bfsQ = new ArrayDeque<>();
            boolean[] visitedArray = new boolean[graph.size()];

            bfsQ.add(graph.get(graph.size() - 1));

            for (int i = 0; i < graph.size(); ++i) {
                visitedArray[i] = false;
            }
            visitedArray[graph.size() - 1] = true;

            boolean check = false;
            Node current = null;
            while (!bfsQ.isEmpty()) {
                current = bfsQ.peek();
                if(current.position.isTarget) {
                    check = true;
                    break;
                }
                bfsQ.remove();
                for (int i = 0; i < graph.size(); ++i) {
                    if (!visitedArray[i] /*&& capacity[current][i] > flow[current][i]*/) {
                        visitedArray[i] = true;
                        bfsQ.add(graph.get(i));
                        //parent[i] = current;
                    }
                }
            }
        }
    }

    /*public static int BFS(PixelPos source){
        boolean[][] visited = new boolean[100][100];

        LinkedList<PixelPos> pxlQ = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        visited[source.getRowPos()][source.getColPos()] = true;
        pxlQ.add(source);

        while (pxlQ.size() != 0)
        {
            source = pxlQ.poll();

            //Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                //if (!visited[n])
                {
                    //visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return 0;
    }*/
}
