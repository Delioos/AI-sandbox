public class PlusProche extends AlgoClassification {

    public PlusProche() {
        super();
    }




    /**
     * méthode predictImage
     * @param image Imagette
     */
    public int predictImage(Imagette image) {

        double distanceMin = image.distanceImagette(this.donnees[0]);
        int index = 0;
        
        for (int i = 1; i < this.donnees.length; i++) {
            double distance = image.distanceImagette(this.donnees[i]);
            if (distance < distanceMin) {
                distanceMin = distance;
                index = i;
            }
        }

        return this.donnees[index].getValeur();
    }

}
