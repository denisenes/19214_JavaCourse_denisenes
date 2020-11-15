import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyGregorianCalendarTest extends TestCase {

    public void testAddDays() {
        MyGregorianCalendar calendar = new MyGregorianCalendar(2020, 11, 14, 23, 0, 20 );
        GregorianCalendar calendar1 = new GregorianCalendar(2020, Calendar.NOVEMBER, 14, 23, 0, 20);
        calendar.addDays(100);
        calendar1.add(Calendar.DAY_OF_MONTH, 100);

        java.util.Date date1 = calendar1.getTime();
        Date date2 = calendar.getDate();

        assertEquals(date1.getYear() + 1900, date2.getYear());
        assertEquals(date1.getMonth() + 1, date2.getMonth());
        assertEquals(date1.getDate(), date2.getDayOfMonth());
        assertEquals(date1.getHours(), date2.getHourOfDay());
        assertEquals(date1.getMinutes(), date2.getMinute());
        assertEquals(date1.getSeconds(), date2.getSecond());
    }

    public void testAddMonths() {
        MyGregorianCalendar calendar = new MyGregorianCalendar(2020, 11, 14, 23, 0, 20 );
        GregorianCalendar calendar1 = new GregorianCalendar(2020, Calendar.NOVEMBER, 14, 23, 0, 20);
        calendar.addMonths(100);
        calendar1.add(Calendar.MONTH, 100);

        java.util.Date date1 = calendar1.getTime();
        Date date2 = calendar.getDate();

        assertEquals(date1.getYear() + 1900, date2.getYear());
        assertEquals(date1.getMonth() + 1, date2.getMonth());
        assertEquals(date1.getDate(), date2.getDayOfMonth());
        assertEquals(date1.getHours(), date2.getHourOfDay());
        assertEquals(date1.getMinutes(), date2.getMinute());
        assertEquals(date1.getSeconds(), date2.getSecond());
    }

    public void testAddYears() {
        MyGregorianCalendar calendar = new MyGregorianCalendar(2020, 11, 14, 23, 0, 20 );
        GregorianCalendar calendar1 = new GregorianCalendar(2020, Calendar.NOVEMBER, 14, 23, 0, 20);
        calendar.addYears(100);
        calendar1.add(Calendar.YEAR, 100);

        java.util.Date date1 = calendar1.getTime();
        Date date2 = calendar.getDate();

        assertEquals(date1.getYear() + 1900, date2.getYear());
        assertEquals(date1.getMonth() + 1, date2.getMonth());
        assertEquals(date1.getDate(), date2.getDayOfMonth());
        assertEquals(date1.getHours(), date2.getHourOfDay());
        assertEquals(date1.getMinutes(), date2.getMinute());
        assertEquals(date1.getSeconds(), date2.getSecond());
    }

    public void testDistanceDate() {
        MyGregorianCalendar calendar = new MyGregorianCalendar(2020, 11, 15, 23, 0, 20 );

        Date d1 = new Date(1971, 8, 18, 23, 0, 20);
        Date date = calendar.distanceDate(d1);

        System.out.println(date.getYear() + " " + date.getMonth() + " " + date.getDayOfMonth());
        System.out.println(date.getHourOfDay() + " " + date.getMinute() + " " + date.getSecond());

        assertEquals(date.getYear(), 49);
        assertEquals(date.getMonth(), 2);
        assertEquals(date.getDayOfMonth(), 28);
    }


    public void testDistanceInDays() {
        MyGregorianCalendar calendar = new MyGregorianCalendar(2020, 11, 15, 23, 0, 20 );

        Date d1 = new Date(1971, 8, 18, 23, 0, 20);
        int days = calendar.distanceInDays(d1);

        assertEquals(days, 17987);
    }

    public void testGetDayOfWeek() {
        MyGregorianCalendar calendar = new MyGregorianCalendar(2020, 11, 15, 23, 0, 20 );

        String day = calendar.dayOfWeekToString();
        assertEquals(day, "Sunday");

        calendar.addDays(7);
        day = calendar.dayOfWeekToString();
        assertEquals(day, "Sunday");

        calendar.addDays(1);
        day = calendar.dayOfWeekToString();
        assertEquals(day, "Monday");

        calendar.addDays(10);
        day = calendar.dayOfWeekToString();
        assertEquals(day, "Thursday");
    }
}