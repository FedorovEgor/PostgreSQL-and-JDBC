package dao;

import dao.postgre.CourseDAOImpl;
import domain.model.Course;
import domain.service.ScriptRunner;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDAOTest {
    CourseDAO courseDAO = new CourseDAOImpl();

    @BeforeAll
    static void setupDb() throws DAOException {
        ScriptRunner scriptRunner = new ScriptRunner();
        scriptRunner.executeScript("sqlscript.sql");
        scriptRunner.executeScript("initData.sql");
    }

    @Test
    void getAllCourses_listSizeOfAllCoursesExpected() throws DAOException {
        int expectedCourseCount = 1;
        int actualCourseCount = courseDAO.selectAllCourses().size();

        assertEquals(expectedCourseCount, actualCourseCount);
    }

    @Test
    void getCourseById_singleCourseExpected() throws DAOException {
        Course expectedCourse = new Course(2, "History", "History is the study of the past.");
        Course actualCourse = courseDAO.selectCourse(2);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void deleteCourseBYId_courseExpectedToBeDeleted() throws DAOException {
        boolean isDeleted;
        isDeleted = courseDAO.deleteCourse(2);

        assertTrue(isDeleted);
    }

    @Test
    void updateCourse_courseExpectedToBeUpdated() throws DAOException {
        Course expectedCourse = courseDAO.selectCourse(1);
        expectedCourse.setCourseName("Java");
        expectedCourse.setCourseDescription("Java core.");
        courseDAO.updateCourse(expectedCourse);

        Course actualCourse = courseDAO.selectCourse(1);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void insertCourse_newCourseExpectedToBeCreatedInDataBase() throws DAOException {
        int initialCourseNumber = courseDAO.selectAllCourses().size();

        courseDAO.insertCourse(new Course("Computer science", "Computer science is the study of computation and information."));
        int expectedCoursesNumber = initialCourseNumber + 1;
        int actualCoursesNumber = courseDAO.selectAllCourses().size();

        assertEquals(expectedCoursesNumber, actualCoursesNumber);
    }
}

