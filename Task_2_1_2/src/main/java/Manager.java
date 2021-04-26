public class Manager extends Thread {

    private final Mailbox order_queue;
    private final Mailbox warehouse;

    private final int orders_number;
    private final EndFlag flag;

    Manager(int orders_number, EndFlag flag, Mailbox order_queue, Mailbox warehouse) {
        this.order_queue = order_queue;
        this.orders_number = orders_number;
        this.flag = flag;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {

        int order = 0;

        while (true) {

            // если мы выдали нужное количество заказов,
            // то сообщаем работникам, что смена завершена и сами завершаем работу
            if (order >= orders_number && warehouse.isEmpty() && order_queue.isEmpty()) {
                flag.set(true);
                System.out.println("Менеджер сообщил об окончании смены");
                return;
            }

            if (order >= orders_number) {
                continue;
            }

            // иначе выдаем новый заказ в очередь
            synchronized (order_queue) {
                order_queue.checkAndAddMessage(order);
                System.out.format("Менеджер добавил заказ № %d в очередь\n", order);
            }
            order++;
        }
    }
}
