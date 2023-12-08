import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Imagette {

    int[][] pixels;
    int valeur;
    private String path = "./files/images/";

    public Imagette(int[][] pixels, int valeur) {
        this.pixels = pixels;
        this.valeur = valeur;
    }

    /**
     * GETTER
     */
    public int[][] getPixels() {
        return pixels;
    }

    public int getValeur() {
        return valeur;
    }


    /**
     * méthode saveImage
     */
    public void saveImage() {
        System.out.println("sauvegarde de l'imagette : "+this.valeur);
        try {
            BufferedImage image = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);
            for (int ligne = 0; ligne < pixels.length; ligne++) {
                for (int col = 0; col < pixels[0].length; col++) {
                    int pixel = pixels[ligne][col];
                    int rgb = (pixel << 16) | (pixel << 8) | pixel;
                    image.setRGB(col, ligne, rgb);
                }
            }
            File outputfile = new File("./files/images/" + System.currentTimeMillis() + ".png");
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double distanceImagette(Imagette imagette) {
        double distance = 0;
        for (int ligne = 0; ligne < pixels.length; ligne++) {
            for (int col = 0; col < pixels[0].length; col++) {
                distance += Math.abs(pixels[ligne][col] - imagette.getPixels()[ligne][col]);
            }
        }
        return distance;
    }

}
