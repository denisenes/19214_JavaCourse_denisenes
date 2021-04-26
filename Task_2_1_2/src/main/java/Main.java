import java.util.ArrayList;

public class Main {

    public static void main(String [] args) {
        int ordersN = 300; // количество заказов

        int bakersN = 20;
        int couriersN = 10;
        EndFlag flag = new EndFlag();

        // инициализируем склад и очередь заказов
        Mailbox warehouse = new Mailbox(10, flag);
        Mailbox orderQueue = new Mailbox(100, flag);

        // инициализируем работников
        ArrayList<Thread> threadList = new ArrayList<>();

        Manager manager = new Manager(ordersN, bakersN, couriersN, flag, orderQueue, warehouse);
        threadList.add(manager);
        manager.start();

        for (int i = 0; i < bakersN; i++) {
            Baker thread = new Baker(warehouse, orderQueue, flag, i);
            threadList.add(thread);
            thread.start();
        }

        for (int i = 0; i < couriersN; i++) {
            Courier thread = new Courier(warehouse, flag, 3, i);
            threadList.add(thread);
            thread.start();
        }

        // ожидаем выполнения всех тредов
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Симуляция успешно завершена");
    }
}
