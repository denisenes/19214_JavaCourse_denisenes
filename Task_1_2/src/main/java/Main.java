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
    public static ArrayList<Long> findString(InputStream inStream, String str) throws IOException {
        ArrayList<Long> indexes = new ArrayList<>();
        int strlen = str.length();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8))) {

            char[] chunk1 = new char[strlen];
            char[] chunk2 = new char[strlen];
            char[] strarr = str.toCharArray();

            int res1 = reader.read(chunk1, 0, strlen);
            int res = reader.read(chunk2, 0, strlen);

            if (Arrays.equals(chunk1, str.toCharArray())) {
                indexes.add(0L);
                if (res1 != -1 && res == -1) {
                    return indexes;
                }
            }

            long globalID = 0;
            while (res != -1) {
                int diff = res;
                // нереальная мега крутая часть для поиска вхождений подстроки в два чанка без их объединения
                // как же люблю O(n^2)...        :( <-- грусть
                for (int i = 1; i <= diff; i++) {
                    int checkFlag = 1; // становится нулем, если найдены 2 неодинаковых символа
                    for (int j = 0; j < strarr.length; j++) {
                        int id = i + j;
                        if (id < chunk1.length) { //если вылезаем за первый чанк, то типа шарим во втором
                            if (chunk1[id] != strarr[j]) {
                                checkFlag = 0;
                                //String s = Character.toString(chunk1[id]) +  " " + Character.toString(strarr[j]) + "\n";
                                //System.out.print(s);
                            }
                        } else {
                            if (chunk2[id - chunk1.length] != strarr[j]) {
                                checkFlag = 0;
                                //String s = Character.toString(chunk2[id - chunk1.length]) +  " " + Character.toString(strarr[j]) + "\n";
                                //System.out.print(s);
                            }
                        }
                    }
                    if (checkFlag == 1) {
                        indexes.add(globalID + i);
                    }
                }

                globalID += str.length();
                //System.out.print("Chunk1: " + Arrays.toString(chunk1) + " chunk2: " + Arrays.toString(chunk2) + "\n");
                char[] temp = chunk1;
                chunk1 = chunk2;
                chunk2 = temp;
                res = reader.read(chunk2, 0, strlen);
            }
        }

        return indexes;
    }

    public static void main(String[] argv) {

        Scanner read = new Scanner(System.in);
        String path = read.nextLine();
        String str = read.nextLine();

        try (InputStream in = new FileInputStream("./" + path)) {

            ArrayList<Long> list;
            list = findString(in, str);

            for (long i : list) {
                System.out.print(i + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}