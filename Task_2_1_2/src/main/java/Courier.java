import java.util.ArrayList;

public class Courier extends Thread {

    //------параметры курьера------
    private final int id;
    private final int backpack_size; // размер рюкзака курьера

    private ArrayList<Integer> backpack;

    private final Mailbox warehouse;
    private final EndFlag flag;

    Courier(Mailbox warehouse, EndFlag flag, int b_s, int id) {
        this.warehouse = warehouse;
        this.flag = flag;
        backpack_size = b_s;
        backpack = new ArrayList<>();
        this.id = id;
    }

    @Override
    public void run() {

        while (true) {

            // 1) взять заказы со склада, закинуть все в рюкзак
            Integer message;
            synchronized (warehouse) {
                while (backpack.size() < backpack_size) {
                    message = warehouse.checkAndGetMessage();
                    // если поток выдачи заказов сообщил об окончании смены
                    // и буфер заказов пуст, то завершаем исполнение потока
                    if (flag.get()) {
                        return;
                    }
                    System.out.println("Курьер (id = " + id + ") взял заказ № " + message);
                    backpack.add(message);
                    if (warehouse.isEmpty()) {
                        break;
                    }
                }
            }

            // если мы не смогли положить что-нибудь в рюкзак, то возвращаемся в начало
            if (backpack.size() == 0) {
                continue;
            }

            // 2) курьер делает вид, что он едет к заказчику
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 3) скидываем заказы и говорим, что все OK!!!!
            for (Integer mess : backpack) {
                System.out.println("Курьер (id = " + id + ") доставил заказ № " + String.valueOf(mess));
            }
            backpack.clear();
        }
    }
}
