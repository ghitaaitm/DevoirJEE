package org.example.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaManager {

    private static final String PERSISTENCE_UNIT_NAME = "myPersistenceUnit";
    private static EntityManagerFactory entityManagerFactory;

    // Initialisation de l'EntityManagerFactory
    static {
        try {
            // Crée l'EntityManagerFactory pour la persistence unit
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retourner un nouvel EntityManager pour interagir avec la base de données
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory non initialisé.");
        }
        return entityManagerFactory.createEntityManager();
    }

    // Démarrer une transaction
    public static void beginTransaction(EntityManager entityManager) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    // Commiter une transaction
    public static void commitTransaction(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }

    // Rollback d'une transaction
    public static void rollbackTransaction(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    // Fermer l'EntityManagerFactory lors de l'arrêt de l'application
    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
