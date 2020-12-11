//why not...
public class IfCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "if";
    }
    @Override
    public int getArity() {
        return 3;
    }
    @Override
    public double apply(double [] args) {
        if (args[0] == 1) {
            return args[1];
        } else {
            return args[2];
        }
    }
}