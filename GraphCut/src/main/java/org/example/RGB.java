package org.example;

public class RGB {
    public int red;
    public int green;
    public int blue;

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public RGB(int red, int green, int blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
    }
}
