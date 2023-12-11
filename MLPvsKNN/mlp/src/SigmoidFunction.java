
class SigmoidFunction implements TransferFunction {
    @Override
    public double evaluate(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    @Override
    public double evaluateDer(double value) {
        return value * (1 - value);
    }
}