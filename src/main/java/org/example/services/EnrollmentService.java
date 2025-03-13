package org.example.services;

import org.example.dao.EnrollmentDAO;
import org.example.entities.Enrollment;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EnrollmentService {
    private EnrollmentDAO enrollmentDAO;

    public EnrollmentService(EntityManager entityManager) {
        this.enrollmentDAO = new EnrollmentDAO(entityManager);
    }

    public void enrollStudent(Enrollment enrollment) {
        enrollmentDAO.save(enrollment);
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentDAO.findById(id);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentDAO.findAll();
    }

    public void updateEnrollment(Enrollment enrollment) {
        enrollmentDAO.update(enrollment);
    }

    public void removeEnrollment(Long id) {
        enrollmentDAO.delete(id);
    }
}
