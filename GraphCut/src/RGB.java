public class RGB {
    public int alpha;
    public int red;
    public int green;
    public int blue;

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

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

    public RGB(int alpha, int red, int green, int blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
    }

    public int squaredSum(){
        return (int) (Math.pow(this.red, 2) + Math.pow(this.blue, 2) + Math.pow(this.green, 2));
    }

    public int difference(RGB pixelVal){
        return this.getRed() - pixelVal.getRed() + this.getGreen() - pixelVal.getGreen()
                + this.getBlue() - pixelVal.getBlue();
    }

    public boolean isWhite() {
        return this.red == 255 && this.green == 255 && this.blue == 255;
    }

    public boolean isBlack() {
        return this.red == 0 && this.green == 0 && this.blue == 0;
    }

    public String toString(){
        return "{"+this.red+","+this.green+","+this.blue+"}";
    }

}
