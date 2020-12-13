import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import picocli.CommandLine;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

@CommandLine.Command(name = "notebook")
public class Notebook implements Runnable {

    private ArrayList<Note> records;

    private static class Note {
        private final String title;
        private final String text;
        private final String date;

        private String getCurrentDate() {
            Calendar calendar = new GregorianCalendar();
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String hour = String.valueOf(calendar.get(Calendar.HOUR));
            String minute = String.valueOf(calendar.get(Calendar.MINUTE));
            String second = String.valueOf(calendar.get(Calendar.SECOND));
            return day + "." + month + "." + year + " " + hour + ":" + minute + ":" + second;
        }

        public Note(String title, String text) {
            this.title = title;
            this.text = text;
            this.date = getCurrentDate();
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getDate() {
            return date;
        }
    }

    Notebook() {
        records = new ArrayList<>();
    }

    @CommandLine.Option(names = "-add", arity = "2")
    private String[] add_args = {null, null};

    /**
     * "-add" option. add new note
     */
    public void add() {
        if (records.stream().anyMatch(note -> note.getTitle().equals(add_args[0]))) {
            throw new IllegalArgumentException();
        }
        records.add(new Note(add_args[0], add_args[1]));
    }

    @CommandLine.Option(names = {"-rm", "--remove"}, arity = "1")
    private String remove_arg;

    /**
     * "-rm --remove" options. remove note with given title
     */
    public void remove() {
        if (!records.removeIf(note -> note.getTitle().equals(remove_arg))) {
            throw new IllegalArgumentException();
        }
    }

    @CommandLine.Option(names = "--show_all")
    private boolean showRequested = false;

    /**
     * "--show_all" option. show all notes
     */
    public void showAll() {
        for (Note note : records) {
            System.out.println("==========");
            System.out.println(note.getDate());
            System.out.println(note.getTitle());
            System.out.println(note.getText());
        }
        System.out.println("==========\n");
    }

    private int compareDates(String date1, String date2) {
        String [] ints1 = date1.split("[ :.]");
        String [] ints2 = date2.split("[ :.]");
        for (int i = 2; i >= 0; i--) {
            if (Integer.parseInt(ints1[i]) > Integer.parseInt(ints2[i])) {
                return 1;
            }
            if (Integer.parseInt(ints1[i]) < Integer.parseInt(ints2[i])) {
                return -1;
            }
        }
        for (int i = 3; i < 6; i++) {
            if (Integer.parseInt(ints1[i]) > Integer.parseInt(ints2[i])) {
                return 1;
            }
            if (Integer.parseInt(ints1[i]) < Integer.parseInt(ints2[i])) {
                return -1;
            }
        }
        return 0;
    }

    @CommandLine.Option(names = "-show", arity = "2")
    private String [] show_args = {null, null};

    /**
     * "-show" option. show all such notes that have dates in [arg[0], arg[1]] interval
     */
    public void showInRange() {
        if (compareDates(show_args[0], show_args[1]) == 1) {
            throw new IllegalArgumentException();
        }
        for (Note n : records) {
            if (compareDates(n.getDate(), show_args[0]) >= 0 && compareDates(n.getDate(), show_args[1]) <= 0) {
                System.out.println("==========");
                System.out.println(n.getDate());
                System.out.println(n.getTitle());
                System.out.println(n.getText());
            }
        }
        System.out.println("==========\n");
    }

    /**
     * save data in file
     */
    public void save() {
        String data = new Gson().toJson(records);
        try (FileWriter writer = new FileWriter("data.json", false)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * load data from file
     * @return - success if true, fail if false
     */
    public boolean load() {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get("data.json")));
            Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
            records = new Gson().fromJson(jsonData, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return records != null;
    }

    @Override
    /**
     * determine what option is given and than call required method
     */
    public void run() {
        load();
        if (add_args[0] != null) {
            add();
        } else if (remove_arg != null) {
            remove();
        } else if (showRequested) {
            showAll();
        } else if (show_args[0] != null) {
            showInRange();
        }
        save();
    }
}
