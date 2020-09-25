import junit.framework.TestCase;
import org.junit.Assert;

public class HeapsortTest extends TestCase {

    public void testSort() {
        Assert.assertArrayEquals(new int[] {1, 2, 3, 4, 5}, HeapSort.testSort(new int[] {5, 4, 3, 2, 1}));
        Assert.assertArrayEquals(new int[] {1, 100, 344, 888, 1000, 1111, 10000, 10001}, HeapSort.testSort(new int[] {1000, 888, 1, 344, 100, 10001, 1111, 10000}));
        Assert.assertArrayEquals(new int[] {666, 666}, HeapSort.testSort(new int[] {666, 666}));
        Assert.assertArrayEquals(new int[] {-32, -32, 0, 32, 32, 64, 128}, HeapSort.testSort(new int[] {0, -32, 128, 32, -32, 32, 64}));
        Assert.assertArrayEquals(new int[] {0}, HeapSort.testSort(new int[] {0}));
        Assert.assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 11}, HeapSort.testSort(new int[] {11, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }

}