package com.course.main;

import com.course.model.Course;
import com.course.model.Student;
import com.course.service.CourseService;
import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;
import com.course.exception.DuplicateEnrollmentException;

public class Main {
    public static void main(String[] args) {
        CourseService service = new CourseService();

        Course javaCourse = new Course(1, "Advanced Java", 2);
        Course pythonCourse = new Course(2, "Python Data Science", 5);

        service.addCourse(javaCourse);
        service.addCourse(pythonCourse);

        Student s1 = new Student(101, "John");
        Student s2 = new Student(102, "Emma");
        Student s3 = new Student(103, "Ryan");

        try {
            service.enrollStudent(1, s1);
            service.enrollStudent(1, s2);
            service.enrollStudent(1, s3);
        } catch (CourseNotFoundException | CourseFullException | DuplicateEnrollmentException e) {
            System.out.println("Error enrolling Ryan: " + e.getMessage());
        }

        try {
            service.enrollStudent(2, s1);
            service.enrollStudent(2, s1);
        } catch (CourseNotFoundException | CourseFullException | DuplicateEnrollmentException e) {
            System.out.println("Error enrolling John twice: " + e.getMessage());
        }

        try {
            service.enrollStudent(99, s1);
        } catch (CourseNotFoundException | CourseFullException | DuplicateEnrollmentException e) {
            System.out.println("Error enrolling in weird course: " + e.getMessage());
        }

        System.out.println("\nAll Course Details:");
        service.viewCourses();
    }
}
