import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

/*
 * Версия с тредами
 */

public class PrimeSearcher<T> {
    private final int nthreads;

    PrimeSearcher(int nthreads) {
        this.nthreads = nthreads;
    }

    public boolean test(Iterable<T> coll, Predicate<T> pred) {
        EndFlag flag = new EndFlag();
        ArrayList<PrimeThread<T>> threadList = new ArrayList<>();
        for (int i = 0; i < nthreads; i++) {
            Iterator<T> iter = coll.iterator();
            for (int j = 0; j < i; j++) {
                iter.next();
            }
            PrimeThread<T> thread = new PrimeThread<>(iter, pred, nthreads, flag);
            threadList.add(thread);
            thread.start();
        }
        for (int i = 0; i < nthreads; i++) {
            try {
                threadList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return flag.get();
    }
}

class EndFlag {
    private boolean flag;

    EndFlag() {
        flag = false;
    }

    public synchronized boolean get() {
        return flag;
    }

    public synchronized void set(boolean value) {
        flag = value;
    }
}

class PrimeThread<T> extends Thread {
    private final Iterator<T> iter;
    private final Predicate<T> pred;
    private final int nthreads;
    private final EndFlag flag;

    @Override
    public void run() {
        Iterator<T> iterator = nIterator(iter, nthreads);
        while (iterator.hasNext()) {
            T obj = iterator.next();
            if (pred.test(obj)) {
                flag.set(true);
                return;
            }
            if (flag.get()) {
                return;
            }
        }
    }

    PrimeThread(Iterator<T> iterator, Predicate<T> predicate, int nthreads, EndFlag flag) {
        iter = iterator;
        pred = predicate;
        this.nthreads = nthreads;
        this.flag = flag;
    }

    private Iterator<T> nIterator(Iterator<T> old, int N) {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                while (old.hasNext()) {
                    if (i % N == 0) {
                        return true;
                    }
                    i++;
                    old.next();
                }
                return false;
            }

            @Override
            public T next() {
                i++;
                return old.next();
            }

        };
    }

}
