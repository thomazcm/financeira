package com.thomazcm.financeira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.thomazcm.financeira.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (em.createQuery("SELECT p FROM Profile p WHERE p.name = :name")
                .setParameter("name", "ROLE_USER")
                .getResultList().isEmpty()) {
            Profile userRole = new Profile();
            userRole.setName("ROLE_USER");
            em.persist(userRole);
        }
        
        if (em.createQuery("SELECT p FROM Profile p WHERE p.name = :name")
                .setParameter("name", "ROLE_ADMIN")
                .getResultList().isEmpty()) {
            Profile adminRole = new Profile();
            adminRole.setName("ROLE_ADMIN");
            em.persist(adminRole);
        }
    }
}
