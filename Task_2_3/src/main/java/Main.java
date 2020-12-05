
public class Main {

    public static void main(String [] args) {
        Quadtree quadtree = new Quadtree(20.0);

        for (int i = 0; i < 9; i++) {
            double x = -1.1 + i;
            double y = -1.1 + i;
            Quadtree.Point point = new Quadtree.Point(x, y, (Integer) i);
            quadtree.insert(point);
        }
        Quadtree.Point point = new Quadtree.Point(1.5, 6.3, 10);
        quadtree.insert(point);

        System.out.println("***");
        /*for (Object i: quadtree) {
            System.out.println(i);
        }*/

        drawCircle(0, 0, 1000.0, 0.01);
    }

    public static void drawCircle(double center_x, double center_y, double r, double tolerance) {
        if (tolerance > r) {
            throw new IllegalArgumentException();
        }

        Quadtree quadtree = new Quadtree(r*3);
        Quadtree.Point point = new Quadtree.Point(center_x - r, center_y, null);
        quadtree.insert(point);
        point = new Quadtree.Point(center_x + r, center_y, null);
        quadtree.insert(point);
        for (double x = center_x - r + tolerance; x < center_x + r - tolerance; x += tolerance) {
            double y = center_y  + Math.sqrt(r*r - (x-center_x)*(x-center_x));
            Quadtree.Point point1 = new Quadtree.Point(x, y, null);
            quadtree.insert(point1);
            y *= -1.0;
            Quadtree.Point point2 = new Quadtree.Point(x, y, null);
            quadtree.insert(point2);
        }
    }
}
