import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

    public void testLoadPlugin() {
        Calculator calculator = new Calculator();
        calculator.loadPlugin("And");
        calculator.loadPlugin("Or");
        calculator.loadPlugin("IfCommand");

        double res = calculator.calc("if | & 0 1 0 69 96");
        assertEquals(96.0, res);
    }

    public void testCalc() {
        Calculator calculator1 = new Calculator();
        double result1 = calculator1.calc("sin + - 1 2 1");
        assertEquals(0.0, result1);

        Calculator calculator2 = new Calculator();
        double result2 = calculator2.calc("- * / 15 - 7 + 1 1 3 + 2 + 1 1");
        assertEquals(5.0, result2);
    }
}