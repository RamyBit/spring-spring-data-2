package com.example.university.repo;

import com.example.university.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> findByFullTime(boolean fullTime);

    List<Student> findByAge(Integer age);

    List<Student> findByAttendeeLastName(String lastName);

    // findOldest,  findTopByOrderByAgeDesc()
    @Query(value = "SELECT * FROM Student s ORDER BY s.age DESC LIMIT 1", nativeQuery = true)
    Optional<Student> findOldest();

    @Query("SELECT s FROM Student s WHERE s.attendee.firstName = :firstName and s.attendee.lastName = :lastName")
    List<Student> findByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<Student> findByAgeLessThan(int age);

    @Query("SELECT s FROM Student s where s.attendee.lastName LIKE %:nameCriteria%")
    List<Student> findBySimilarLastName(@Param("nameCriteria") String nameCriteria);

    //findFirstInAlphabet
    @Query(value = "SELECT * FROM Student s ORDER BY s.last_name ASC LIMIT 1",nativeQuery = true)
    Optional<Student> findFirstInAlphabet();

    // find3Oldest
    @Query(value = "SELECT TOP 3 * FROM Student ORDER BY age DESC", nativeQuery = true)
    List<Student> find3Oldest();
}
