
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


class Layer {
    public Neuron Neurons[];
    public int 	  Length;

    /**
     * Couche de Neurones
     *
     * @param l     Taille de la couche
     * @param prev  Taille de la couche précédente
     */
    public Layer(int l, int prev) {
        Length = l;
        Neurons = new Neuron[l];

        for(int j = 0; j < Length; j++)
            Neurons[j] = new Neuron(prev);
    }
}

