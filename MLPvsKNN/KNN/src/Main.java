public class Main {
    public static void main(String[] args) {
        // demarrage du chrono
        long startTime = System.currentTimeMillis();
        KNN plusProche = new KNN();

        // On prends 100 images par défaut, sinon on utilise le paramètre passé en argument
        int nbImages = 100;
        if (args.length > 0) {
            nbImages = Integer.parseInt(args[0]);
        }
        ListeImage listeImage = new ListeImage("ressources/MNIST/t10k-labels.idx1-ubyte", "ressources/MNIST/t10k-images.idx3-ubyte", nbImages);

        Statistiques stat = new Statistiques(plusProche, listeImage.getListeImage());

        System.out.println("Pourcentage de réussite : " + stat.getPourcentageCorrect() + "%");

        // fin du chrono
        long endTime = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (endTime - startTime) + "ms. Soit " + (endTime - startTime) / 1000 + " secondes.");
    }
}