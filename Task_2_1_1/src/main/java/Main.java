import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Main {

    //TODO выводить время

    public static void main(String [] args) {

        // тестовая коллекция
        ArrayList<Integer> ints = new ArrayList<>();
        File data = new File("data.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(data))){
            String text;
            while ((text = reader.readLine()) != null) {
                ints.add(Integer.parseInt(text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        IsPrimeNum pred = new IsPrimeNum();
        long time;
        long duration;

        //Linear version
        time = System.nanoTime();
        LinearPrimeSearcher<Integer> searcher = new LinearPrimeSearcher<>(ints.iterator(), pred);
        boolean result = searcher.test();
        System.out.println(result);
        duration = (System.nanoTime() - time) / 1000000;
        System.out.println("Duration: " + duration);
        System.out.println("==========================");

        //Version with threads
        time = System.nanoTime();
        PrimeSearcher<Integer> searcher1 = new PrimeSearcher<>(10);
        boolean result1 = searcher1.test(ints, pred);
        System.out.println(result1);
        duration = (System.nanoTime() - time) / 1000000;
        System.out.println("Duration: " + duration);
        System.out.println("==========================");

        //Version with ParallelStream
        time = System.nanoTime();
        PSPrimeSearcher<Integer> searcher2 = new PSPrimeSearcher<>(ints.parallelStream(), pred);
        boolean result2 = searcher2.test();
        System.out.println(result2);
        duration = (System.nanoTime() - time) / 1000000;
        System.out.println("Duration: " + duration);
    }
}

class IsPrimeNum implements Predicate<Integer> {

    @Override
    public boolean test(Integer number) {
        if (number == 0 || number == 1) {
            return true;
        }
        return IntStream.rangeClosed(2, (int) Math.sqrt(number)).anyMatch(i -> number % i == 0);
    }
}
