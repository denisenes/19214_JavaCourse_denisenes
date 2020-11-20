import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PriorityQueueTest extends TestCase {

    //тесты на очередь
    public void testExtractMin() {
        PriorityQueue<Integer, String> queue = new PriorityQueue<>();
        queue.insert(1, "Hello");
        queue.insert(10, "Something");
        assertEquals("Hello", queue.extractMin());
        assertEquals("Something", queue.extractMin());

        PriorityQueue<Integer, Integer> queue2 = new PriorityQueue<>();
        queue2.insert(1, 5);
        queue2.insert(5, 1);
        queue2.insert(3, 10);
        queue2.insert(2, 11);
        assertEquals((Integer) 5, queue2.extractMin());
        assertEquals((Integer) 11, queue2.extractMin());
        assertEquals((Integer) 10, queue2.extractMin());
        assertEquals((Integer) 1, queue2.extractMin());
    }

    //тесты на stream
    public void testStream() {
        PriorityQueue<Integer, Integer> queue = new PriorityQueue<>();
        queue.insert(20, 1);
        queue.insert(15, 2);
        queue.insert(10, 3);
        queue.insert(0, 4);

        Stream<Integer> stream1 = Stream.of(4, 3, 2, 1);
        Stream<Integer> stream2 = queue.stream();

        Iterator<?> iter1 = stream1.iterator(), iter2 = stream2.iterator();
        while(iter1.hasNext() && iter2.hasNext()) {
            assertEquals(iter1.next(), iter2.next());
        }

    }
}