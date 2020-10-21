import junit.framework.TestCase;
import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PriorityQueueTest extends TestCase {

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

    public void testStream() {
        PriorityQueue<Integer, Integer> queue = new PriorityQueue<>();
        queue.insert(1, 1);
        queue.insert(2, 2);
        queue.insert(3, 3);
        queue.insert(4, 4);

        assertStreams(IntStream.of(6, 7, 8, 9), queue.stream().map(x -> x + 5));
    }

    private void assertStreams(IntStream s1, Stream<Integer> s2) {
        int [] arr1 = s2.mapToInt(x -> x).toArray();
        int [] arr2 =  s1.toArray();
        Assert.assertArrayEquals(arr1, arr2);
    }
}