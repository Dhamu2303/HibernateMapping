package com.javatpoint;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class StoreTest {
    public static void main(String args[]) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        try (SessionFactory factory = meta.getSessionFactoryBuilder().build();
             Session session = factory.openSession()) {

            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                HashMap<String, String> map1 = new HashMap<>();
                map1.put("Java is a programming language", "John Milton");
                map1.put("Java is a platform", "Ashok Kumar");

                HashMap<String, String> map2 = new HashMap<>();
                map2.put("Servlet technology is a server side programming", "John Milton");
                map2.put("Servlet is an Interface", "Ashok Kumar");
                map2.put("Servlet is a package", "Rahul Kumar");

                Question question1 = new Question("What is Java?", "Alok", map1);
                Question question2 = new Question("What is Servlet?", "Jai Dixit", map2);

                session.persist(question1);
                session.persist(question2);

                transaction.commit();
                System.out.println("Successfully stored.");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                System.out.println("Error occurred while storing the data: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while setting up Hibernate: " + e.getMessage());
        }
    }
}
