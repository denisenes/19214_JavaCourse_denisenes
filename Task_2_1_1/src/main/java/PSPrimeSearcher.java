import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Версия с Parallel Stream
 */

public class PSPrimeSearcher<T> {
    private final Stream<T> stream;
    private final Predicate<T> pred;

    PSPrimeSearcher(Stream<T> stream, Predicate<T> predicate) {
        this.stream = stream;
        pred = predicate;
    }

    public boolean test() {
        return stream.parallel().anyMatch(pred);
    }
}
