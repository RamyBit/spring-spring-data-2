package com.example.university.repo;

import com.example.university.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Integer> {

    Optional<Course> findByName(String name);
    List<Course> findByDepartmentChairMemberLastName(String chair);
    List<Course> findCourseByPrerequisites(Course prerequisite);
    List<Course> findByCredits(int credits);
}
