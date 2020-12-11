public class And implements ICommand {
    @Override
    public String getTokenRepr() {
        return "&";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public double apply(double [] args) {
        if (args[0] != 0 && args[1] != 0) {
            return 1;
        }
        return 0;
    }

}