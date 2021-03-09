package dao;

import dao.postgre.StudentCourseDAOImpl;
import domain.model.StudentCourse;
import domain.service.ScriptRunner;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentCourseDAOTest {
    StudentCourseDAO studentCourseDAO = new StudentCourseDAOImpl();

    @BeforeAll
    static void setupDb() throws DAOException {
        ScriptRunner scriptRunner = new ScriptRunner();
        scriptRunner.executeScript("sqlscript.sql");
        scriptRunner.executeScript("initData.sql");
    }

    @Test
    void getAllStudentsCourses_listSizeOfAllStudentsCoursesExpected() throws DAOException {
        int expectedStudentsCoursesNumber = 4;
        int actualStudentsCoursesNumber = studentCourseDAO.selectAllStudentsCourses().size();

        assertEquals(expectedStudentsCoursesNumber, actualStudentsCoursesNumber);
    }

    @Test
    void getStudentCourseById_singleStudentCourseExpected() throws DAOException {
        StudentCourse expectedStudentCourse = new StudentCourse(1,2,1 );
        StudentCourse actualStudentCourse = studentCourseDAO.selectFromStudentsCourses(1);

        assertEquals(expectedStudentCourse, actualStudentCourse);
    }

    @Test
    void getStudentCourseByStudentIdAndCourseId_singleStudentCourseIsExpected() throws DAOException {
        StudentCourse expectedStudentCourse = new StudentCourse(1, 2, 1);
        StudentCourse actualStudentCourse = studentCourseDAO.selectFromStudentsCourses(2, 1);

        assertEquals(expectedStudentCourse, actualStudentCourse);
    }

    @Test
    void deleteStudentCourseById_studentCourseExpectedToBeDeleted() throws DAOException {
        boolean isDeleted;
        isDeleted = studentCourseDAO.deleteFromStudentsCourses(3);

        assertTrue(isDeleted);
    }

    @Test
    void updateStudentCourse_studentCourseExpectedToBeUpdated() throws DAOException {
        StudentCourse expectedStudentCourse = studentCourseDAO.selectFromStudentsCourses(4);
        expectedStudentCourse.setStudent_id(4);
        studentCourseDAO.updateStudentsCourses(expectedStudentCourse);

        StudentCourse actualStudentCourse = studentCourseDAO.selectFromStudentsCourses(4);

        assertEquals(expectedStudentCourse, actualStudentCourse);
    }

    @Test
    void insertStudentCourse_newStudentCourseExpectedToBeCreatedInDataBase() throws DAOException {
        int initialStudentCourseNumber = studentCourseDAO.selectAllStudentsCourses().size();

        int expectedStudentCourseNUmber = initialStudentCourseNumber + 1;
        studentCourseDAO.insertToStudentsCourses(new StudentCourse(1,1));
        int actualStudentCourseNumber = studentCourseDAO.selectAllStudentsCourses().size();

        assertEquals(expectedStudentCourseNUmber, actualStudentCourseNumber);
    }

    @Test
    void checkStudentSubscriptionToCourse() throws DAOException {
        boolean isSubscribed;

        isSubscribed = studentCourseDAO.doesSubscriptionExist(2, 1);
        assertTrue(isSubscribed);
    }
}

