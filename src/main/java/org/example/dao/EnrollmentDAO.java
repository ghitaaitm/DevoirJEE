package org.example.dao;

import org.example.entities.Enrollment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class EnrollmentDAO {
    private EntityManager entityManager;

    public EnrollmentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Enrollment enrollment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(enrollment);
        transaction.commit();
    }

    public Enrollment findById(Long id) {
        return entityManager.find(Enrollment.class, id);
    }

    public List<Enrollment> findAll() {
        return entityManager.createQuery("SELECT e FROM Enrollment e", Enrollment.class).getResultList();
    }

    public void update(Enrollment enrollment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(enrollment);
        transaction.commit();
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Enrollment enrollment = entityManager.find(Enrollment.class, id);
        if (enrollment != null) {
            entityManager.remove(enrollment);
        }
        transaction.commit();
    }
}
