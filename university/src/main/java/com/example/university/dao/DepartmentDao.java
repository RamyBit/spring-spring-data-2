package com.example.university.dao;

import com.example.university.domain.Department;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
/**
 * Data Access Object Class for the Department Entity.
 * Uses only javax.persistence libraries.
 */

@Repository
public class DepartmentDao {

    private EntityManager em;
    public DepartmentDao(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }



}
