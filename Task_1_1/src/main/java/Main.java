import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //TODO input
        int[] array = {1,11,2,3,4,5,55,6,6,7,8,89};

        array = HeapSort.testSort(array);

        for (int j : array) {
            System.out.print(j + ", ");
        }

    }
}
