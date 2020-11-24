
public class Main {

    public static void main(String[] args) {

        RecordBook book = new RecordBook("Enes Denis Sergeevich", 19214);

        book.addResult("Алгебра и анализ", "отлично");
        book.addResult("Дискретка", "отлично");
        book.addResult("Декларативное прог", "отлично");
        book.addResult("Иностранный язык", "зачет");
        book.addResult("Императивное прог", "отлично");
        book.addResult("История", "отлично");
        book.addResult("Культура речи", "отлично");
        book.addResult("Физра", "зачет");
        book.addResult("Цифровые платформы", "зачет");
        book.nextSemester();
        book.addResult("Алгебра и анализ", "хорошо");
        book.addResult("Дискретка", "отлично");
        book.addResult("Декларативное прог", "отлично");
        book.addResult("Иностранный язык", "отлично");
        book.addResult("Императивное прог", "отлично");
        book.addResult("Физра", "зачет");
        book.addResult("Цифровые платформы", "отлично");

        book.printAllInfo();
    }

}
