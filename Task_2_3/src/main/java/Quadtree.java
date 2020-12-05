import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Quadtree implements Iterable<Object> {

    public Iterator<Object> iterator() {
        double x1 = box.center_x - box.halfdim_x;
        double y1 = box.center_y + box.halfdim_y;
        double x2 = box.center_x + box.halfdim_x;
        double y2 = box.center_y - box.halfdim_y;

        ArrayList<Object> list = new ArrayList<>();
        findAll(list, x1, y1, x2, y2);

        return list.stream().iterator();
    }

    private int count = 1; // tree ID (for debug)

    public static class Point {
        private final double x;
        private final double y;
        private final Object val;

        Point(double x, double y, Object val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        public Object getValue() {
            return val;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    private static class BoundingBox {
        public double center_x;
        public double center_y;

        public double halfdim_x;
        public double halfdim_y;

        BoundingBox(double center_x, double center_y, double halfdim_x, double halfdim_y) {
            this.center_x = center_x;
            this.center_y = center_y;

            this.halfdim_x = halfdim_x;
            this.halfdim_y = halfdim_y;
        }

        /**
         * принадлежит ли точка боксу
         * @param p - ссылка на точку
         * @return - тру, если да, фолс, если нет
         */
        boolean containsPoint(Point p) {
            double x_norm = Math.abs(p.getX() - center_x);
            double y_norm = Math.abs(p.getY() - center_y);

            return !(x_norm > halfdim_x) && !(y_norm > halfdim_y);
        }

        /**
         * проверяет, пересекается ли бокс с прямоугольником
         * @param x11  \
         * @param x12   \
         * @param y11   /   координаты прямоугольника
         * @param y12  /
         * @return - интуитивно понятно
         */
        boolean checkCollision(double x11, double x12, double y11, double y12) {
            //get corners
            double x21 = center_x - halfdim_x;
            double y21 = center_y + halfdim_y;
            double x22 = center_x + halfdim_x;
            double y22 = center_y - halfdim_y;

            return !(x11 < x22 && x12 > x21 && y11 < y22 && y12 > y21);
        }
    }

    // Point in this tree
    // box - boundary of this tree
    private Point point;
    private BoundingBox box;

    //Parent
    private Quadtree parent;

    //Children
    //"One generation plants the trees, and another gets the shade."
    private Quadtree northWest;
    private Quadtree northEast;
    private Quadtree southWest;
    private Quadtree southEast;

    //Let's create an new tree (this constructor is not for children)
    public Quadtree(double range) {
        parent = null; // for root
        point = null;
        box = new BoundingBox(0.0, 0.0, range, range);
        northWest = null;
        northEast = null;
        southWest = null;
        southEast = null;
    }

    private Quadtree(double center_x, double center_y, double dim_x, double dim_y, Quadtree parent, int c) {
        this.parent = parent;
        point = null;
        box = new BoundingBox(center_x, center_y, dim_x, dim_y);
        northWest = null;
        northEast = null;
        southWest = null;
        southEast = null;

        count = c;
        //System.out.println(c);
    }

    private void subdivide() {
        double dimx = box.halfdim_x / 2;
        double dimy = box.halfdim_y / 2;

        northWest = new Quadtree(box.center_x - dimx, box.center_y + dimy, dimx, dimy, this, count + 1);
        northEast = new Quadtree(box.center_x + dimx, box.center_y + dimy, dimx, dimy, this, count + 2);
        southWest = new Quadtree(box.center_x - dimx, box.center_y - dimy, dimx, dimy, this, count + 3);
        southEast = new Quadtree(box.center_x + dimx, box.center_y - dimy, dimx, dimy, this, count + 4);
    }

    /**
     * вставляем точку в квадродерево
     * @param p - точка
     * @return - да, если успешно, нет, если не успешно (такое в теории невозможно)
     */
    boolean insert(Point p) {
        if (!box.containsPoint(p)) {
            return false;
        }

        // able to insert
        if (point == null && northWest == null) {
            //System.out.println("Точку " + p.getX() + "," + p.getY() + " кладу в лист с параметрами " + box.center_x + " " + box.center_y + " | " + box.halfdim_x);
            point = p;
            return true;
        }

        //if we aren't able to insert the point then let's try to insert into child

        //let's check if we have children
        if (northWest == null) {
            subdivide();

            if (point != null) {
                if (d_eq(point.getX(), p.getX()) == 0 && d_eq(point.getY(), p.getX()) == 0) {
                    throw new IllegalArgumentException();
                }
                northWest.insert(point);
                northEast.insert(point);
                southWest.insert(point);
                southEast.insert(point);
                point = null;
            }
        }

        if (northWest.insert(p)) {
            //System.out.println("Точку " + p.getX() + "," + p.getY() + " кладу на СЗ");
            return true;
        }
        if (northEast.insert(p)) {
            //System.out.println("Точку " + p.getX() + "," + p.getY() + " кладу на СВ");
            return true;
        }
        if (southWest.insert(p)) {
            //System.out.println("Точку " + p.getX() + "," + p.getY() + " кладу на ЮЗ");
            return true;
        }
        if (southEast.insert(p)) {
            //System.out.println("Точку " + p.getX() + "," + p.getY() + " кладу на ЮВ");
            return true;
        }

        //that's impossible!!!
        return false;
    }

    private int d_eq(double a, double b) {
        double EPS = 0.0000000000001;
        if (Math.abs(a - b) < EPS) {
            return 0;
        }
        return 1;
    }

    // let's ask the parent to kill his children if all of them haven't any point inside
    private void tryToKillYourChildren() {
        Point p1 = parent.northWest.point;
        Point p2 = parent.northEast.point;
        Point p3 = parent.southWest.point;
        Point p4 = parent.southEast.point;

        if (p1 == null && p2 == null && p3 == null && p4 == null) {
            parent.northWest = null;
            parent.northEast = null;
            parent.southWest = null;
            parent.southEast = null;

            if (parent != null) {
                parent.tryToKillYourChildren();
            }
        }
    }

    /**
     * удаляем элементы из дерева
     * @param x \     координаты точки, которую нужно удалить
     * @param y /
     */
    public void remove(double x, double y) {
        if (northWest == null) {
            if (d_eq(point.getX(), x) == 0 && d_eq(point.getY(), y) == 0) {
                //System.out.println("Found!!!");
                point = null;
                tryToKillYourChildren();
                return;
            }
        }

        double x_norm = x - box.center_x;
        double y_norm = y - box.center_y;

        if (x_norm >= 0 && y_norm >= 0) {
            northEast.remove(x, y);
            return;
        }
        if (x_norm < 0 && y_norm >= 0) {
            northWest.remove(x, y);
            return;
        }
        if (x_norm >= 0 && y_norm < 0) {
            southEast.remove(x, y);
            return;
        }
        if (x_norm < 0 && y_norm < 0) {
            southWest.remove(x, y);
            return;
        }
        throw new NoSuchElementException();
    }

    /**
     * найти элемент с данными координатами в дерев
     * @param x \   координаты нужной точки
     * @param y /
     * @return - объект, который
     * @throws NoSuchElementException - если ничего не найдено
     */
    public Object find(double x, double y) throws NoSuchElementException {
        if (northWest == null) {
            if (point != null && d_eq(point.getX(), x) == 0 && d_eq(point.getY(), y) == 0) {
                return point.getValue();
            }
        } else {
            double x_norm = x - box.center_x;
            double y_norm = y - box.center_y;

            if (x_norm >= 0 && y_norm >= 0) {
                return northEast.find(x, y);
            }
            if (x_norm < 0 && y_norm >= 0) {
                return northWest.find(x, y);
            }
            if (x_norm >= 0 && y_norm < 0) {
                return southEast.find(x, y);
            }
            if (x_norm < 0 && y_norm < 0) {
                return southWest.find(x, y);
            }
        }
        throw new NoSuchElementException();
    }

    private boolean containsPoint(double x1, double y1, double x2, double y2, Point p) {
        return x1 <= p.getX() && y1 >= p.getY() && x2 >= p.getX() && y2 <= p.getY();
    }

    /**
     * найти все точки, лежащие в данном прямоугольнике
     * @param list - сюда складываем найденные точки
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void findAll(ArrayList<Object> list, double x1, double y1, double x2, double y2) {
        if (northWest == null) {
            if (point != null && containsPoint(x1, y1, x2, y2, point)) {
                list.add(point.getValue());
            }
        } else {
            if (northWest.box.checkCollision(x1, x2, y1, y2)) {
                northWest.findAll(list, x1, y1, x2, y2);
            }
            if (northEast.box.checkCollision(x1, x2, y1, y2)) {
                northEast.findAll(list, x1, y1, x2, y2);
            }
            if (southWest.box.checkCollision(x1, x2, y1, y2)) {
                southWest.findAll(list, x1, y1, x2, y2);
            }
            if (southEast.box.checkCollision(x1, x2, y1, y2)) {
                southEast.findAll(list, x1, y1, x2, y2);
            }
        }
    }

    /**
     * просто выводит координаты всех точек в дереве
     */
    public void treeTrace() {
        if (northWest == null) {
            if (point != null) {
                System.out.println(point.getX() + ", " + point.getY());
            }
        } else {
            northWest.treeTrace();
            northEast.treeTrace();
            southWest.treeTrace();
            southEast.treeTrace();
        }
    }

    //             /\__/\    _________________
    //            | X   X|  |    я устал       \
    //           /\'= ~ =/  <   (деба)жить :(  |
    //          /       |    \________________/
    //         /    \   |
    //      __|     | | |
    //    / __   ___| | |
    //    \ (  \____\_)_)
    //     ---

}