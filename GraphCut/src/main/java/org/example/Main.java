package org.example;
import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws IOException {
        //System.out.println("Hello world!");
        ArrayList<ArrayList<RGB>> srcPixelValues = new ArrayList<>();
        ArrayList<ArrayList<RGB>> targetPixelValues = new ArrayList<>();
        //Reading the image
        File src= new File("C:\\Users\\pooja\\IdeaProjects\\GraphCut\\src.jpg");
        File target= new File("C:\\Users\\pooja\\IdeaProjects\\GraphCut\\target.jpg");
        BufferedImage srcImg = ImageIO.read(src);
        BufferedImage targetImg = ImageIO.read(target);
        //System.out.println(img.getHeight()+" "+img.getWidth());//src: 217 272  target: 353 271
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

        System.out.println(targetPixelValues);
        //271 125
        // 271 225
        // 271* 100
    }

    public static void getEdgeValues(ArrayList<ArrayList<RGB>> target, ArrayList<ArrayList<RGB>> src){

    }
}