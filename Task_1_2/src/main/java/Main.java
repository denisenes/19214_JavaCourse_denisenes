import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

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
    public static ArrayList<Long> findString(InputStream inStream, String str) {
        ArrayList<Long> indexes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8))) {
            int len = str.length();

            char[] chunk1 = new char[len];
            char[] chunk2 = new char[len];
            int res1 = reader.read(chunk1, 0, len);
            int res = reader.read(chunk2, 0, len);

            if (res1 != -1 && res == -1 && Arrays.equals(chunk1, str.toCharArray())) {
                indexes.add(0L);
                return indexes;
            }

            String chunkStr;
            long globalID = 0;
            while (res != -1) {
                chunkStr = String.valueOf(chunk1).concat(String.valueOf(chunk2));
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