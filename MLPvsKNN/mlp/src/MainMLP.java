import java.util.Arrays;
import java.util.HashMap;

public class MainMLP {
    public static void main(String[] args) {
        // Configurer le MLP
        int nbImages = 10000;
        ListeImage listeImage = new ListeImage("ressources/MNIST/t10k-labels.idx1-ubyte", "ressources/MNIST/t10k-images.idx3-ubyte", nbImages);

        int pixels = listeImage.listeImage[0].getPixels().length * listeImage.listeImage[0].getPixels()[0].length;

        int[] layers = {pixels, 150, 100, 10}; // Exemple avec une couche d'entrée de 2 neurones, une couche cachée de 2 neurones et une couche de sortie de 1 neurone
        double learningRate = 0.6;

        // Choisissez la fonction d'activation (Sigmoïde ou Tangente Hyperbolique)
        SigmoidFunction activationFunction = new SigmoidFunction();
        // TransferFunction activationFunction = new HyperbolicTangentFunction();

        MLP mlp = new MLP(layers, learningRate, activationFunction);

        /*
        // Exemple de données d'apprentissage pour la table XOR
        double[][] trainingInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] trainingOutputs = {{0}, {1}, {1}, {0}};

         */
        if (args.length > 0) {
            nbImages = Integer.parseInt(args[0]);
        }

        // TODO
        double [][] trainingInputs =  listeImage.getMLPFormatedInputs();
        double [][] trainingOutputs = listeImage.getMLPFormatedOutputs();

        // Entraînement
        int maxEpochs = 1000000; // Nombre maximal de passages des exemples
        double targetError = 0.01; // Erreur cible
        HashMap<Integer, Double> errorsForExemple = new HashMap<>();
        int epoch = 1;
        double totalError = 1;
        while (epoch < maxEpochs && (totalError/epoch) > targetError) {
            int randomIndex = (int) (Math.random() * (trainingInputs.length - 2));
            double[] input = trainingInputs[randomIndex];
            double[] output = new double[10];

            int indexGoodValue = listeImage.listeImage[randomIndex].valeur;
            output[indexGoodValue] = 1;


            double error = mlp.backPropagate(input, output);
            errorsForExemple.put(randomIndex, error);
            boolean allErrorsAreUnderTarget = true;
            for (double e : errorsForExemple.values()) {
                if (e > targetError) {
                    allErrorsAreUnderTarget = false;
                    break;
                }
            }
            if (allErrorsAreUnderTarget) {
                System.out.println("Target error reached at epoch: " + epoch);
                break;
            } else {
                System.out.print("Epoch: " + epoch + ", Error: " + (totalError / (epoch + 1)) + "\r");
            }
            totalError += error;
            epoch++;
        }

        // Tester sur les exemples après l'apprentissage
        int nbFailed = 0;
        System.out.println("Testing on training examples:");
        for (int i = 0; i < trainingInputs.length-1; i++) {
            double[] input = trainingInputs[i];
            double[] predictedOutput = mlp.execute(input);
            // on récup la valeur de l'index la plus grande
            int indexMax = 0;
            for (int j = 0; j < predictedOutput.length; j++) {
                if (predictedOutput[j] > predictedOutput[indexMax]) {
                    indexMax = j;
                }
            }
            System.out.println("Attendu: " + listeImage.listeImage[i].valeur + ", Prédit: " + indexMax);
            if (listeImage.listeImage[i].valeur != indexMax) {
                nbFailed++;
            }
        }
        System.out.println("Failed: " + nbFailed + "/" + trainingInputs.length + "\r");




        /*
        // Tester sur d'autres exemples
        double[] testInputs = // TODO
        for (int i = 0; i < testInputs.length; i++) {
            double input = testInputs[i];
            double predictedOutput = mlp.execute(input);
        }*

         */

    }
}