import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Communes communes;
        // si le fichier existe on l'utilise
        if (new File("communes.ser").exists()) {
            // Deserialization
            FileInputStream fis = new FileInputStream("communes.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            communes = (Communes) ois.readObject();
            ois.close();

        }
        else {
            System.out.println("chargement des communes...");
            // on cree l objet communes et on l'utilise
            communes = new Communes();
            // Serialization
            FileOutputStream fos = new FileOutputStream("communes.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(communes);
            oos.close();
        }
        System.out.println(Arrays.toString(communes.getListeCommunes()));

        Distance distance = new Distance();

        // On prends 2 communes au hasard
        Commune commune1 = communes.getListeCommunes()[(int) (Math.random() * communes.getListeCommunes().length)];
        Commune commune2 = communes.getListeCommunes()[(int) (Math.random() * communes.getListeCommunes().length)];

        // On regarde le type de route
    }
}
