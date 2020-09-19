import java.util.Arrays;

public class Main {

    public static int[] sort(int[] array) {
        Heap heap = new Heap(array.length);
        heap.arr = Arrays.copyOf(array, array.length);

        heap.heapSort();
        return heap.arr;
    }

    public static void main(String[] args) {

        //TODO input
        int[] array = {1,11,2,3,4,5,55,6,6,7,8,89};

        array = sort(array);

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }

    }
}
