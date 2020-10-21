import java.util.*;
import java.util.stream.Stream;

public class PriorityQueue<K, T> implements Iterable<T> {
    private final TreeMap<K, T> map;

    public Iterator<T> iterator() {

        return new Iterator<T>() {

            private K currentKey = map.firstKey();

            @Override
            public boolean hasNext() {
                return currentKey != null;
            }

            @Override
            public T next() {
                T val = map.get(currentKey);
                currentKey = map.higherKey(currentKey);
                return val;
            }
        };
    }


    PriorityQueue() {
        map = new TreeMap<K, T>();
    }

    /**
     * добавляем элемент в очередь с приоритетами
     * @param key - ключ   (очень информативно)
     * @param value - значение
     */
    public void insert(K key, T value) {
        map.put(key, value);
    }

    /**
     * @return val: элемент с минимальным ключом
     */
    public T extractMin() {
        T val = map.get(map.firstKey());
        map.remove(map.firstKey());
        return val;
    }

    /**
     * Реализация Stream API
     * @return объект Stream<T>
     */
    // немного непонятно, что требуется. Хз, приемлемый ли это вариант реализации...
    public Stream<T> stream() {
        Collection<T> collection = map.values();
        return collection.stream();
    }

}
