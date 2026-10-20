import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    static Scanner sc = new Scanner(System.in);

    static class Student {
        String name;
        int rollNo;
        double[] marks;

        Student(String name, int rollNo, double[] marks) {
            this.name = name;
            this.rollNo = rollNo;
            this.marks = marks;
        }

        double getTotal() {
            double total = 0;
            for (double mark : marks) {
                total += mark;
            }
            return total;
        }

        double getAverage() {
            return getTotal() / marks.length;
        }

        String getGrade() {
            double avg = getAverage();

            if (avg >= 90) return "A+";
            if (avg >= 80) return "A";
            if (avg >= 70) return "B";
            if (avg >= 60) return "C";
            if (avg >= 50) return "D";
            return "F";
        }

        void display() {
            System.out.println("\n----- STUDENT RESULT -----");
            System.out.println("Name: " + name);
            System.out.println("Roll Number: " + rollNo);

            for (int i = 0; i < marks.length; i++) {
                System.out.println("Subject " + (i + 1) + ": " + marks[i]);
            }

            System.out.println("Total Marks: " + getTotal());
            System.out.printf("Average Marks: %.2f%n", getAverage());
            System.out.println("Grade: " + getGrade());
        }
    }

    static ArrayList<Student> students = new ArrayList<>();

    static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    static double readMarks(int subject) {
        while (true) {
            System.out.print("Enter marks for subject " + subject + " (0-100): ");

            try {
                double marks = Double.parseDouble(sc.nextLine().trim());

                if (marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println("Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid marks.");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        int rollNo = readInt("Enter roll number: ");

        for (Student s : students) {
            if (s.rollNo == rollNo) {
                System.out.println("Roll number already exists!");
                return;
            }
        }

        int subjects;

        do {
            subjects = readInt("Enter number of subjects (1-10): ");

            if (subjects < 1 || subjects > 10) {
                System.out.println("Enter a number between 1 and 10.");
            }
        } while (subjects < 1 || subjects > 10);

        double[] marks = new double[subjects];

        for (int i = 0; i < subjects; i++) {
            marks[i] = readMarks(i + 1);
        }

        Student student = new Student(name, rollNo, marks);
        students.add(student);

        System.out.println("\nStudent added successfully!");
        student.display();
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    static void searchStudent() {
        int rollNo = readInt("Enter roll number to search: ");

        for (Student s : students) {
            if (s.rollNo == rollNo) {
                s.display();
                return;
            }
        }

        System.out.println("Student not found!");
    }

    static void showStatistics() {
        if (students.isEmpty()) {
            System.out.println("Please add a student first.");
            return;
        }

        double totalAverage = 0;
        double highest = -1;
        double lowest = 101;
        Student topStudent = null;

        for (Student s : students) {
            double avg = s.getAverage();

            totalAverage += avg;

            if (avg > highest) {
                highest = avg;
                topStudent = s;
            }

            if (avg < lowest) {
                lowest = avg;
            }
        }

        System.out.println("\n----- CLASS STATISTICS -----");
        System.out.println("Total Students: " + students.size());
        System.out.printf("Class Average: %.2f%n",
                totalAverage / students.size());
        System.out.printf("Highest Average: %.2f%n", highest);
        System.out.printf("Lowest Average: %.2f%n", lowest);

        if (topStudent != null) {
            System.out.println("Top Student: " + topStudent.name);
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n================================");
            System.out.println("     STUDENT GRADE TRACKER");
            System.out.println("================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Show Statistics");
            System.out.println("5. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    showStatistics();
                    break;

                case 5:
                    System.out.println("Thank you for using Student Grade Tracker!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please select 1-5.");
            }
        }
    }
}