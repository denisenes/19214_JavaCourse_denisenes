import java.util.Iterator;

public class Main {

    public static void main(String[] argv) {
        Stack<Integer> s = new Stack();
        s.push(123);
        s.push(64);
        System.out.println(s.count());
        s.push(100);
        System.out.println(s.count());

    }

}
