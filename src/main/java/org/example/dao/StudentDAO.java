package org.example.dao;

import org.example.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class StudentDAO {
    private EntityManager entityManager;

    public StudentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Student student) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(student);
        transaction.commit();
    }

    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> findAll() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public void update(Student student) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(student);
        transaction.commit();
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            entityManager.remove(student);
        }
        transaction.commit();
    }
}
