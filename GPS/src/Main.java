import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
        System.out.println("chargement des communes terminé");
        Commune[] listeCommunes = communes.getListeCommunes();

        // Decoupage des communes par population
        Commune[] top50 = new Commune[50];
        Commune[] top100 = new Commune[50];
        Commune[] other = new Commune[listeCommunes.length - 100];
        // Trier la liste de communes par population

        ArrayList<Commune> listeCommunesArrayList = new ArrayList<Commune>(Arrays.asList(listeCommunes));
        // Remove null values
        listeCommunesArrayList.removeAll(Arrays.asList(null, ""));
        listeCommunesArrayList.sort(Comparator.comparingInt(Commune::getPopulation).reversed());

        top50 = listeCommunesArrayList.subList(0, 50).toArray(new Commune[50]);
        top100 = listeCommunesArrayList.subList(50, 100).toArray(new Commune[50]);
        other = listeCommunesArrayList.subList(100, listeCommunesArrayList.size()).toArray(new Commune[listeCommunesArrayList.size() - 100]);

        // Calcul de la distance entre 2 communes
        Distance distance = new Distance(top50, top100);

        // On calcule le temps de trajet
        Commune metz = top50[30];
        Commune nancy = top50[40];
        Commune paris = top50[0];
        Commune random = other[0];
        Commune random2 = top100[0];
        distance.timeBetween(nancy, metz);
        distance.timeBetween(nancy, paris);
        distance.timeBetween(nancy, random);
        distance.timeBetween(nancy, random2);

        // On creer notre graphe
        Graph graphe = new Graph(distance, listeCommunesArrayList);

        // On calcule le chemin le plus rapide entre deux communes
        System.out.println(graphe.fastestPath(nancy, metz));
        System.out.println(graphe.fastestPath(nancy, paris));
        System.out.println(graphe.fastestPath(nancy, random));
        System.out.println(graphe.fastestPath(nancy, random2));

    }
}
