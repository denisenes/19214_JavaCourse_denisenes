public class Heap {
    int[] arr;
    int size;

    Heap(int n) {
        arr = new int[n];
        size = n;
    }

    public void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void siftDown(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int smallest = i;
        if (left < size && arr[left] > arr[smallest])
            smallest = left;
        if (right < size && arr[right] > arr[smallest]) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            siftDown(smallest);
        }
    }

    public void buildHeap() {
        int len = size;
        for (int i = (len/2); i >= 0; i--) {
            siftDown(i);
        }
    }

    public void heapSort() {
        buildHeap();
        int length = size;
        for (int i = 0; i < length; i++) {
            swap(0, size-1);
            size--;
            siftDown(0);
        }
        size = length;
    }

}
