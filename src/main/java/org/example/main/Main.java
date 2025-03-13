package org.example.main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.entities.Address;
import org.example.entities.Course;
import org.example.entities.Module;
import org.example.entities.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {

            // Création de l'adresse d'abord
            Address address = new Address();
            address.setStreet("123 Rue Exemple");
            address.setCity("Casablanca");
            address.setZipCode("20000");

            Module module = new Module();
            module.setName("Module de Mathématiques");

            Student student1 = new Student();
            student1.setName("Ghita AIT");
            student1.setEmail("ghitaait@example.com");
            student1.setAddress(address);
            student1.setModule(module);

            Student student2 = new Student();
            student2.setName("Amine BEN");
            student2.setEmail("amineben@example.com");
            student2.setAddress(address);
            student2.setModule(module);

            // Créer des cours
            Course course1 = new Course();
            course1.setTitle("Mathematics");

            Course course2 = new Course();
            course2.setTitle("Physics");

            student1.addCourse(course1);
            student1.addCourse(course2);

            student2.addCourse(course1);
            student2.addCourse(course2);

            Set<Student> students = new HashSet<>();
            students.add(student1);
            students.add(student2);
            module.setStudents(students);

            em.persist(address);
            em.persist(module);
            em.persist(student1);
            em.persist(student2);
            em.persist(course1);
            em.persist(course2);

            em.getTransaction().commit();
            System.out.println("Étudiants, module, adresse et cours enregistrés avec succès !");

            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.module = :module", Student.class);
            query.setParameter("module", module);

            List<Student> studentsInModule = query.getResultList();
            if (!studentsInModule.isEmpty()) {
                for (Student foundStudent : studentsInModule) {
                    System.out.println("Étudiant trouvé dans le module : " + foundStudent.getName());
                }
            } else {
                System.out.println("Aucun étudiant trouvé pour ce module.");
            }

            TypedQuery<Student> addressQuery = em.createQuery(
                    "SELECT s FROM Student s WHERE s.address.city = :city", Student.class);
            addressQuery.setParameter("city", "Casablanca");

            List<Student> studentsInCity = addressQuery.getResultList();
            if (!studentsInCity.isEmpty()) {
                for (Student foundStudent : studentsInCity) {
                    System.out.println("Étudiant trouvé dans la ville : " + foundStudent.getName());
                }
            } else {
                System.out.println("Aucun étudiant trouvé dans cette ville.");
            }

            Student studentToUpdate = em.find(Student.class, student1.getId());
            if (studentToUpdate != null) {
                studentToUpdate.setName("Ghita Modifiée");
                studentToUpdate.getAddress().setCity("Rabat");
                em.merge(studentToUpdate);
                System.out.println("✅ Étudiant modifié avec succès !");
            }

            Student studentToDelete = em.find(Student.class, student2.getId());
            if (studentToDelete != null) {
                studentToDelete.removeCourse(course1);  // Supprimer le cours du student avant de supprimer l'étudiant
                studentToDelete.removeCourse(course2);
                em.remove(studentToDelete);  // Supprimer l'étudiant
                System.out.println("Étudiant et ses informations supprimés avec succès !");
            }

            em.getTransaction().begin();
            if (em.createQuery("SELECT c FROM Course c WHERE c.students IS EMPTY", Course.class)
                    .getResultList().contains(course1)) {
                em.remove(course1);
                System.out.println(" Cours 'Mathematics' supprimé !");
            }
            if (em.createQuery("SELECT c FROM Course c WHERE c.students IS EMPTY", Course.class)
                    .getResultList().contains(course2)) {
                em.remove(course2);
                System.out.println(" Cours 'Physics' supprimé !");
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
}
