import java.time.DateTimeException;

public class Date {
    /* поля тра ля ля */
    private int year;
    private int month;
    private int dayOfMonth;

    private int hourOfDay;
    private int minute;
    private int second;

    private boolean isLeap(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else return year % 4 == 0;
    }

    private void checkCorrectness(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) throws DateTimeException {
        //проверяем корректность даты. если что-то введено некорректо, кидаем исключение.
        if (year < 0) {
            throw new DateTimeException("Incorrect year");
        }

        if (month < 0 || month >= 13) {
            throw new DateTimeException("Incorrect month");
        }

        if (dayOfMonth > 31 || dayOfMonth < 0) {
            throw new DateTimeException("Incorrect day");
        } else if (dayOfMonth > 30 && (month == 4 || month == 6 || month == 9 || month == 11)) {
            throw new DateTimeException("Incorrect day");
        } else if (dayOfMonth > 28 && month == 2 && !isLeap(year)) {
            throw new DateTimeException("Incorrect day");
        } else if (dayOfMonth > 29 && month == 2 && isLeap(year)) {
            throw new DateTimeException("Incorrect day");
        }
    }


    Date(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) throws DateTimeException {
        checkCorrectness(year, month, dayOfMonth, hourOfDay, minute, second);

        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
    }

    //копируем поля из другой даты
    Date(Date copyOf) {
        this.year = copyOf.getYear();
        this.month = copyOf.getMonth();
        this.dayOfMonth = copyOf.getDayOfMonth();
        this.hourOfDay = copyOf.getHourOfDay();
        this.minute = copyOf.getMinute();
        this.second = copyOf.getSecond();
    }

    Date(int year, int month, int dayOfMonth) throws DateTimeException {
        checkCorrectness(year, month, dayOfMonth, 0, 0, 0);

        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hourOfDay = 0;
        this.minute = 0;
        this.second = 0;
    }

    //кучка методов для доступа к полям
    int getYear() {
        return year;
    }

    int getMonth() {
        return month;
    }

    int getDayOfMonth() {
        return dayOfMonth;
    }

    int getHourOfDay() {
        return hourOfDay;
    }

    int getMinute() {
        return minute;
    }

    int getSecond() {
        return second;
    }

    void setYear(int year) {
        this.year = year;
    }

    void setMonth(int month) {
        this.month = month;
    }

    void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    void setMinute(int minute) {
        this.minute = minute;
    }

    void setSecond(int second) {
        this.second = second;
    }
}
