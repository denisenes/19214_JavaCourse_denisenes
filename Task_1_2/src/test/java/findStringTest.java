import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class findStringTest {

    private void assertListsEquals(ArrayList<Long> al, ArrayList<Long> foo) {
        if (al.size() == foo.size()) {
            for (int i = 0; i < foo.size(); i++) {
                assertEquals(al.get(i), foo.get(i));
            }
        }
    }

    @Test
    public void findString() throws IOException {
        //-- test 1 --
        ArrayList<Long> test1 = new ArrayList<>();
        test1.add(30L);
        try (InputStream in = new FileInputStream("./input1.txt")) {
            assertListsEquals(test1, Main.findString(in, "ti"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-- test 2, test 3 --
        String str1 = "bababa s bebebe";
        ArrayList<Long> list0 = new ArrayList<>();
        list0.add(0L); list0.add(2L);
        ArrayList<Long> list1 = new ArrayList<>();
        list1.add(9L); list1.add(11L); list1.add(13L);
        InputStream in1 = new ByteArrayInputStream(str1.getBytes());
        assertListsEquals(list1, Main.findString(in1, "be"));
        assertListsEquals(list0, Main.findString(in1, "baba"));
        //-- test 4 --
        String str2 = "-пахнет мазохизмом...\n" +
                "-пахнет интересным экспириенсом...\n" +
                "-мазохисты так и говорят!!!!";
        ArrayList<Long> list3 = new ArrayList<>();
        list3.add(8L); list3.add(58L);
        InputStream in2 = new ByteArrayInputStream(str2.getBytes());
        assertListsEquals(list3, Main.findString(in2, "мазо"));
    }
}