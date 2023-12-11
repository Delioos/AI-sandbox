public class Statistiques {

    public AlgoClassification algo;
    public Imagette[] donneesTest;

    public Statistiques(AlgoClassification algo, Imagette[] donneesTest) {
        this.algo = algo;
        this.donneesTest = donneesTest;
    }

    /**
     * methode getPourcentageCorrect
     *
     * @return double
     */
    public double getPourcentageCorrect() {
        int nbCorrect = 0;
        int nbIncorrect = 0;

        for (Imagette image : this.donneesTest) {
            if (image != null) {
                System.out.println("====================================");
                System.out.println("image : " + image.getValeur() + " prédiction : " + algo.predictImage(image));
                System.out.println("------------------------------------");
                if (image.getValeur() == algo.predictImage(image)) {
                    nbCorrect++;
                } else {
                    nbIncorrect++;
                }
            }
        }

        return (double) (nbCorrect / (1.0 * (nbCorrect + nbIncorrect))) * 100;
    }
}
