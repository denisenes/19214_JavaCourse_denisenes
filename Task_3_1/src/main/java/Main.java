public class Main {

    public static void main(String [] args) {

        /*
         * Задания из условия лабы
         */

        //1) Какой день недели будет через 1024 дня?
        MyGregorianCalendar calendar1 = new MyGregorianCalendar(2020, 11, 14, 23, 0, 20 );
        calendar1.addDays(1024);
        System.out.print("1) " + calendar1.dayOfWeekToString() + "\n");

        //2) Сколько лет, месяцев и дней назад был день победы 9 мая 1945 года?
        MyGregorianCalendar calendar2 = new MyGregorianCalendar(2020, 11, 14 );
        Date dayOfVictory = new Date(1945, 5, 9);
        Date distance = calendar2.distanceDate(dayOfVictory);
        System.out.print("2) " + distance.getYear() + " лет " + distance.getMonth() + " месяцев " + distance.getDayOfMonth() + " дней\n");

        //3) В какой день недели вы родились?
        MyGregorianCalendar calendar3 = new MyGregorianCalendar(2001, 11, 17, 0, 0,0);
        System.out.println("3) " + calendar3.dayOfWeekToString());

        //4) Какой месяц будет через 17 недель?
        calendar2.addDays(7 * 17);
        System.out.println("4) " + calendar2.getDate().getMonth());

        calendar2.addDays(7 * -17);

        //5) Сколько дней до нового года?
        Date newYear = new Date(2021, 1, 1);
        int days = calendar2.distanceInDays(newYear);
        System.out.println("5) " + days + " дней до нового года");

        //6) Ближайшая пятница 13-го числа месяца?
        MyGregorianCalendar calendar = new MyGregorianCalendar(2020, 12, 13);
        while (calendar.getDayOfWeek() != MyGregorianCalendar.DayOfWeek.FRIDAY) {
            calendar.addMonths(1);
        }
        Date date = calendar.getDate();
        System.out.println("6) " + date.getDayOfMonth() + "." + date.getMonth() + "." + date.getYear());
    }
}
