import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphCut {
    public static void main(String[] args) throws IOException {
        //System.out.println("Hello world!");
        ArrayList<ArrayList<RGB>> srcPixelValues = new ArrayList<>();
        ArrayList<ArrayList<RGB>> targetPixelValues = new ArrayList<>();
        ArrayList<ArrayList<RGB>> maskPixelValues = new ArrayList<>();
        //Reading the image
        //File src= new File("C:\\Users\\pooja\\IdeaProjects\\GraphCut\\src.jpg");
        //File target= new File("C:\\Users\\pooja\\IdeaProjects\\GraphCut\\target.jpg");
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

        edmondsKarp(buildGraph(targetPixelValues, srcPixelValues, maskPixelValues));
    }

    public static ArrayList<Node> buildGraph(ArrayList<ArrayList<RGB>> target, ArrayList<ArrayList<RGB>> src,
                                                  ArrayList<ArrayList<RGB>> mask) {
        //adjacency list
        ArrayList<Node> adjList = new ArrayList<>();
        int leftWeight = 0, rightWeight = 0, topWeight = 0, bottomWeight = 0;
        boolean isSource, isTarget;
        PixelPos leftPixel = null, rightPixel = null, topPixel = null, bottomPixel = null;
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

        System.out.println(adjList.size());

        return adjList;
    }

    public static void edmondsKarp(ArrayList<Node> adjList) {
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
