package org.example.services;

import org.example.dao.CourseDAO;
import org.example.entities.Course;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CourseService {
    private CourseDAO courseDAO;

    public CourseService(EntityManager entityManager) {
        this.courseDAO = new CourseDAO(entityManager);
    }

    public void addCourse(Course course) {
        courseDAO.save(course);
    }

    public Course getCourseById(Long id) {
        return courseDAO.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public void updateCourse(Course course) {
        courseDAO.update(course);
    }

    public void removeCourse(Long id) {
        courseDAO.delete(id);
    }
}
