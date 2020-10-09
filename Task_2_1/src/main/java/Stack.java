import java.util.EmptyStackException;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {
    private Object[] container;
    private int counter = 0;
    private int size = 1;

    public Iterator<T> iterator() {

        return new Iterator<T>() {

            private int curID = 0;

            @Override
            public boolean hasNext() {
                if (curID < counter) {
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                return (T)container[curID++];
            }
        };

    }

    // Set start params
    Stack() {
        container = new Object[1];
        size = 1;
    }

    /**
     * Put value to stack
     * @param val - value that we want to put in stack
     */
    public void push(T val) {
        if (counter >= size) {
            resize();
        }
        container[counter] = val;
        counter++;
    }

    /**
     * Get element from stack
     * @return
     */
    public T pop() throws EmptyStackException {
        if (counter != 0) {
            counter--;
            return (T) container[counter];
        } else {
            throw new EmptyStackException();
        }
    }

    private void resize() {
        Object[] newObj = new Object[container.length * 2];
        System.arraycopy(container, 0, newObj, 0, container.length);
        container = newObj;
        size *= 2;
    }

    /**
     * Get the number of elements in stack
     * @return
     */
    public int count() {
        return counter;
    }
}
