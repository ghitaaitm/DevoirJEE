package org.example.dao;

import org.example.entities.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class AddressDAO {
    private EntityManager entityManager;

    public AddressDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Address address) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(address);
        transaction.commit();
    }

    public Address findById(Long id) {
        return entityManager.find(Address.class, id);
    }

    public List<Address> findAll() {
        return entityManager.createQuery("SELECT a FROM Address a", Address.class).getResultList();
    }

    public void update(Address address) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(address);
        transaction.commit();
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Address address = entityManager.find(Address.class, id);
        if (address != null) {
            entityManager.remove(address);
        }
        transaction.commit();
    }
}
