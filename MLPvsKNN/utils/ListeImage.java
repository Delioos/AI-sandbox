import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class ListeImage {

    public Imagette[] listeImage;

    private static int nbValue = 60000;

    public ListeImage(String path_label, String path_image,int nbval) {

        try {
            DataInputStream dis_label = new DataInputStream(new FileInputStream(path_label));
            DataInputStream dis = new DataInputStream(new FileInputStream(path_image));
            int magic = dis.readInt();
            int magic_label = dis_label.readInt();
            int nbImages = dis.readInt();
            int nb_labels = dis_label.readInt();
            int nbLignes = dis.readInt();
            int nbColonnes = dis.readInt();
            this.nbValue = nbval;
            this.listeImage = new Imagette[nbValue];
            System.out.println("magic: " + magic);
            System.out.println("nbImages: " + nbImages);
            System.out.println("nbLignes: " + nbLignes);
            System.out.println("nbColonnes: " + nbColonnes);

            for (int i = 0; i < nbValue - 1; i++) {
                int[][] pixels = new int[nbLignes][nbColonnes];
                for (int j = 0; j < nbLignes; j++) {
                    for (int k = 0; k < nbColonnes; k++) {
                        pixels[j][k] = dis.readUnsignedByte();
                    }
                }

                int label = dis_label.readUnsignedByte();
                Imagette imagette = new Imagette(pixels, label);
                this.listeImage[i] = imagette;
                System.out.print("Progression: " + ((i * 100) / (nbValue - 1)) + "%\r");
            }

            // on enregistre une imagette aléatoire
            int index = (int) (Math.random() * 10);
            this.listeImage[index].saveImage();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * GETTER
     */
    public Imagette[] getListeImage() {
        return listeImage;
    }

    /**
     * méthode permettant de recuperer les images sous forme de tableau de double
     * @return
     */
    public double[][] getMLPFormatedInputs() {
        double [][] res = new double[this.listeImage.length][784];
        for (int i = 0; i < this.listeImage.length - 1; i++) {
            int[][] pixels = this.listeImage[i].getPixels();
            int index = 0;
            for (int j = 0; j < pixels.length - 1; j++) {
                for (int k = 0; k < pixels[0].length - 1; k++) {
                    res[i][index] = pixels[j][k] / 255.0; // Normalisation des valeurs de pixels entre 0 et 1
                    index++;
                }
            }
        }
        return res;
    }


    public double[][] getMLPFormatedOutputs() {
        double [][] res = new double[this.listeImage.length][1];
        for (int i = 0; i < this.listeImage.length - 1; i++) {
            res[i][0] = this.listeImage[i].getValeur();
            System.out.println("valeur de l'image "+i+" : "+res[i][0]);
        }
        return res;
    }
}
