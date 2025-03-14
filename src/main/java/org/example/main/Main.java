package org.example.main;

import jakarta.persistence.*;
import org.example.entities.*;
import org.example.entities.Module;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {
            // Création du module
            Module module = new Module();
            module.setName("Mathématiques");

            // Création des étudiants et leurs adresses (indépendantes)
            Student student1 = new Student();
            student1.setName("Ghita AIT");
            student1.setEmail("ghitaait@example.com");

            Address address1 = new Address();
            address1.setStreet("123 Rue Exemple");
            address1.setCity("Casablanca");
            address1.setZipCode("20000");
            student1.setAddress(address1);  // Adresse unique pour l'étudiant 1

            Address address2 = new Address();  // Adresse unique pour l'étudiant 2
            address2.setStreet("456 Rue Exemple");
            address2.setCity("Rabat");
            address2.setZipCode("10000");

            Student student2 = new Student();
            student2.setName("Amine BEN");
            student2.setEmail("amineben@example.com");
            student2.setAddress(address2);  // Adresse unique pour l'étudiant 2

            // Module avec étudiants
            student1.setModule(module);
            student2.setModule(module);

            // Ajouter les cours aux étudiants
            Course course1 = new Course();
            course1.setTitle("Mathematics");

            Course course2 = new Course();
            course2.setTitle("Physics");

            student1.addCourse(course1);
            student1.addCourse(course2);
            student2.addCourse(course1);
            student2.addCourse(course2);

            // Ajouter les étudiants au module
            Set<Student> students = new HashSet<>();
            students.add(student1);
            students.add(student2);
            module.setStudents(students);

            // Persist les entités
            em.persist(module);
            em.persist(student1);
            em.persist(student2);
            em.persist(address1);  // Persister l'adresse unique de l'étudiant 1
            em.persist(address2);  // Persister l'adresse unique de l'étudiant 2
            em.persist(course1);
            em.persist(course2);

            em.getTransaction().commit();
            System.out.println("Étudiants, module, adresse et cours enregistrés avec succès !");

            // Recherche des étudiants dans le module
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.module = :module", Student.class);
            query.setParameter("module", module);
            List<Student> studentsInModule = query.getResultList();
            if (!studentsInModule.isEmpty()) {
                for (Student foundStudent : studentsInModule) {
                    System.out.println("Étudiant trouvé dans le module : " + foundStudent.getName());
                }
            }

            // Recherche des étudiants dans une ville
            TypedQuery<Student> addressQuery = em.createQuery(
                    "SELECT s FROM Student s WHERE s.address.city = :city", Student.class);
            addressQuery.setParameter("city", "Casablanca");
            List<Student> studentsInCity = addressQuery.getResultList();
            if (!studentsInCity.isEmpty()) {
                for (Student foundStudent : studentsInCity) {
                    System.out.println("Étudiant trouvé dans la ville : " + foundStudent.getName());
                }
            }

            // Mise à jour d'un étudiant et de son adresse
            updateStudent(em, student1.getId(), "Ghita Modifiée", "Rabat");

            // Mise à jour d'un cours
            updateCourse(em, course1.getId(), "Nouveau Titre de Mathématiques");

            // Suppression d'un étudiant
            deleteStudent(em, student2.getId());

            // Suppression des cours inutilisés
            em.getTransaction().begin();
            if (em.createQuery("SELECT c FROM Course c WHERE c.students IS EMPTY", Course.class)
                    .getResultList().contains(course1)) {
                em.remove(course1);
                System.out.println("Cours 'Mathematics' supprimé !");
            }
            if (em.createQuery("SELECT c FROM Course c WHERE c.students IS EMPTY", Course.class)
                    .getResultList().contains(course2)) {
                em.remove(course2);
                System.out.println("Cours 'Physics' supprimé !");
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    // Mise à jour d'un étudiant
    public static void updateStudent(EntityManager em, Long studentId, String newName, String newCity) {
        em.getTransaction().begin();
        Student studentToUpdate = em.find(Student.class, studentId);

        if (studentToUpdate != null) {
            studentToUpdate.setName(newName);
            studentToUpdate.getAddress().setCity(newCity);
            em.merge(studentToUpdate);
            System.out.println("Étudiant mis à jour avec succès !");
        } else {
            System.out.println(" Étudiant non trouvé.");
        }
        em.getTransaction().commit();
    }

    // Mise à jour d'un cours
    public static void updateCourse(EntityManager em, Long courseId, String newTitle) {
        em.getTransaction().begin();
        Course courseToUpdate = em.find(Course.class, courseId);

        if (courseToUpdate != null) {
            courseToUpdate.setTitle(newTitle);
            em.merge(courseToUpdate);
            System.out.println(" Cours mis à jour avec succès !");
        } else {
            System.out.println(" Cours non trouvé.");
        }
        em.getTransaction().commit();
    }

    // Suppression d'un étudiant
    public static void deleteStudent(EntityManager em, Long studentId) {
        em.getTransaction().begin();
        Student studentToDelete = em.find(Student.class, studentId);
        if (studentToDelete != null) {
            em.remove(studentToDelete);
            System.out.println(" Étudiant supprimé avec succès !");
        } else {
            System.out.println(" Étudiant non trouvé.");
        }
        em.getTransaction().commit();
    }
}