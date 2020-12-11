import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Stack;

interface ICommand {
    String getTokenRepr();
    int getArity();

    double apply(double [] args);
}

class PlusCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "+";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public double apply(double [] args) {
        return args[0] + args[1];
    }
}

class MinusCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "-";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public double apply(double [] args) {
        return args[0] - args[1];
    }
}

class MulCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "*";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public double apply(double [] args) {
        return args[0] * args[1];
    }
}

class DivCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "/";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public double apply(double [] args) {
        return args[0] / args[1];
    }
}

class SinCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "sin";
    }
    @Override
    public int getArity() {
        return 1;
    }
    @Override
    public double apply(double [] args) {
        return Math.sin(args[0]);
    }
}

class CosCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "cos";
    }
    @Override
    public int getArity() {
        return 1;
    }
    @Override
    public double apply(double [] args) {
        return Math.cos(args[0]);
    }
}

class PowCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "^";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public double apply(double [] args) {
        return Math.pow(args[0], args[1]);
    }
}

class SqrtCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "sqrt";
    }
    @Override
    public int getArity() {
        return 1;
    }
    @Override
    public double apply(double [] args) {
        return Math.sqrt(args[0]);
    }
}

class LogCommand implements ICommand {
    @Override
    public String getTokenRepr() {
        return "log";
    }
    @Override
    public int getArity() {
        return 1;
    }
    @Override
    public double apply(double [] args) {
        return Math.sin(args[0]);
    }
}

public class Calculator {

    private HashMap<String, ICommand> handlers;

    /**
     * Construct Calculator
     * register default commands
     */
    public Calculator() {
        handlers = new HashMap<>();
        //register default calculator functions
        registerCommand(new PlusCommand());
        registerCommand(new MinusCommand());
        registerCommand(new MulCommand());
        registerCommand(new DivCommand());
        registerCommand(new SinCommand());
        registerCommand(new CosCommand());
        registerCommand(new PowCommand());
        registerCommand(new SqrtCommand());
        registerCommand(new LogCommand());
    }

    private void registerCommand(ICommand handler) {
        if (handler == null) {
            throw new NullPointerException();
        }
        handlers.put(handler.getTokenRepr(), handler);
    }

    void loadPlugin(String name) {
        try {
            ClassLoader loader = Calculator.class.getClassLoader();
            Class<?> cl = loader.loadClass(name);
            Constructor<?> ctor = cl.getConstructor();
            Object obj = ctor.newInstance();
            registerCommand((ICommand) obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * calculate value of the expression
     * @param expr - expression that we want to calculate
     * @return - value of expression
     */
    public double calc(String expr) {
        // tokenize expression
        String [] tokens = expr.split(" ");
        // create new stack for tokens
        Stack<String> stack = new Stack<>();
        // add all tokens from array on the stack in reverse order
        for (int i = tokens.length - 1; i >= 0; i--) {
            stack.push(tokens[i]);
        }

        // then let's calculate
        return recursivelyParse(stack);
    }

    // beautiful (maybe...) recursive parser
    private double recursivelyParse(Stack<String> stack) {
        // get current operation
        String tokenOperation = stack.pop();
        ICommand command = handlers.get(tokenOperation);
        // then let's calculate arguments of this command
        double[] args = new double[command.getArity()];
        for (int i = 0; i < command.getArity(); i++) {
            String token = stack.pop();
            if (isDouble(token)) {
                args[i] = Double.parseDouble(token); // if we get number then let's just put it in the args
            } else {
                stack.push(token); // return token on the stack
                args[i] = recursivelyParse(stack); // else we need to calculate this argument before adding it into the args
            }
        }
        // now we have arguments and can do the operation we need
        return command.apply(args);
    }

    private boolean isDouble(String token) {
        if (token == null) {
            return false;
        }
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}