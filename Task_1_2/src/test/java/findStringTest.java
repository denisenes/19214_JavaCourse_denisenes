import org.junit.Assert;
import org.junit.Test;

import javax.swing.event.InternalFrameEvent;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class findStringTest {

    private void assertListsEquals(ArrayList<Integer> al, ArrayList<Integer> foo) {
        for (int i = 0; i < al.size(); i++) {
            assertEquals(al.get(i), foo.get(i));
        }
    }

    @Test
    public void findString() {
        ArrayList<Integer> test1 = new ArrayList<>();
        test1.add(0);
        assertListsEquals(test1, Main.findString("input1.txt", "привет"));
    }
}