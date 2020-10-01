import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static ArrayList<Integer> findString(String path, String str) {
        ArrayList<Integer> indexes = new ArrayList<>();

        try {
            int len = str.length();

            File file = new File("./" + path);
            FileReader freader = new FileReader(file);

            BufferedReader reader = new BufferedReader(freader);

            char[] chunk1 = new char[len];
            char[] chunk2 = new char[len];
            int res1 = reader.read(chunk1, 0, len);
            int res = reader.read(chunk2, 0, len);

            if (res1 != -1 && res == -1 && Arrays.toString(chunk1).equals(str)) {
                indexes.add(0);
                return indexes;
            }

            int globalID = 0;
            while (res != -1) {
                String chunkStr = String.valueOf(chunk1).concat(String.valueOf(chunk2));
                //System.out.println("String: " + chunkStr);
                int index = 0;
                index = chunkStr.indexOf(str, index);
                if (index != -1) {
                    if (indexes.size() == 0) {
                        indexes.add(globalID + index);
                    } else {
                        if (indexes.get(indexes.size()-1) != index+globalID)
                            indexes.add(globalID + index);
                    }
                }
                globalID += str.length();
                chunk1 = Arrays.copyOf(chunk2, len);
                res = reader.read(chunk2, 0, len);
                //System.out.println(chunk1);
                //System.out.println(chunk2);
                //System.out.println("====");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexes;
    }

    public static void main(String[] argv) {

        Scanner read = new Scanner(System.in);
        String path = read.nextLine();
        String str = read.nextLine();

        ArrayList<Integer> list = findString(path, str);
        list.toArray();

        for (int i : list) {
            System.out.print(i + " ");
        }
    }

}