
class SigmoidFunction implements TransferFunction {
    @Override
    public double evaluate(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    @Override
    public double evaluateDer(double value) {
        double sigmoid = evaluate(value);
        return sigmoid * (1 - sigmoid);
    }
}