import java.io.*;
import java.util.*;

public class StudentStore {
    private static final String FILENAME = "students.txt";


    public static void load() {
        File f = new File(FILENAME);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", 2);
                if (p.length == 2) {
                    DataStore.students.add(new DataStore.StudentRecord(p[0].trim(), p[1].trim()));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static boolean addStudent(String id, String name) {

        if (findById(id) != null) return false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
            bw.write(id + "," + name);
            bw.newLine();
            DataStore.students.add(new DataStore.StudentRecord(id, name));
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static DataStore.StudentRecord findById(String id) {
        for (DataStore.StudentRecord s : DataStore.students) if (s.id.equals(id)) return s;
        return null;
    }


    public static boolean verify(String id, String name) {
        DataStore.StudentRecord s = findById(id);
        return s != null && s.name.equals(name);
    }
}
