import junit.framework.TestCase;

import java.util.*;

public class QuadtreeTest extends TestCase {

    public void testRemove() {
        Quadtree quadtree = new Quadtree(20.0);
        Quadtree.Point point = new Quadtree.Point(1.0, 1.0, null);
        quadtree.insert(point);
        point = new Quadtree.Point(2.0, 1.0, null);
        quadtree.insert(point);
        point = new Quadtree.Point(1.0, 2.0, null);
        quadtree.insert(point);

        quadtree.remove(1.0, 1.0);
        quadtree.remove(2.0, 1.0);

        ArrayList<Object> list = new ArrayList<>();
        quadtree.findAll(list, 0.0, 5.0, 5.0, 0.0);
        assertEquals(1, list.size());
    }

    public void testFind() {
        Quadtree quadtree = new Quadtree(20.0);
        Quadtree.Point point = new Quadtree.Point(1.0, 1.0, (String) "please");
        quadtree.insert(point);
        point = new Quadtree.Point(2.0, 1.0, (String) "concatenate");
        quadtree.insert(point);
        point = new Quadtree.Point(1.0, 2.0, (String) "me");
        quadtree.insert(point);

        Object obj = quadtree.find(1.0, 1.0);
        assertEquals(obj, "please");

        int flag = 0;
        try {
            quadtree.find(3.0, 3.0);
        } catch (NoSuchElementException e) {
            System.out.println("Error");
            flag = 1;
        }
        assertEquals(1, flag);
    }

    public void testFindAll() {
        Quadtree quadtree = new Quadtree(20.0);
        Quadtree.Point point = new Quadtree.Point(1.0, 1.0, (Integer) 0);
        quadtree.insert(point);
        point = new Quadtree.Point(2.0, 1.0, (Integer) 1);
        quadtree.insert(point);
        point = new Quadtree.Point(1.0, 2.0, (Integer) 2);
        quadtree.insert(point);

        ArrayList<Object> list = new ArrayList<>();
        quadtree.findAll(list, 0.0, 5.0, 5.0, 0.0);
        assertEquals(3, list.size());
    }
}