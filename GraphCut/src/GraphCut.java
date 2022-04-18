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
        //Reading the image
        //File src= new File("C:\\Users\\pooja\\IdeaProjects\\GraphCut\\src.jpg");
        //File target= new File("C:\\Users\\pooja\\IdeaProjects\\GraphCut\\target.jpg");
        File src= new File("/Users/akshithreddyc/Desktop/Workplace/GraphCut/src.jpeg");
        File target= new File("/Users/akshithreddyc/Desktop/Workplace/GraphCut/target.jpeg");
        BufferedImage srcImg = ImageIO.read(src);
        BufferedImage targetImg = ImageIO.read(target);
        //System.out.println(targetImg.getHeight()+" "+targetImg.getWidth());//src: 353 271  target: 353 271
        for (int j = 0; j < srcImg.getHeight(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < srcImg.getWidth(); i++) {
                //Retrieving contents of a pixel
                int pixel = srcImg.getRGB(i,j);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            srcPixelValues.add(rowValues);
        }

        for (int j = 0; j < targetImg.getHeight(); j++) {
            ArrayList<RGB> rowValues = new ArrayList<>();
            for (int i = 0; i < targetImg.getWidth(); i++) {
                //Retrieving contents of a pixel
                int pixel = targetImg.getRGB(i,j);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                RGB temp = new RGB(color.getRed(),color.getGreen(),color.getBlue());
                rowValues.add(temp);
            }
            targetPixelValues.add(rowValues);
        }

        edmondsKarp(getEdgeValues(targetPixelValues, srcPixelValues));
    }

    public static ArrayList<Adjacency> getEdgeValues(ArrayList<ArrayList<RGB>> target, ArrayList<ArrayList<RGB>> src){
        //adjacency list
        ArrayList<Adjacency> adjList = new ArrayList<>();
        int leftWeight = 0, rightWeight = 0, topWeight = 0, bottomWeight = 0;
        //int edgeWeight = 0;
        //calculating edge weights for 4 neighbors of each pixel
        for(int i = 125; i < 225; i++){
            for(int j = 0; j < 271; j++){
                //left neighbor
                if(j - 1 > 125){
                    leftWeight = (Math.abs(src.get(i).get(j).squaredSum() - target.get(i).get(j).squaredSum()) +
                            Math.abs(src.get(i).get(j - 1).squaredSum() - target.get(i).get(j - 1).squaredSum()));
                }
                //right neighbor
                if(j + 1 < src.get(i).size()){
                    rightWeight = (Math.abs(src.get(i).get(j).squaredSum() - target.get(i).get(j).squaredSum()) +
                            Math.abs(src.get(i).get(j + 1).squaredSum() - target.get(i).get(j + 1).squaredSum()));
                }
                //top
                topWeight = (Math.abs(src.get(i).get(j).squaredSum() - target.get(i).get(j).squaredSum()) +
                        Math.abs(src.get(i - 1).get(j).squaredSum() - target.get(i - 1).get(j).squaredSum()));

                //bottom
                bottomWeight = (Math.abs(src.get(i).get(j).squaredSum() - target.get(i).get(j).squaredSum()) +
                        Math.abs(src.get(i + 1).get(j).squaredSum() - target.get(i + 1).get(j).squaredSum()));

                adjList.add(new Adjacency(i, j, leftWeight, rightWeight, topWeight, bottomWeight));
            }
        }

        System.out.println(adjList.toString());

        return adjList;
    }

    public static void edmondsKarp(ArrayList<Adjacency> adjList){

    }
}
