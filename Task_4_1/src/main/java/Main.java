import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String [] args) throws IOException {
        // получаем строчку
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String expression = reader.readLine();

        // вычисляем
        Calculator calculator = new Calculator();
        double result = calculator.calc(expression);

        // печатаем
        System.out.println(result);
    }

}
