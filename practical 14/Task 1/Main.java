package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class StudentService {
        private final List<Student> students = new ArrayList<>();

        public void addStudent(int id, String name) {
            students.add(new Student(id, name));
        }

        public List<Student> getStudents() {
            return students;
        }

        public int getStudentCount() {
            return students.size();
        }

        @SuppressWarnings("unchecked")
        public void loadData(String filename) throws IOException, ClassNotFoundException {
            File file = new File(filename);
            if (!file.exists()) {
                return;
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = in.readObject();
                if (obj instanceof List<?>) {
                    students.clear();
                    for (Object item : (List<?>) obj) {
                        if (item instanceof Student) {
                            students.add((Student) item);
                        }
                    }
                }
            }
        }

        public void saveData(String filename) throws IOException {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(new ArrayList<>(students));
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentService();
        String filename = "students.dat";

        try {
            service.loadData(filename);
            System.out.println("Saved student data loaded.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Starting with empty student data.");
        }

        int choice;

        do {
            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Count Students");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            try {
                if (choice == 1) {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    service.addStudent(id, name);
                    System.out.println("Student added.");
                } else if (choice == 2) {
                    for (Student s : service.getStudents()) {
                        System.out.println(s.getId() + " - " + s.getName());
                    }
                } else if (choice == 3) {
                    System.out.println("Total students: " + service.getStudentCount());
                } else if (choice == 4) {
                    service.saveData(filename);
                    System.out.println("Data saved. Exiting.");
                } else {
                    throw new IllegalArgumentException("Invalid action");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Error saving data.");
            }
        } while (choice != 4);

        sc.close();
    }
}

