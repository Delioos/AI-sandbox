public abstract class AlgoClassification {

    public Imagette[] donnees;

    public AlgoClassification() {
        /*
        // print curent directory
        System.out.println("Current dir: " + System.getProperty("user.dir"));*/
        ListeImage listeImage = new ListeImage("ressources/MNIST/train-labels.idx1-ubyte", "ressources/MNIST/train-images.idx3-ubyte", 10000);
        this.donnees = listeImage.getListeImage();
        System.out.println("Il y a " + this.donnees.length + " images dans la liste");
    }

    /**
     * méthode abstraitre predictImage
     * @param image Imagette
     */
    public abstract int predictImage(Imagette image);


}
