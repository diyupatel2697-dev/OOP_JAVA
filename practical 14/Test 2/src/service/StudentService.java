package service;

import java.util.ArrayList;
import java.io.*;

public class StudentService {
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        private int id;
        private String name;

        public Student(int id, String name) {
            if (id <= 0 || name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid student details");
            }
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

    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(int id, String name) {
        if (id <= 0 || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student details");
        }

        Student student = new Student(id, name);
        students.add(student);
    }

    public int getStudentCount() {
        return students.size();
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void saveData(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(students);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            students = (ArrayList<Student>) in.readObject();
        }
    }
}