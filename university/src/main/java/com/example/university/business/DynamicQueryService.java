package com.example.university.business;

import com.example.university.domain.Course;
import com.example.university.repo.CourseQueryDslRepo;
import com.example.university.repo.CourseRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicQueryService {

    private CourseRepo courseRepo;
    private CourseQueryDslRepo queryDslRepo;

    public DynamicQueryService(CourseRepo courseRepo, CourseQueryDslRepo courseQueryDslRepo) {

        this.courseRepo = courseRepo;
        this.queryDslRepo = courseQueryDslRepo;
    }
    public List<Course> filterBySpecification(CourseFilter filter){
        return courseRepo.findAll(filter.getSpecification());
    }
    public List<Course>filterByQueryDsl(CourseFilter filter){
        List<Course> courses = new ArrayList<>();
        queryDslRepo.findAll(filter.getQueryPredicate()).forEach(courses::add);
        return courses;
    }

    public List<Course>filterByExample(CourseFilter filter){
        return courseRepo.findAll(filter.getExampleProbe());
    }
}
