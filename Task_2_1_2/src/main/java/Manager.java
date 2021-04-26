public class Manager extends Thread {

    private final Mailbox order_queue;
    private final Mailbox warehouse;

    private final int orders_number;
    private final EndFlag flag;

    private final int bN;
    private final int cN;

    Manager(int orders_number, int bN, int cN, EndFlag flag, Mailbox order_queue, Mailbox warehouse) {
        this.order_queue = order_queue;
        this.orders_number = orders_number;
        this.flag = flag;
        this.warehouse = warehouse;
        this.bN = bN;
        this.cN = cN;
    }

    @Override
    public void run() {

        int order = 0;

        while (true) {

            // если мы выдали нужное количество заказов,
            // то дожидаемся, когда все потоки уснут, а затем завершаем их
            if (order >= orders_number && warehouse.isEmpty() && order_queue.isEmpty()) {
                while (true) {
                    // если все треды
                    if (order_queue.getWaitCounter() == bN && warehouse.getWaitCounter() == cN) {
                        System.out.println("Менеджер сообщил об окончании смены");
                        flag.set(true);
                        synchronized (order_queue) {
                            order_queue.notifyAll();
                        }
                        synchronized (warehouse) {
                            warehouse.notifyAll();
                        }
                        return;
                    }
                }
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
