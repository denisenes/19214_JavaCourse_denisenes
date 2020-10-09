import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    private static void swapCharBuffers(char [] b1, char [] b2) {
        char [] tmp = b1;
        b1 = b2;
        b2 = tmp;
    }

    private static void swapStrings(String s1, String s2) {
        String tmp = s1;
        s1 = s2;
        s2 = tmp;
    }

    /**
     * поиск всех вхождений:
     *  buffer = concat(chunk1, chunk2)
     * 1) из входного потока читаем в буфер, размер которого равен chunk1 + chunk2 = 2*str.length
     * 2) ищем вхождение подстроки в этом буфере
     * 3) далее в chunk1 записываем chunk2, а в chunk2 читаем новые данные из входного потока
     * 4) повторяем 2 и 3, пока во входном потоке есть данные
     *
     * @param inStream - поток ввода, в котором ищется подстрока str
     * @param str - искомая подстрока
     * @return indexes - лист индексов позиций искомой подстроки
     */
    public static ArrayList<Long> findString(InputStream inStream, String str) throws IOException {
        ArrayList<Long> indexes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8))) {
            int len = str.length();

            char[] chunk1 = new char[len];
            char[] chunk2 = new char[len];
            String chunk1str;
            String chunk2str;
            String chunkStr;

            int res1 = reader.read(chunk1, 0, len);
            int res = reader.read(chunk2, 0, len);
            chunk1str = String.valueOf(chunk1);
            chunk2str = String.valueOf(chunk2);

            if (res1 != -1 && res == -1 && Arrays.equals(chunk1, str.toCharArray())) {
                indexes.add(0L);
                return indexes;
            }

            long globalID = 0;
            while (res != -1) {
                chunkStr = chunk1str.concat(chunk2str);
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
                //swap
                swapCharBuffers(chunk1, chunk2);
                swapStrings(chunk1str, chunk2str);

                res = reader.read(chunk2, 0, len);
                chunk2str = String.valueOf(chunk2);
            }
        }

        return indexes;
    }

    public static void main(String[] argv) {

        Scanner read = new Scanner(System.in);
        String path = read.nextLine();
        String str = read.nextLine();

        try (InputStream in = new FileInputStream("./" + path)) {

            ArrayList<Long> list = findString(in, str);
            list.toArray();

            for (long i : list) {
                System.out.print(i + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}