import java.util.Arrays;

class HeapSort {
    private static int[] arr;
    private static int size;

    // Initialize heap array and copy values to this array
    /* Heap(int n, int [] src) {
        arr = new int[n];
        size = n;
        for (int i = 0; i < n; i++) {
            arr[i] = src[i];
        }
    } */

    private static void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void siftDown(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int smallest = i;
        if (left < size && arr[left] > arr[smallest]) {
            smallest = left;
        }
        if (right < size && arr[right] > arr[smallest]) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            siftDown(smallest);
        }
    }

    private static void buildHeap() {
        int len = size;
        for (int i = (len/2); i >= 0; i--) {
            siftDown(i);
        }
    }

    // Sorts elements in heap array and return it
    public static int [] sort(int n, int [] src) {

        arr = new int[n];
        size = n;
        for (int i = 0; i < n; i++) {
            arr[i] = src[i];
        }

        if (size <= 1) {
            return arr;
        }

        buildHeap();
        int length = size;
        for (int i = 0; i < length; i++) {
            swap(0, size-1);
            size--;
            siftDown(0);
        }
        size = length;

        return arr;
    }

    // This wrapper is just for tests
    public static int[] testSort(int[] array) {

        return HeapSort.sort(array.length, array);
    }

}
