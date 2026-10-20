package com.javaProgram;

class Student1 {
    Student1() {
        System.out.println("Default Constructor");
    }

    Student1(String name) {
        System.out.println("Name: " + name);
    }

    Student1(String name, int age) {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public static void main(String[] args) {
        Student1 s1 = new Student1();
        Student1 s2 = new Student1("Rahul");
        Student1 s3 = new Student1("alok", 20);
    }
}