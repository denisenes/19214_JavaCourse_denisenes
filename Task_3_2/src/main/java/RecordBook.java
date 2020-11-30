import java.util.ArrayList;

public class RecordBook {
    private final String studentName;
    private final int groupID;
    private final ResultTable resultTable;

    private enum Results {
        FAIL,
        ACCEPTED,

        SATISFACTORILY,
        GOOD,
        EXCELLENT
    }

    // запись в книжке
    // предмет + оценка или зачет/незачет
    private static class Record {

        private final String subjectName;

        Results result;

        Record(String subjectName, String result) {
            this.subjectName = subjectName;
            if (result.equals("зачет") || result.equals("незачет")) {
                if (result.equals("зачет")) {
                    this.result = Results.ACCEPTED;
                } else {
                    this.result = Results.FAIL;
                }
            } else {
                switch (result) {
                    case "неудовлетворительно":
                        this.result = Results.FAIL;
                        break;
                    case "удовлетворительно":
                        this.result = Results.SATISFACTORILY;
                        break;
                    case "хорошо":
                        this.result = Results.GOOD;
                        break;
                    case "отлично":
                        this.result = Results.EXCELLENT;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }

        Results getResult() {
            return this.result;
        }

        int resultToInt() {
            switch (result) {
                case FAIL:
                    return 0;
                case ACCEPTED:
                    return  1;
                case SATISFACTORILY:
                    return 3;
                case GOOD:
                    return 4;
                case EXCELLENT:
                    return 5;
            }
            return 0;
        }

        private String resultToString(Results res) {
            switch (res) {
                case FAIL:
                    return "незачет";
                case ACCEPTED:
                    return "зачет";
                case SATISFACTORILY:
                    return "удовлетворительно";
                case GOOD:
                    return "хорошо";
                case EXCELLENT:
                    return "отлично";
            }
            return null;
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
            Results result = resultTable.getRecord(lastSemester, i).getResult();
            if (result == Results.SATISFACTORILY && (scholarship == Scholarship.HIGH || scholarship == Scholarship.MIDDLE)) {
                scholarship = Scholarship.LOW;
            } else if (result == Results.GOOD && scholarship == Scholarship.HIGH) {
                scholarship = Scholarship.MIDDLE;
            } else if (result == Results.FAIL) {
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
                int resint = resultTable.getRecord(i, j).resultToInt();
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
                int res = resultTable.getRecord(i, j).resultToInt();
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
                System.out.println(record.getSubject() + " -> " + record.resultToString(record.getResult()));
            }
            System.out.println("------------------------");
        }
        System.out.println("Average result: " + getAverageResult());
        System.out.println("Scholarship: " + getScholarship());
        System.out.println("Is able to have a red diploma: " + hasRedDiploma());
    }
}
