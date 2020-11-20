import java.util.Spliterator;

public class Main {

    public static void main(String [] args) {
        PriorityQueue<Integer, String> queue = new PriorityQueue<>();
        queue.insert(3, "Mother");
        queue.insert(4, "Dog");
        queue.insert(1, "Father");
        queue.insert(2, "Son");

        /*String min = queue.extractMin();
        System.out.println(min);

        min = queue.extractMin();
        System.out.println(min);

        min = queue.extractMin();
        System.out.println(min);

        min = queue.extractMin();
        System.out.println(min);*/

        Spliterator<String> sp = queue.spliterator();
        sp.forEachRemaining(System.out::print);

        /*for (String val : queue) {
            System.out.println(val);
        }*/


    }

}
