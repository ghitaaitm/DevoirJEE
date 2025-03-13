package org.example.services;

import org.example.dao.StudentDAO;
import org.example.entities.Student;
import jakarta.persistence.EntityManager;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService(EntityManager entityManager) {
        this.studentDAO = new StudentDAO(entityManager);
    }

    public void addStudent(Student student) {
        studentDAO.save(student);
    }

    public Student getStudentById(Long id) {
        return studentDAO.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public void updateStudent(Student student) {
        studentDAO.update(student);
    }

    public void removeStudent(Long id) {
        studentDAO.delete(id);
    }
}
