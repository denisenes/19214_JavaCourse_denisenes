import java.util.Arrays;

class HeapSort {

    private static void swap(int [] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void siftDown(int i, int size, int [] arr) {
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
            swap(arr, i, smallest);
            siftDown(smallest, size, arr);
        }
    }

    private static void buildHeap(int [] arr) {
        int len = arr.length;
        for (int i = len; i >= 0; i--) {
            siftDown(i, len, arr);
        }
    }

    // Sorts elements in heap array and return it
    public static int [] sort(int [] src) {

        if (src.length <= 1) {
            return src;
        }

        buildHeap(src);

        int length = src.length;
        for (int i = 0; i < src.length; i++) {
            swap(src, 0, length-1);
            length--;
            siftDown(0, length, src);
        }

        return src;
    }

    // This wrapper is just for tests
    public static int[] testSort(int[] array) {

        return HeapSort.sort(array);
    }

}
