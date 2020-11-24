import java.util.ArrayList;

public class RecordBook {
    private final String studentName;
    private final int groupID;
    private final ResultTable resultTable;

    // запись в книжке
    // предмет + оценка или зачет/незачет
    private static class Record {
        private final String subjectName;

        // 2-5, если это оценка
        // 0/1, если это зачет/незачет
        private final int result;

        Record(String subjectName, String result) throws IllegalArgumentException {
            this.subjectName = subjectName;
            if (result.equals("зачет") || result.equals("незачет")) {
                if (result.equals("зачет")) {
                    this.result = 1;
                } else {
                    this.result = 0;
                }
            } else {
                switch (result) {
                    case "неудовлетворительно":
                        this.result = 2;
                        break;
                    case "удовлетворительно":
                        this.result = 3;
                        break;
                    case "хорошо":
                        this.result = 4;
                        break;
                    case "отлично":
                        this.result = 5;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        int getResult() {
            return this.result;
        }

        String getSubject() {
            return this.subjectName;
        }
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
        Record newResult = new Record(subject, result);
        resultTable.addRecord(newResult);
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
            int result = resultTable.getRecord(lastSemester, i).getResult();
            if (result == 3 && (scholarship == Scholarship.HIGH || scholarship == Scholarship.MIDDLE)) {
                scholarship = Scholarship.LOW;
            } else if (result == 4 && scholarship == Scholarship.HIGH) {
                scholarship = Scholarship.MIDDLE;
            } else if (result == 0 || result == 2) {
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
                int res = resultTable.getRecord(i, j).result;
                if (res >= 2) {
                    sum += res;
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
                int res = resultTable.getRecord(i, j).result;
                if (res >= 2) {
                    countAll++;
                }
                if (res == 0 || res == 3 || res == 2) {
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

    private String resultToString(int res) {
        switch (res) {
            case 0:
                return "незачет";
            case 1:
                return "зачет";
            case 2:
                return "неудовлетворительно";
            case 3:
                return "удовлетворительно";
            case 4:
                return "хорошо";
            case 5:
                return "отлично";
        }
        return null;
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
                System.out.println(record.getSubject() + " -> " + resultToString(record.getResult()));
            }
            System.out.println("------------------------");
        }
        System.out.println("Average result: " + getAverageResult());
        System.out.println("Scholarship: " + getScholarship());
        System.out.println("Is able to have a red diploma: " + hasRedDiploma());
    }
}
