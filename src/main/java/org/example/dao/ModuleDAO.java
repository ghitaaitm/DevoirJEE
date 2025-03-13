package org.example.dao;

import org.example.entities.Module;
import org.example.utils.JpaManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ModuleDAO {

    // Méthode pour créer ou mettre à jour un module
    public void save(Module module) {
        EntityManager entityManager = JpaManager.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(module); // Si l'objet a déjà un ID, ça fait une mise à jour
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e; // Propager l'exception pour que l'appelant puisse la gérer
        } finally {
            JpaManager.close(); // Fermer l'EntityManager
        }
    }

    // Méthode pour récupérer tous les modules
    public List<Module> findAll() {
        EntityManager entityManager = JpaManager.getEntityManager();
        try {
            return entityManager.createQuery("SELECT m FROM Module m", Module.class)
                    .getResultList();
        } finally {
            JpaManager.close(); // Fermer l'EntityManager
        }
    }

    // Méthode pour récupérer un module par son ID
    public Module findById(Long id) {
        EntityManager entityManager = JpaManager.getEntityManager();
        try {
            return entityManager.find(Module.class, id);
        } finally {
            JpaManager.close(); // Fermer l'EntityManager
        }
    }

    // Méthode pour supprimer un module par son ID
    public void delete(Long id) {
        EntityManager entityManager = JpaManager.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Module module = entityManager.find(Module.class, id);
            if (module != null) {
                entityManager.remove(module);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e; // Propager l'exception pour que l'appelant puisse la gérer
        } finally {
            JpaManager.close(); // Fermer l'EntityManager
        }
    }
}
