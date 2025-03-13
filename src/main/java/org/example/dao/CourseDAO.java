package org.example.dao;

import org.example.entities.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CourseDAO {
    private EntityManager entityManager;

    public CourseDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Course course) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(course);
        transaction.commit();
    }

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    public List<Course> findAll() {
        return entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    public void update(Course course) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(course);
        transaction.commit();
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            entityManager.remove(course);
        }
        transaction.commit();
    }
}
