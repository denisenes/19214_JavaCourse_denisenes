import picocli.CommandLine;

public class Main {

    public static void main(String [] args) {
        int exitCode = new CommandLine(new Notebook()).execute(args);
        System.out.println(exitCode);
    }

}