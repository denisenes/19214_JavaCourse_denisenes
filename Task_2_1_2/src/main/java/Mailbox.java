import java.util.ArrayDeque;

public class Mailbox {

    // максимальный размер буффера
    private final int size;

    private final ArrayDeque<Integer> buffer = new ArrayDeque<>();

    Mailbox(int size) {
        this.size = size;
    }

    // пытаемся добавить сообщение в буфер
    // если он переполнен, кидаем исключение
    public void checkAndAddMessage(Integer mess) {
        synchronized (this) {
            while (size - buffer.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            buffer.addFirst(mess);
            notify();
        }
    }

    // пытаемся получить сообщение из буфера
    // если он пуст, кидаем исключение
    public Integer checkAndGetMessage() {
        synchronized (this) {
            while (buffer.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notify();
            return buffer.pollFirst();
        }
    }

    public synchronized boolean isEmpty() {
        return buffer.size() == 0;
    }

    public synchronized boolean isFilledUp() {
        return size - buffer.size() == 0;
    }
}
