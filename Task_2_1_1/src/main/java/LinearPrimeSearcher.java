import java.util.Iterator;
import java.util.function.Predicate;

/*
 * Версия на одном потоке
 */

public class LinearPrimeSearcher<T> {
    private final Iterator<T> iter;
    private final Predicate<T> pred;

    LinearPrimeSearcher(Iterator<T> iterator, Predicate<T> predicate) {
        iter = iterator;
        pred = predicate;
    }

    public boolean test() {
        while (iter.hasNext()) {
            if (pred.test(iter.next())) {
                return true;
            }
        }
        return false;
    }
}
