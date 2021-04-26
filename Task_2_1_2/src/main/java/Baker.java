public class Baker extends Thread {



    //------параметры пекаря------
    private final int id;

    private final Mailbox warehouse;
    private final Mailbox order_queue;
    private final EndFlag flag;

    Baker(Mailbox warehouse, Mailbox order_queue, EndFlag flag, int id) {
        this.warehouse = warehouse;
        this.order_queue = order_queue;
        this.flag = flag;
        this.id = id;
    }

    @Override
    public void run() {

        while (true) {

            // если поток выдачи заказов сообщил об окончании смены
            // и буфер заказов пуст, то завершаем исполнение потока
            if (flag.get()) {
                return;
            }

            // 1) взять заказ из очереди заказов
            Integer message;
            synchronized (order_queue) {
                message = order_queue.checkAndGetMessage();
                System.out.println("Пекарь (id = " + id + ") взял заказ № " + message);
            }

            // 2) пекарь делает вид, что он работает
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 3) типа сделали пиццу, пытаемся отправить ее на склад
            synchronized (warehouse) {
                warehouse.checkAndAddMessage(message);
                System.out.println("Пекарь (id = " + id + ") отдал заказ № " + message);
            }
        }
    }

}
