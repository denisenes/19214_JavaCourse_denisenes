public class MyGregorianCalendar {
    private Date currentDate;
    private DayOfWeek dayOfWeek;

    enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    MyGregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        currentDate = new Date(year, month, dayOfMonth, hourOfDay, minute, second);
        this.setDayOfWeek();
    }

    MyGregorianCalendar(int year, int month, int dayOfMonth) {
        currentDate = new Date(year, month, dayOfMonth);
        this.setDayOfWeek();
    }

    private int dayOfYear(int month, int dayOfMonth, int year) {
        int day = 0;
        day += dayOfMonth;

        for (int i = 1; i < month; i++) {
            day += daysInMonth(i, isLeap(year));
        }

        return day;
    }

    private int daysInMonth(int month, boolean isLeap) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            if (isLeap) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    private boolean isLeap(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else return year % 4 == 0;
    }

    /*
     *  Epoch - время от начала эры в секундах
     */
    private long toEpoch(Date date) {
        long epoch = 0;
        epoch += date.getSecond();
        epoch += 60 * date.getMinute();
        epoch += 60 * 60 * date.getHourOfDay();
        epoch += 60 * 60 * 24 * (dayOfYear(date.getMonth(), date.getDayOfMonth(), date.getYear()) - 1);
        for (int i = 1; i < date.getYear(); i++) {
            if (isLeap(i)) {
                epoch += 366 * 24 * 60 * 60;
            } else {
                epoch += 365 * 24 * 60 * 60;
            }
        }
        return epoch;
    }

    private void fromEpoch(long Epoch, Date to) {
        int year = 1;
        int yy = 24 * 60 * 60;
        while (Epoch >= (365 + (isLeap(year) ? 1 : 0)) * yy) {
            if (isLeap(year)) {
                Epoch -= 366 * yy;
            } else {
                Epoch -= 365 * yy;
            }
            year += 1;
        }
        int month = 1;
        while (Epoch > daysInMonth(month, isLeap(year)) * yy) {
            Epoch -= daysInMonth(month, isLeap(year)) * yy;
            month += 1;
        }
        int dayOfMonth = 1;
        while (Epoch >= yy) {
            dayOfMonth++;
            Epoch -= yy;
        }
        int hourOfDay = 0;
        while (Epoch >= 60 * 60) {
            hourOfDay++;
            Epoch -= 3600;
        }
        int minute = 0;
        while (Epoch >= 60) {
            minute++;
            Epoch -= 60;
        }
        int second = (int) Epoch;

        to.setYear(year);
        to.setMonth(month);
        to.setDayOfMonth(dayOfMonth);
        to.setHourOfDay(hourOfDay);
        to.setMinute(minute);
        to.setSecond(second);
    }

    private void setDayOfWeek() {
        int days = (int) (toEpoch(currentDate) / (24 * 60 * 60));
        days = days % 7;

        switch (days) {
            case 0:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 1:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 2:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 3:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 4:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case 5:
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case 6:
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
        }
    }

    /* public zone */

    /**
     * Распечатать содержимое currentDate
     */
    public void printDate() {
        System.out.print(currentDate.getDayOfMonth() + ".");
        System.out.print(currentDate.getMonth() + ".");
        System.out.print(currentDate.getYear() + " ");
        System.out.print(currentDate.getHourOfDay() + ":");
        System.out.print(currentDate.getMinute() + ":");
        System.out.print(currentDate.getSecond() + "\n");
    }

    /**
     * добавить days дней к текущей дате
     * @param days - количество добавляемых дней
     */
    public void addDays(int days) {
        long diff = days * 24 * 60 * 60;
        long curTime = this.toEpoch(currentDate);

        curTime = curTime + diff;
        this.fromEpoch(curTime, currentDate);

        setDayOfWeek();
    }

    /**
     * Добавить month месяцев к текущей дате
     * @param month - количество добавляемых месяцев
     */
    public void addMonths(int month) {
        int d = currentDate.getDayOfMonth();
        int m = currentDate.getMonth();
        int y = currentDate.getYear();
        m += month;
        y += (m-1) / 12;
        m = (m-1) % 12 + 1;
        if (m <= 0) {
            y--;
            m = 12 + m;
        }
        int max = daysInMonth(month, isLeap(currentDate.getYear()));
        if (d > max) {
            m++;
            d = d - max;
        }
        if (m == 13) {
            m = 1;
        }
        currentDate.setMonth(m);
        currentDate.setYear(y);
        currentDate.setDayOfMonth(d);

        setDayOfWeek();
    }

    /**
     * добавить years лет к текущей дате
     * @param years - количество добавляемых лет
     */
    public void addYears(int years) {
        int y = currentDate.getYear();
        //если мы находимся 29 февраля в високосном году, но переместились в невисокосный, то надо, чтобы дата стала 1 марта
        if (isLeap(y) && !isLeap(y+years) && currentDate.getMonth() == 2 && currentDate.getDayOfMonth() == 29) {
            currentDate.setMonth(3);
            currentDate.setDayOfMonth(1);
        }
        currentDate.setYear(y + years);

        setDayOfWeek();
    }

    /**
     * разница между двумя датами в годах, месяцах и днях
     * @param secondDate - дата, с которой сравнивается текущая дата
     * @return - объект типа Date с результатом разности двух дат
     */
    public Date distanceDate(Date secondDate) {
        long sec1 = toEpoch(currentDate);
        long sec2 = toEpoch(secondDate);

        sec1 = Math.abs(sec1 - sec2);

        Date newDate = new Date(1, 1, 1, 0, 0, 0);
        fromEpoch(sec1, newDate);

        newDate.setYear(newDate.getYear() - 1);
        newDate.setMonth(newDate.getMonth() - 1);
        newDate.setDayOfMonth(newDate.getDayOfMonth() - 1);

        return newDate;
    }

    /**
     * разница между двумя датами, выраженная в днях
     * @param secondDate - дата, с которой сравнивается текущая дата
     * @return - количество дней между currentDate и secondDate
     */
    public int distanceInDays(Date secondDate) {
        long sec1 = toEpoch(currentDate);
        long sec2 = toEpoch(secondDate);

        sec1 = Math.abs(sec1 - sec2);

        return (int) (sec1 / (24 * 60 * 60));
    }

    /**
     * получить текущий день недели
     * @return - текущий день недели
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * получить текущий день недели в строковом представлении
     * @return - текущий день недели в строковом представлении
     */
    public String dayOfWeekToString() {
        switch (dayOfWeek) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
        }
        return null;
    }

    /**
     * получить текущую дату
     * @return - текущая дата
     */
    public Date getDate() {
        return new Date(currentDate);
    }
}