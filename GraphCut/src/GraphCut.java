import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class GraphCut {
    public static ArrayList<Edge> cuts;
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
        File mask= new File("/Users/akshithreddyc/Desktop/Workplace/GraphCut/mask.jpeg");
        BufferedImage srcImg = ImageIO.read(src);
        BufferedImage targetImg = ImageIO.read(target);
        BufferedImage maskImg = ImageIO.read(mask);
        //System.out.println(targetImg.getHeight()+" "+targetImg.getWidth());//src: 353 271  target: 353 271
        for (int j = 0; j < srcImg.getWidth(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < srcImg.getHeight(); i++) {
                //Retrieving contents of a pixel
                int pixel = srcImg.getRGB(i,j);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getAlpha(), color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            srcPixelValues.add(rowValues);
        }
        //System.out.println(srcImg.getHeight()+" "+srcImg.getWidth());
        for (int j = 0; j < targetImg.getWidth(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < targetImg.getHeight(); i++) {
                //Retrieving contents of a pixel
                int pixel = targetImg.getRGB(i,j);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getAlpha(), color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            targetPixelValues.add(rowValues);
        }
        HashSet<Integer> srcNodes = new HashSet<>();
        HashSet<Integer> targetNodes = new HashSet<>();
        HashSet<Integer> overlapNodes = new HashSet<>();

        //writeImage(targetPixelValues, srcPixelValues);
        //writeImage();
        for (int j = 0; j < maskImg.getWidth(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < maskImg.getHeight(); i++) {
                //Retrieving contents of a pixel
                int pixel = maskImg.getRGB(i,j);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getAlpha(), color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            maskPixelValues.add(rowValues);

        }

        for(int i = 0; i < 2999; i++){
            targetNodes.add(i);
        }
        for(int i = 6999; i < 9999; i++){
            srcNodes.add(i);
        }
        //System.out.println(maskPixelValues);
        //int pixel = maskImg.getRGB(0,0);
        //Color color = new Color(pixel, true);
        System.out.println(srcNodes);
        System.out.println(targetNodes);

        //System.out.println(maskPixelValues.size()+" "+maskPixelValues.get(0).size());

        //edmondsKarp(buildGraph(targetPixelValues, srcPixelValues, maskPixelValues));

        //edmondsKarp(adjacencyMatrix(targetPixelValues, srcPixelValues, maskPixelValues), 9999, 0);

        Vertex[][] test = new Vertex[6][6];
        test[0][1] =new Vertex(false, false, 16);
        test[0][2] =new Vertex(false, false, 13);
        test[1][2] =new Vertex(false, false, 10);
        test[1][3] =new Vertex(false, false, 12);
        test[2][1] =new Vertex(false, false, 4);
        test[2][4] =new Vertex(false, false, 14);
        test[3][2] =new Vertex(false, false, 9);
        test[3][5] =new Vertex(false, false, 20);
        test[4][3] =new Vertex(false, false, 7);
        test[4][5] =new Vertex(false, false, 4);
        test[5][0] =new Vertex(false, false, 0);
        int graph[][] = { {0, 16, 13, 0, 0, 0},
                            {0, 0, 10, 12, 0, 0},
                            {0, 4, 0, 0, 14, 0},
                            {0, 0, 9, 0, 0, 20},
                            {0, 0, 0, 7, 0, 4},
                            {0, 0, 0, 0, 0, 0}
                    };
        long maxFLow = getMaxFlow(10001, 0,adjacencyMatrix(targetPixelValues,srcPixelValues,maskPixelValues,srcNodes,targetNodes));
        //long maxFLow = getMaxFlow(0, 5,test);
        System.out.println(maxFLow);
        writeImage(targetPixelValues, srcPixelValues);
        //getMaxFlow( 0, 5, test);
        //bfsNew(adjacencyMatrix(targetPixelValues, srcPixelValues, maskPixelValues, srcNodes, targetNodes));
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

    public static Vertex[][] adjacencyMatrix(ArrayList<ArrayList<RGB>> target, ArrayList<ArrayList<RGB>> src,
                                             ArrayList<ArrayList<RGB>> mask, HashSet<Integer> sourceNodes,
                                             HashSet<Integer> targetNodes) {
        //adjacency list
        ArrayList<Node> adjList = new ArrayList<>();
        int pixelNumber = 0;
        Vertex[][] adjMatrix = new Vertex[10000][10000];
        int leftWeight = 0, rightWeight = 0, topWeight = 0, bottomWeight = 0;
        boolean isSource, isTarget;
        Vertex leftPixel = new Vertex(false,false,Integer.MIN_VALUE),
                rightPixel = new Vertex(false,false,Integer.MIN_VALUE),
                topPixel = new Vertex(false,false,Integer.MIN_VALUE),
                bottomPixel = new Vertex(false,false,Integer.MIN_VALUE);
        //int edgeWeight = 0;
        //calculating edge weights for 4 neighbors of each pixel
        //and if the node is from source or target and building an adjacency list
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                //left neighbor
                if(j - 1 >= 0) {
                    leftWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i).get(j - 1).difference(target.get(i).get(j - 1)), 2);

                    leftPixel = new Vertex(mask.get(i).get(j - 1).isWhite(),mask.get(i).get(j - 1).isBlack(),leftWeight);

                    adjMatrix[i * 100 + j][pixelNumber - 1] = leftPixel;
                    /*ArrayList<Vertex> temp = adjMatrix.get(i * adjMatrix.size() + j);
                    temp.set(pixelNumber - 1, leftPixel);
                    adjMatrix.set(i * adjMatrix.size() + j, temp);*/
                }
                //right neighbor
                if(j + 1 < 100) {
                    rightWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i).get(j + 1).difference(target.get(i).get(j + 1)), 2);

                    rightPixel = new Vertex( mask.get(i).get(j + 1).isWhite(),mask.get(i).get(j + 1).isBlack(),rightWeight);

                    adjMatrix[i * 100 + j][pixelNumber + 1] = rightPixel;
                    /*ArrayList<Vertex> temp = adjMatrix.get(i * adjMatrix.size() + j);
                    temp.set(pixelNumber + 1, topPixel);
                    adjMatrix.set(i * adjMatrix.size() + j, temp);*/
                }
                //top
                if(i - 1 >= 0) {
                    topWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i - 1).get(j).difference(target.get(i - 1).get(j)), 2);

                    topPixel = new Vertex( mask.get(i - 1).get(j).isWhite(),mask.get(i - 1).get(j).isBlack(),topWeight);

                    adjMatrix[i * 100 + j][pixelNumber - 100] = topPixel;
                    /*ArrayList<Vertex> temp = adjMatrix.get(i * adjMatrix.size() + j);
                    temp.set(pixelNumber - adjMatrix.size(), topPixel);
                    adjMatrix.set(i * adjMatrix.size() + j, temp);*/
                }
                //bottom
                if(i + 1 < 100) {
                    bottomWeight = (int) Math.pow(src.get(i).get(j).difference(target.get(i).get(j)), 2) +
                            (int) Math.pow(src.get(i + 1).get(j).difference(target.get(i + 1).get(j)), 2);

                    bottomPixel = new Vertex( mask.get(i + 1).get(j).isWhite(),mask.get(i + 1).get(j).isBlack(),bottomWeight);

                    adjMatrix[i * 100 + j][pixelNumber + 100] = bottomPixel;
                    /*ArrayList<Vertex> temp = adjMatrix.get(i * adjMatrix.size() + j);
                    temp.set(pixelNumber + adjMatrix.size(), bottomPixel);
                    adjMatrix.set(i * adjMatrix.size() + j, temp);*/
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
        //System.out.println(Arrays.deepToString(adjMatrix));
        /*for (int i = 0; i < adjMatrix.length; i++){
            // Loop through all elements of current row
            for (int j = 0; j < adjMatrix[i].length; j++)
                System.out.print(adjMatrix[i][j] + " ");
            System.out.print("\n");
        }*/

        Vertex[][] newAdjMat = new Vertex[10002][10002];
        Vertex[] sources = new Vertex[10002];
        Vertex[] targets = new Vertex[10002];

        for(int i = 1; i < 10001; i++) {
            if(targetNodes.contains(i)){
                newAdjMat[0][i] = new Vertex(true, false, Integer.MAX_VALUE);
                newAdjMat[i][0] = new Vertex(true, false, Integer.MAX_VALUE);
            } else if(sourceNodes.contains(i)) {
                newAdjMat[10001][i] = new Vertex(false, true, Integer.MAX_VALUE);
                newAdjMat[i][10001] = new Vertex(false, true, Integer.MAX_VALUE);
            } else {
                newAdjMat[0][i] = new Vertex(false, false, 0);
                newAdjMat[i][0] = new Vertex(false, false, 0);
            }
        }

        for(int i = 1; i < 10001; i++){
            System.arraycopy(adjMatrix[i - 1], 0, newAdjMat[i], 1, 10000);
        }

        for(int i = 0; i < newAdjMat.length; i++){
            System.out.print(i + 1 + "->");
            for(int j = 0; j < newAdjMat.length; j++){
                if(newAdjMat[i][j] != null && newAdjMat[i][j].edgeWeight > 0 )
                    System.out.print(j + 1 +", ");
            }
            System.out.print("\n");
        }
        return newAdjMat;
    }

    public static void edmondsKarp(Vertex[][] graph, int source, int sink) {
        int u = 0, v = 0;
        int[][] rGraph = new int[10002][10002];
        int[] parent = new int[10002];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if(graph[i][j] != null) {
                    rGraph[i][j] = graph[i][j].edgeWeight;
                } else {
                    rGraph[i][j] = 0;
                }
            }
        }

        while (bfs(rGraph,source, sink, parent)){
            int pathFlow = Integer.MAX_VALUE;
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] = rGraph[u][v] - pathFlow;
                rGraph[v][u] = rGraph[v][u] + pathFlow;
            }
        }

        //System.out.println(Arrays.deepToString(rGraph));
        /*for (int[] ints : rGraph) {
            for (int j = 0; j < rGraph.length; j++) {
                System.out.print(ints[j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }*/
        boolean[] isVisited = new boolean[graph.length];
        dfs(rGraph, source, isVisited);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] != null && graph[i][j].edgeWeight > 0 && isVisited[i] && !isVisited[j]) {
                    System.out.println(i + " - " + j);
                }
            }
        }
    }

    private static boolean bfs(int[][] rGraph, int s, int t, int[] parent) {

        boolean[] visited = new boolean[rGraph.length];

        Queue<Integer> bfsQ = new LinkedList<Integer>();
        bfsQ.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!bfsQ.isEmpty()) {
            int v = bfsQ.poll();
            for (int i = 0; i < rGraph.length; i++) {
                if (rGraph[v][i] > 0 && !visited[i]) {
                    bfsQ.offer(i);
                    visited[i] = true;
                    parent[i] = v;
                }
            }
        }

        return visited[t];
    }

    private static void dfs(int[][] rGraph, int s,
                            boolean[] visited) {
        visited[s] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[s][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }

    public static void bfsNew(Vertex[][] adjMatrix) {
        int source = 9999;
        boolean[] visited = new boolean[10000];
        boolean isSourceFound = false;
        Queue<Integer> q = new LinkedList<>();
        q.add(source);

        while(!q.isEmpty()) {
            int s = q.poll();
            for (int i = 0; i < 10000; i++) {

                if(adjMatrix[s][i] != null && adjMatrix[s][i].edgeWeight > 0 && !adjMatrix[s][i].isTarget
                        && !visited[i]){
                    q.add(i + 1);
                    System.out.println(i + 1);
                }
            }
        }
    }

    public static long getMaxFlow(int s, int t, Vertex[][] capacity) {
        boolean[] visited = new boolean[capacity.length];
        int[][] flow = new int[capacity.length][capacity.length];
        int[] parent = new int[capacity.length];
        while (true) {
            final Queue<Integer> Q = new ArrayDeque<Integer>();
            Q.add(s);

            for (int i = 0; i < capacity.length; ++i)
                visited[i] = false;
            visited[s] = true;

            boolean check = false;
            int current;
            while (!Q.isEmpty()) {
                current = Q.peek();
                if (current == t) {
                    check = true;
                    break;
                }
                Q.remove();
                for (int i = 0; i < capacity.length; ++i) {
                    if (capacity[current][i] != null && !visited[i] && capacity[current][i].edgeWeight > flow[current][i]) {
                        visited[i] = true;
                        Q.add(i);
                        parent[i] = current;
                    }
                }
            }
            if (!check)
                break;

            long temp;
            if (capacity[parent[t]][t] != null)
                temp = capacity[parent[t]][t].edgeWeight - flow[parent[t]][t];
            else
                temp = -flow[parent[t]][t];
            for (int i = t; i != s; i = parent[i])
                if (capacity[parent[i]][i] != null){
                    temp = Math.min(temp, (capacity[parent[i]][i].edgeWeight- flow[parent[i]][i]));
                } else {
                    temp = Math.min(temp, (-flow[parent[i]][i]));
                }


            for (int i = t; i != s; i = parent[i]) {
                flow[parent[i]][i] += temp;
                flow[i][parent[i]] -= temp;
            }
        }

        long result = 0;
        for (int i = 0; i < capacity.length; ++i)
            result += flow[s][i];

        cuts = new ArrayList<>();
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity.length; j++) {
                if (capacity[i][j] != null && capacity[i][j].edgeWeight > 0 && visited[i] && !visited[j]) {
                    System.out.println(i + " - " + j);
                    Edge temp = new Edge(j, i);
                    cuts.add(temp);
                }
            }
        }

        return result;

    }

    public static void writeImage(ArrayList<ArrayList<RGB>> targetImage, ArrayList<ArrayList<RGB>> sourceImage)
            throws IOException {
        int height = 100, width =  100;
        BufferedImage img = null;
        img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);


        int i = 0;
        int pixelNumber = 1;
        for (int y = 0; y < 30; y++)
        {
            for (int x = 0; x < 100; x++)
            {
                int a, r, g, b;
                a = targetImage.get(y).get(x).getAlpha();
                r = targetImage.get(y).get(x).getRed();
                g = targetImage.get(y).get(x).getGreen();
                b = targetImage.get(y).get(x).getBlue();

                int p = (a<<24) | (r<<16) | (g<<8) | b;

                img.setRGB(x, y, p);
            }
        }

        for (int y = 69; y < 100; y++)
        {
            for (int x = 0; x < 100; x++)
            {
                int a, r, g, b;
                a = sourceImage.get(y).get(x).getAlpha();
                r = sourceImage.get(y).get(x).getRed();
                g = sourceImage.get(y).get(x).getGreen();
                b = sourceImage.get(y).get(x).getBlue();

                int p = (a<<24) | (r<<16) | (g<<8) | b;

                img.setRGB(x, y, p);
            }
        }

        for (int y = 30; y < 70; y++)
        {
            for (int x = 0; x < 100; x++)
            {
                int a, r, g, b;
                a = sourceImage.get(y).get(x).getAlpha();
                r = sourceImage.get(y).get(x).getRed();
                g = sourceImage.get(y).get(x).getGreen();
                b = sourceImage.get(y).get(x).getBlue();
                int p = (a<<24) | (r<<16) | (g<<8) | b;

                img.setRGB(x, y, p);

                pixelNumber++;
            }
        }

        /*if(i < cuts.size()) {
            if(pixelNumber < cuts.get(i).getTargetVertex()){
                a = targetImage.get(y).get(x).getAlpha();
                r = targetImage.get(y).get(x).getRed();
                g = targetImage.get(y).get(x).getGreen();
                b = targetImage.get(y).get(x).getBlue();
            } else {
                i++;
                a = sourceImage.get(100 - y).get(x).getAlpha();
                r = sourceImage.get(100 - y).get(x).getRed();
                g = sourceImage.get(100 - y).get(x).getGreen();
                b = sourceImage.get(100 - y).get(x).getBlue();
            }
        } else {
            a = sourceImage.get(100 - y).get(x).getAlpha();
            r = sourceImage.get(100 - y).get(x).getRed();
            g = sourceImage.get(100 - y).get(x).getGreen();
            b = sourceImage.get(100 - y).get(x).getBlue();
        }*/

        File f = new File("/Users/akshithreddyc/Desktop/Workplace/GraphCut/output.png");
        ImageIO.write(img, "png", f);
    }
}
