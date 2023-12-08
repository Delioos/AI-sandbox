public class Main {
    public static void main(String[] args) {

        KNN plusProche = new KNN();

        ListeImage listeImage = new ListeImage("./files/MNIST/t10k-labels.idx1-ubyte", "./files/MNIST/t10k-images.idx3-ubyte", 1000);

        Statistiques stat = new Statistiques(plusProche, listeImage.getListeImage());

        System.out.println("Pourcentage de réussite : " + stat.getPourcentageCorrect() + "%");


    }
}