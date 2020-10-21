import java.util.stream.Stream;

public class Main {

    public static void main(String [] args) {
        PriorityQueue<Integer, String> queue = new PriorityQueue<>();
        queue.insert(3, "Mother");
        queue.insert(4, "Dog");
        queue.insert(1, "Father");
        queue.insert(2, "Son");

        for (String val : queue) {
            System.out.println(val);
        }
    }

}
