import java.util.ArrayList;

public class RecordBook {
    private final String studentName;
    private final int groupID;
    private final ResultTable resultTable;

    // запись в книжке
    // предмет + оценка или зачет/незачет
    private enum Result {
        FAIL("незачет", 0),
        ACCEPTED("зачет", 1),
        SATISFACTORILY("удовлетворительно", 3),
        GOOD("хорошо", 4),
        EXCELLENT("отлично", 5);

        private final String text;
        private final int integer;

        Result(String result, int integer) {
            this.text = result;
            this.integer = integer;
        }

        public int getInteger() {
            return integer;
        }

        public String getText() {
            return text;
        }
    }

    private class Record {
        public Result result;
        public String subject;
    }

    // табличка с записями
    // для каждого семестра свой ArrayList
    private static class ResultTable {
        private int currentSemester;
        private ArrayList<Record>[] table;

        ResultTable() {
            currentSemester = 1;
            table = new ArrayList[8];
            for (int i = 0; i < 8; i++) {
                table[i] = new ArrayList<>();
            }
        }

        /**
         * переходим к следующему семестру
         * @throws IllegalStateException - если пытаемся перейти к следующему семестру при текущем = 8 (это максимальное значение)
         */
        public void incSem() throws IllegalStateException{
            if (currentSemester < 8) {
                currentSemester++;
            } else {
                throw new IllegalStateException();
            }
        }

        /**
         * получить количество предметов в определенном семестре
         * @param semester - семестр, в котором мы хотим узнать количество предметов
         * @return - количество
         * @throws IllegalArgumentException - если передали невалидное значение
         */
        public int getNumberOfSubjects(int semester) throws IllegalArgumentException {
            if (semester <= 8 && semester >= 1 && semester <= currentSemester) {
                return this.table[semester-1].size();
            } else {
                throw new IllegalArgumentException();
            }
        }

        /**
         * получить запись из таблицы
         * @param semester - значение нужного семестра
         * @param index - номер предмета в списке предметов текущего семестра
         * @return - нужная запись
         * @throws IllegalArgumentException - если передали невалидные значения
         */
        public Record getRecord(int semester, int index) throws IllegalArgumentException {
            if (semester <= 8 && semester >= 1 && semester <= currentSemester) {
                return this.table[semester-1].get(index);
            } else {
                throw new IllegalArgumentException();
            }
        }

        /**
         * получить текущий семестр
         * @return - текущий семестр
         */
        public int getSemesters() {
            return currentSemester;
        }

        /**
         * добавить запись в таблицу (в текущий семестр)
         * @param newRecord - запись, которую нужно добавить в таблицу
         */
        public void addRecord(Record newRecord) {
            table[currentSemester-1].add(newRecord);
        }
    }


    RecordBook(String FIO, int groupID) {
        studentName = FIO;
        this.groupID = groupID;
        resultTable = new ResultTable();
    }

    /**
     * заинкрементить в таблице текущий семестр
     * @throws IllegalStateException - если incSem кинул эксепшн, кидаем его дальше
     */
    public void nextSemester() throws IllegalStateException {
        resultTable.incSem();
    }

    /**
     * добавить результат в табличку
     * @param subject - название предмета
     * @param result - результат
     */
    public void addResult(String subject, String result) {
        Record newRecord = new Record();
        newRecord.subject = subject;
        if (result.equals("зачет") || result.equals("незачет")) {
            if (result.equals("зачет")) {
                newRecord.result = Result.ACCEPTED;
            } else {
                newRecord.result = Result.FAIL;
            }
        } else {
            switch (result) {
                case "неудовлетворительно":
                    newRecord.result = Result.FAIL;
                    break;
                case "удовлетворительно":
                    newRecord.result = Result.SATISFACTORILY;
                    break;
                case "хорошо":
                    newRecord.result = Result.GOOD;
                    break;
                case "отлично":
                    newRecord.result = Result.EXCELLENT;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        resultTable.addRecord(newRecord);
    }

    enum Scholarship {
        HIGH,
        MIDDLE,
        LOW,
        NO
    }

    /**
     * вычислить стипендию студента, исходя из его последних оценок
     * @return - значение стипендии
     */
    public Scholarship getScholarship() {
        int lastSemester = resultTable.getSemesters();
        Scholarship scholarship = Scholarship.HIGH;
        for (int i = 0; i < resultTable.getNumberOfSubjects(lastSemester); i++) {
            Record record = resultTable.getRecord(lastSemester, i);
            if (record.result == Result.SATISFACTORILY && (scholarship == Scholarship.HIGH || scholarship == Scholarship.MIDDLE)) {
                scholarship = Scholarship.LOW;
            } else if (record.result == Result.GOOD && scholarship == Scholarship.HIGH) {
                scholarship = Scholarship.MIDDLE;
            } else if (record.result == Result.FAIL) {
                scholarship = Scholarship.NO;
            }
        }

        return scholarship;
    }

    /**
     * текущий средний результат за все предметы (не считая зачеты)
     * @return - средний результат
     */
    public double getAverageResult() {
        double sum = 0;
        double count = 0;
        for (int i = 1; i <= resultTable.getSemesters(); i++) {
            for (int j = 0; j < resultTable.getNumberOfSubjects(i); j++) {
                int resint = resultTable.getRecord(i, j).result.getInteger();
                if (resint > 2) {
                    sum += resint;
                    count++;
                }
            }
        }

        return sum / count;
    }

    /**
     * может ли студент с его оценками иметь красный диплом?
     * @return - true, если может, false, если не может
     */
    public boolean hasRedDiploma() {
        int countAll = 0;
        int countExc = 0;
        boolean hasProblems = false;
        for (int i = 1; i <= resultTable.getSemesters(); i++) {
            for (int j = 0; j < resultTable.getNumberOfSubjects(i); j++) {
                int res = resultTable.getRecord(i, j).result.getInteger();
                if (res >= 2) {
                    countAll++;
                }
                if (res == 0 || res == 3) {
                    hasProblems = true;
                } else if (res == 5) {
                    countExc++;
                }
            }
        }

        if (hasProblems) {
            return false;
        } else {
            double percent = (double) countExc / (double) countAll;
            return percent > 0.75;
        }
    }

    /**
     * напечатать всю возможную информацию из книжки
     */
    public void printAllInfo() {
        System.out.println("Зачетка");
        System.out.println(studentName + " " + groupID);
        System.out.println("===========================");
        for (int i = 1; i <= resultTable.getSemesters(); i++) {
            System.out.println(i + " semester:");
            for (int j = 0; j < resultTable.getNumberOfSubjects(i); j++) {
                Record record = resultTable.getRecord(i, j);
                System.out.println(record.subject + " -> " + record.result.getText());
            }
            System.out.println("------------------------");
        }
        System.out.println("Average result: " + getAverageResult());
        System.out.println("Scholarship: " + getScholarship());
        System.out.println("Is able to have a red diploma: " + hasRedDiploma());
    }
}
