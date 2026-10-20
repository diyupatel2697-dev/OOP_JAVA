import java.util.HashMap;
import java.util.Map;

public class StudentServiceTest {

    public void testAddStudentIncreasesCount() {
        StudentService service = new StudentService();

        service.addStudent(1, "Diya");

        if (service.getStudentCount() != 1) {
            throw new AssertionError("Expected student count to be 1");
        }
    }

    public void testInvalidActionThrowsException() {
        StudentService service = new StudentService();

        expectIllegalArgumentException(() -> {
            service.performAction(5);
        });
    }

    public void testInvalidStudentThrowsException() {
        StudentService service = new StudentService();

        expectIllegalArgumentException(() -> {
            service.addStudent(0, "Diya");
        });
    }

    private void expectIllegalArgumentException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException");
    }

    public static void main(String[] args) {
        StudentServiceTest tests = new StudentServiceTest();
        tests.testAddStudentIncreasesCount();
        tests.testInvalidActionThrowsException();
        tests.testInvalidStudentThrowsException();
    }
}

// Kept package-private so this single-file project can compile without a separate source file.
class StudentService {
    private final Map<Integer, String> students = new HashMap<>();

    public void addStudent(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        students.put(id, name);
    }

    public int getStudentCount() {
        return students.size();
    }

    public void performAction(int action) {
        if (action < 1 || action > 4) {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}
