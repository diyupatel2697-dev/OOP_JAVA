import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentServiceTest {

    public void testSaveAndLoadData() throws Exception {
        String filename = "test_students.dat";
        StudentService service1 = new StudentService();

        service1.addStudent(1, "Diya");
        service1.addStudent(2, "Riya");
        service1.saveData(filename);

        StudentService service2 = new StudentService();
        service2.loadData(filename);

        try {
            assertEquals(service1.getStudentCount(), service2.getStudentCount());
        } finally {
            new File(filename).delete();
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    public static void main(String[] args) throws Exception {
        new StudentServiceTest().testSaveAndLoadData();
    }
}

class StudentService implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<Integer, String> students = new HashMap<>();

    public void addStudent(int id, String name) {
        students.put(id, name);
    }

    public int getStudentCount() {
        return students.size();
    }

    public void saveData(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(students);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData(String filename) throws IOException, ClassNotFoundException {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof Map<?, ?>) {
                students.clear();
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                    if (entry.getKey() instanceof Integer && entry.getValue() instanceof String) {
                        students.put((Integer) entry.getKey(), (String) entry.getValue());
                    }
                }
            }
        }
    }
}