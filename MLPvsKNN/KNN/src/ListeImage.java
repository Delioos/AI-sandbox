import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ListeImage {

    Imagette[] listeImage;

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
                System.out.println("ajout de l'imagette " + i);
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

}
