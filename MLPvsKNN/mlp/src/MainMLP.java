import java.util.Arrays;
import java.util.HashMap;

public class MainMLP {
    public static void main(String[] args) {
        // Configurer le MLP
        int[] layers = {2, 2, 1}; // Exemple avec une couche d'entrée de 2 neurones, une couche cachée de 2 neurones et une couche de sortie de 1 neurone
        double learningRate = 0.6;

        // Choisissez la fonction d'activation (Sigmoïde ou Tangente Hyperbolique)
        HyperbolicTangentFunction activationFunction = new HyperbolicTangentFunction();
        // TransferFunction activationFunction = new HyperbolicTangentFunction();

        MLP mlp = new MLP(layers, learningRate, activationFunction);

        /*
        // Exemple de données d'apprentissage pour la table XOR
        double[][] trainingInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] trainingOutputs = {{0}, {1}, {1}, {0}};

         */
        int nbImages = 10000;
        if (args.length > 0) {
            nbImages = Integer.parseInt(args[0]);
        }
        ListeImage listeImage = new ListeImage("ressources/MNIST/t10k-labels.idx1-ubyte", "ressources/MNIST/t10k-images.idx3-ubyte", nbImages);

        // TODO
        double [][] trainingInputs =  listeImage.getMLPFormatedInputs();
        double [][] trainingOutputs = listeImage.getMLPFormatedOutputs();
        for (int i = 0; i < trainingOutputs.length; i++) {
            System.out.println(i + " input: "+ trainingInputs[i] + " value: " + trainingOutputs[i]);
        }

        // Entraînement
        int maxEpochs = 10000; // Nombre maximal de passages des exemples
        double targetError = 0.01; // Erreur cible
        HashMap<Integer, Double> errorsForExemple = new HashMap<>();
        for (int i = 0; i < maxEpochs; i++) {
            int randomIndex = (int) (Math.random() * trainingInputs.length);
            double[] input = trainingInputs[randomIndex];
            double[] output = trainingOutputs[randomIndex];
            double error = mlp.backPropagate(input, output);
            errorsForExemple.put(randomIndex, error);
            boolean allErrorsAreUnderTarget = true;
            for (double e : errorsForExemple.values()) {
                if (e > targetError) {
                    allErrorsAreUnderTarget = false;
                    break;
                }
            }
            System.out.println(errorsForExemple.values());
            if (allErrorsAreUnderTarget) {
                System.out.println("Target error reached at epoch: " + i);
                break;
            } else {
                System.out.println("Epoch: " + i + ", Error: " + error);
            }
        }

        // Tester sur les exemples après l'apprentissage
        System.out.println("Testing on training examples:");
        for (int i = 0; i < trainingInputs.length; i++) {
            double[] input = trainingInputs[i];
            double[] predictedOutput = mlp.execute(input);
            System.out.println("Input: " + Arrays.toString(input) + ", Predicted Output: " + Arrays.toString(predictedOutput));
        }




        /*
        // Tester sur d'autres exemples
        System.out.println("Testing on additional examples:");
        double[] testInputs = // TODO
        for (int i = 0; i < testInputs.length; i++) {
            double input = testInputs[i];
            double predictedOutput = mlp.execute(input);
            System.out.println("Input: " + Double.toString(input) + ", Predicted Output: " + Double.toString(predictedOutput));
        }*

         */

    }
}