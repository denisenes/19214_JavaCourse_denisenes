import junit.framework.TestCase;

public class RecordBookTest extends TestCase {

    public void testGetScholarship() {
        RecordBook book = new RecordBook("Ivanov Ivan", 19214);

        book.addResult("Math", "отлично");
        book.addResult("Programing", "отлично");
        book.addResult("Physics", "отлично");
        assertEquals("HIGH", book.getScholarship().toString());

        book.nextSemester();
        book.addResult("Math", "хорошо");
        book.addResult("Programing", "отлично");
        book.addResult("Physics", "отлично");
        assertEquals("MIDDLE", book.getScholarship().toString());

        book.nextSemester();
        book.addResult("Math", "хорошо");
        book.addResult("Programing", "удовлетворительно");
        book.addResult("Physics", "отлично");
        assertEquals("LOW", book.getScholarship().toString());

        book.nextSemester();
        book.addResult("Math", "хорошо");
        book.addResult("Programing", "неудовлетворительно");
        book.addResult("Physics", "отлично");
        assertEquals("NO", book.getScholarship().toString());
    }

    public void testGetAverageResult() {
        RecordBook book = new RecordBook("Ivanov Ivan", 19214);

        book.addResult("Math", "отлично");
        book.addResult("Programing", "отлично");
        book.addResult("Physics", "хорошо");
        book.nextSemester();
        book.addResult("Math", "удовлетворительно");
        book.addResult("Programing", "удовлетворительно");
        book.addResult("Physics", "хорошо");
        assertEquals(4.0, book.getAverageResult());
    }

    public void testHasRedDiploma() {
        RecordBook book = new RecordBook("Ivanov Ivan", 19214);

        book.addResult("Math", "отлично");
        book.addResult("Programing", "отлично");
        book.addResult("Physics", "хорошо");
        book.nextSemester();
        book.addResult("Math", "отлично");
        book.addResult("Programing", "отлично");
        book.addResult("Physics", "отлично");
        assertTrue(book.hasRedDiploma());

        RecordBook book1 = new RecordBook("Ivanov Ivan", 19214);

        book1.addResult("Math", "отлично");
        book1.addResult("Programing", "отлично");
        book1.addResult("Physics", "хорошо");
        book1.nextSemester();
        book1.addResult("Math", "хорошо");
        book1.addResult("Programing", "отлично");
        book1.addResult("Physics", "отлично");
        assertFalse(book1.hasRedDiploma());
    }
}