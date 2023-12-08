import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Configurer le MLP
        int[] layers = {2, 4, 4, 4, 1}; // Exemple avec une couche d'entrée de 2 neurones, une couche cachée de 2 neurones et une couche de sortie de 1 neurone
        double learningRate = 0.6;

        // Choisissez la fonction d'activation (Sigmoïde ou Tangente Hyperbolique)
        TransferFunction activationFunction = new SigmoidFunction();
        // TransferFunction activationFunction = new HyperbolicTangentFunction();

        MLP mlp = new MLP(layers, learningRate, activationFunction);

        // Exemple de données d'apprentissage pour la table ET
        double[][] trainingInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] trainingOutputs = {{0}, {0}, {0}, {1}};

        // Entraînement
        int maxEpochs = 10000; // Nombre maximal de passages des exemples
        double targetError = 0.01; // Erreur cible
        for (int epoch = 1; epoch <= maxEpochs; epoch++) {
            double totalError = 0.0;
            for (int i = 0; i < trainingInputs.length; i++) {
                double[] input = trainingInputs[i];
                double[] output = trainingOutputs[i];
                totalError += mlp.backPropagate(input, output);
            }
            double averageError = totalError / trainingInputs.length;

            // Afficher l'erreur pendant l'entraînement
            System.out.println("Epoch " + epoch + ", Error: " + averageError);

            // Tester sur les exemples d'apprentissage
            if (averageError < targetError) {
                System.out.println("Training completed successfully.");
                break;
            }
        }

        // Tester sur les exemples après l'apprentissage
        System.out.println("Testing on training examples:");
        for (int i = 0; i < trainingInputs.length; i++) {
            double[] input = trainingInputs[i];
            double[] predictedOutput = mlp.execute(input);
            System.out.println("Input: " + Arrays.toString(input) + ", Predicted Output: " + Arrays.toString(predictedOutput));
        }

        // Tester sur d'autres exemples
        System.out.println("Testing on additional examples:");
        double[][] testInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        for (int i = 0; i < testInputs.length; i++) {
            double[] input = testInputs[i];
            double[] predictedOutput = mlp.execute(input);
            System.out.println("Input: " + Arrays.toString(input) + ", Predicted Output: " + Arrays.toString(predictedOutput));
        }
    }
}