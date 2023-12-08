public abstract class AlgoClassification {

    public Imagette[] donnees;

    public AlgoClassification() {
        ListeImage listeImage = new ListeImage("./files/MNIST/train-labels.idx1-ubyte", "./files/MNIST/train-images.idx3-ubyte", 10000);
        this.donnees = listeImage.getListeImage();
        System.out.println("Il y a " + this.donnees.length + " images dans la liste");
    }

    /**
     * méthode abstraitre predictImage
     * @param image Imagette
     */
    public abstract int predictImage(Imagette image);


}
