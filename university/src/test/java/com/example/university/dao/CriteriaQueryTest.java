package com.example.university.dao;

import com.example.university.business.CourseFilter;
import com.example.university.business.DynamicQueryService;
import com.example.university.business.UniversityService;
import com.example.university.domain.Department;
import com.example.university.domain.Person;
import com.example.university.domain.Staff;
import com.example.university.repo.DepartmentRepo;
import com.example.university.repo.StaffRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import static com.example.university.business.CourseFilter.filterBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * Test Criteria-based queries
 */
@SpringBootTest
public class CriteriaQueryTest {

    @Autowired
    private DynamicQueryService queryService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private StaffRepo staffRepo;

    @Test
    void findByCriteria() {
        UniversityFactory.fillUniversity(universityService);
        Department humanities = departmentRepo.findByName("Humanities").get();
        Staff professorBlack = staffRepo.findByMemberLastName("Black").stream().findFirst().get();

        System.out.println('\n' + "*** All Humanities Courses");
        queryAndVerify(filterBy().department(humanities));

        System.out.println('\n' + "*** 4 credit courses");
        queryAndVerify(filterBy().credits(4));

        System.out.println('\n' + "*** Courses taught by Professor Black");
        queryAndVerify(filterBy().instructor(professorBlack));

        System.out.println('\n' + "*** Courses In Humanities, taught by Professor Black, 4 credits");
        queryAndVerify(filterBy()
                .department(humanities)
                .credits(4)
                .instructor(professorBlack));

        System.out.println('\n' + "### Query Dsl Tests ###");
        System.out.println('\n' + "*** All Humanities Courses");
        queryDslAndVerify(filterBy().department(humanities));

        System.out.println('\n' + "*** 4 credit courses");
        queryDslAndVerify(filterBy().credits(4));

        System.out.println('\n' + "*** Courses taught by Professor Black");
        queryDslAndVerify(filterBy().instructor(professorBlack));

        System.out.println('\n' + "*** Courses In Humanities, taught by Professor Black, 4 credits");
        queryDslAndVerify(filterBy()
                .department(humanities)
                .credits(4)
                .instructor(professorBlack));


    }

    @Test
    void findByCriteriaExample(){
        UniversityFactory.fillUniversity(universityService);
        Department humanities = departmentRepo
                .findOne(Example.of(new Department("Humanities", null), matching().withIgnoreCase())).get();
        Staff professorBlack = staffRepo
                .findAll(Example.of(new Staff(new Person(null, "Black")),
                        matching().withIgnoreCase()))
                .stream().findFirst().get();

        System.out.println('\n' + "### Query Example Tests ###");
        System.out.println('\n' + "*** All Humanities Courses");
        queryByExampleAndVerify(filterBy().department(humanities));

        System.out.println('\n' + "*** 4 credit courses");
        queryByExampleAndVerify(filterBy().credits(4));

        System.out.println('\n' + "*** Courses taught by Professor Black");
        queryByExampleAndVerify(filterBy().instructor(professorBlack));

        System.out.println('\n' + "*** Courses In Humanities, taught by Professor Black, 4 credits");
        queryByExampleAndVerify(filterBy()
                .department(humanities)
                .credits(4)
                .instructor(professorBlack));
    }


    private void queryAndVerify(CourseFilter filter) {
        queryService.filterBySpecification(filter)
                .forEach(course -> {
                    filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
                    filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
                    filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
                    System.out.println(course);
                });
    }
    private void queryDslAndVerify(CourseFilter filter) {
        queryService.filterByQueryDsl(filter)
                .forEach(course -> {
                    filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
                    filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
                    filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
                    System.out.println(course);
                });
    }
    private void queryByExampleAndVerify(CourseFilter filter) {
        queryService.filterByExample(filter)
                .forEach(course -> {
                    filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
                    filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
                    filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
                    System.out.println(course);
                });
    }
}
