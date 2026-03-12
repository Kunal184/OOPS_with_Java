package com.course.model;

public class Course {
    private int courseId;
    private String courseName;
    private int maxSeats;
    private int enrolledStudents;
    private Student[] students;

    public Course(int courseId, String courseName, int maxSeats) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.maxSeats = maxSeats;
        this.enrolledStudents = 0;
        this.students = new Student[maxSeats];
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
        Student[] newStudents = new Student[maxSeats];
        for (int i = 0; i < enrolledStudents && i < maxSeats; i++) {
            newStudents[i] = students[i];
        }
        students = newStudents;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public Student[] getStudents() {
        return students;
    }

    public void display() {
        System.out.println("Course ID: " + courseId + " | Name: " + courseName + " | Capacity: " + enrolledStudents + "/" + maxSeats);
        for (int i = 0; i < enrolledStudents; i++) {
            students[i].display();
        }
    }
}
