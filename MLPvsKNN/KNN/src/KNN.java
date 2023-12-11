public class KNN extends AlgoClassification {

    public KNN() {
        super();
    }

    @Override
    public int predictImage(Imagette image) {
        int[] knn = this.getKNN(image, 10);

        int plusPresent = knn[0];

        int[] nbPresent = new int[10];

        for (int i = 0; i < knn.length; i++) {
            nbPresent[knn[i]]++;
        }

        for (int i = 0; i < nbPresent.length; i++) {
            if (nbPresent[i] > nbPresent[plusPresent]) {
                plusPresent = i;
            }
        }


        return plusPresent;

    }

    public int[] getKNN(Imagette image, int k) {
        Imagette[] knn = new Imagette[k];
        for (int i = 0; i < k; i++) {
            knn[i] = this.donnees[i];
        }
        for (int i = 0; i < this.donnees.length - 1; i++) {
            Imagette plusGrand = knn[0];
            int indexPlusGrand = 0;
            for (int j = 0; j < k ; j++) {
                if (knn[j].distanceImagette(image) > plusGrand.distanceImagette(image)) {
                    plusGrand = knn[j];
                    indexPlusGrand = j;
                }
            }
            if (this.donnees[i].distanceImagette(image) < plusGrand.distanceImagette(image)) {
                knn[indexPlusGrand] = this.donnees[i];
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = knn[i].getValeur();
        }
        System.out.println("knn : "+res[0]+" "+res[1]+" "+res[2]+" "+res[3]+" "+res[4]);
        return res;
    }

    /**
     * méthode trierTabDouble
     * @return double[]
     */
    public double[] trierTabDouble(double[] tab) {
        double[] res = new double[tab.length];
        for (int i = 0; i < tab.length; i++) {
            double min = tab[i];
            int index = i;
            for (int j = i; j < tab.length; j++) {
                if (tab[j] < min) {
                    min = tab[j];
                    index = j;
                }
            }
            res[i] = min;
            tab[index] = tab[i];
        }
        return res;
    }

}
