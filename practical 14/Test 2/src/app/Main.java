package app;

import java.io.IOException;
import java.util.Scanner;

import service.StudentService;

public class Main {
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
                    try {
                        Object students = service.getClass().getMethod("getStudents").invoke(service);
                        for (Object student : (Iterable<?>) students) {
                            try {
                                Object id = student.getClass().getMethod("getId").invoke(student);
                                Object name = student.getClass().getMethod("getName").invoke(student);
                                System.out.println(id + " - " + name);
                            } catch (ReflectiveOperationException e) {
                                System.out.println("Unable to view student.");
                            }
                        }
                    } catch (ReflectiveOperationException e) {
                        System.out.println("Unable to view students.");
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