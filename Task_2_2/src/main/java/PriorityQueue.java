import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PriorityQueue<K extends Comparable<K>, V> implements Iterable<V> {
    private Pair<K, V>[] container;
    private int counter;

    public Iterator<V> iterator() {

        return new Iterator<V>() {

            private int curID = 0;

            @Override
            public boolean hasNext() {
                return curID < counter;
            }

            @Override
            public V next() {
                return container[curID++].getVal();
            }
        };

    }

    Stream<V> stream() {
        return StreamSupport.stream(new PriorityQueueSpliterator(), false);
    }

    public class PriorityQueueSpliterator implements Spliterator<V> {
        private int firstPosition, lastPosition;

        //первичный конструктор сплитератора
        public PriorityQueueSpliterator() {
            firstPosition = -1;
            lastPosition = counter - 1;
        }

        //внутренний конструктор сплитератора
        public PriorityQueueSpliterator(int f, int l) {
            firstPosition = f;
            lastPosition = l;
        }

        //проверяем есть ли следующий элемент и если есть, переходим к нему
        @Override
        public boolean tryAdvance(Consumer<? super V> action) {
            if (firstPosition <= lastPosition) {
                firstPosition++;
                action.accept(container[firstPosition].getVal());
                return true;
            }
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super V> action) {
            for (;firstPosition <= lastPosition; firstPosition++) {
                action.accept(container[firstPosition].getVal());
            }
        }

        //пытаемся разбить контейнер на 2 части и иттерироваться по ним параллельно
        @Override
        public Spliterator<V> trySplit() {
            int half = (lastPosition - firstPosition) / 2;
            if (half<=1) {
                //Not enough data to split
                return null;
            }
            int f = firstPosition;
            int l = firstPosition + half;

            firstPosition = firstPosition + half +1;

            return new PriorityQueueSpliterator(f, l);
        }

        //узнать диапазон итерирования
        @Override
        public long estimateSize() {
            return lastPosition - firstPosition;
        }

        @Override
        public long getExactSizeIfKnown() {
            return estimateSize();
        }

        @Override
        public int characteristics() {
            return IMMUTABLE | SIZED | SUBSIZED;
        }
    }

    //собственный класс пары ключ-значение
    private static class Pair<K extends Comparable<K> , V> {
        private final K key;
        private final V val;

        Pair(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }
    }

    PriorityQueue() {
        container = new Pair[1];
        counter = 0;
    }

    private void swap(int i, int j) {
        Pair temp = container[i];
        container[i] = container[j];
        container[j] = temp;
    }

    private void siftDown(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int smallest = i;
        if (left < counter && container[left].getKey().compareTo(container[smallest].getKey()) < 0) {
            smallest = left;
        }
        if (right < counter && container[right].getKey().compareTo(container[smallest].getKey()) < 0) {
            smallest = right;
        }
        if (smallest != i) {
            swap(i, smallest);
            siftDown(smallest);
        }
    }

    private void siftUp(int i) {
        while (container[i].getKey().compareTo(container[(i-1) / 2].getKey()) < 0) {
            swap(i, (i - 1) / 2);
            i = (i -1) / 2;
        }
    }

    private void realloc() {
        Pair[] newPairArray = new Pair[container.length * 2];
        System.arraycopy(container, 0, newPairArray, 0, container.length);
        container = newPairArray;

    }

    /**
     * вставить новый элемент в очередь
     * @param key - ключ, по которому будет вычисляться положение пары в очереди
     * @param val - значение элемента в очереди
     */
    public void insert(K key, V val) {
        if (counter == container.length) {
            realloc();
        }
        counter++;

        Pair<K, V> newPair = new Pair<>(key, val);
        container[counter - 1] = newPair;
        siftUp(counter - 1);
    }

    /**
     * вытаскиваем из очереди элемент с минимальным ключом,
     * а затем восстанавливаем порядок в куче
     * @return - элемент с минимальным ключом
     * @throws NoSuchElementException - выкидываем этот exception, если очередь была пуста, а мы внезапно решили что-нибудь из нее взять
     */
    public V extractMin() throws NoSuchElementException {
        if (counter == 0) {
            throw new NoSuchElementException();
        } else {
            Pair<K, V> min = container[0];
            container[0] = container[counter - 1];
            counter--;
            siftDown(0);
            return min.getVal();
        }
    }

}
