package com.example.university.dao;

import com.example.university.domain.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object Class for the Course Entity.
 * Uses only javax.persistence libraries.
 */
@Repository
public class CourseDao {
    private EntityManager em;

    public CourseDao(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }


    public List<Course> findByCriteria(CriteriaQuery<Course> criteria){
        return em.createQuery(criteria).getResultList();
    }
}
