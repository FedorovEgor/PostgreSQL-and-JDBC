package service;
import dao.DAOException;
import dao.StudentCourseDAO;
import dao.postgre.StudentCourseDAOImpl;
import domain.model.Course;
import domain.model.Student;
import domain.model.StudentCourse;
import domain.service.StudentCourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentCourseServiceTest {

    @Mock
    StudentCourseDAO studentCourseDAO = new StudentCourseDAOImpl();
    StudentCourseService studentCourseService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.studentCourseService = new StudentCourseService(studentCourseDAO);
    }

    @Test
    void getAllStudentsCourses_listOfAllStudentsCoursesExpected() throws DAOException {
        StudentCourse[] expectedStudentCourses = new StudentCourse[] {
                new StudentCourse(1,3),
                new StudentCourse(2,5)
        };
        when(studentCourseDAO.selectAllStudentsCourses()).thenReturn(Arrays.asList(expectedStudentCourses));
        StudentCourse[] actualStudentCourses = studentCourseService.selectAllStudentCourses().toArray(new StudentCourse[0]);
        assertArrayEquals(expectedStudentCourses, actualStudentCourses);

        verify(studentCourseDAO).selectAllStudentsCourses();
    }

    @Test
    void getStudentCourseById_singleStudentCourseExpected() throws DAOException {
        StudentCourse expectedStudentCourse = new StudentCourse(1,3);

        when(studentCourseDAO.selectFromStudentsCourses(1)).thenReturn(expectedStudentCourse);
        StudentCourse actualStudentCourse = studentCourseService.selectStudentCourse(1);
        assertEquals(expectedStudentCourse, actualStudentCourse);

        verify(studentCourseDAO).selectFromStudentsCourses(1);
    }

    @Test
    void getStudentCourseByStudentIdAndCourseId_singleStudentCourseIsExpected() throws DAOException {
        StudentCourse expectedStudentCourse = new StudentCourse(1, 3);

        when(studentCourseDAO.selectFromStudentsCourses(1,3)).thenReturn(expectedStudentCourse);
        StudentCourse actualStudentCourse = studentCourseService.selectByStudentAndCourseIds(1,3);
        assertEquals(expectedStudentCourse, actualStudentCourse);

        verify(studentCourseDAO).selectFromStudentsCourses(1,3);
    }

    @Test
    void deleteStudentCourseById_studentCourseExpectedToBeDeleted() throws DAOException {
        boolean isDeleted;
        when(studentCourseDAO.deleteFromStudentsCourses(1)).thenReturn(true);
        isDeleted = studentCourseService.deleteStudentCourse(1);

        assertTrue(isDeleted);
        verify(studentCourseDAO).deleteFromStudentsCourses(1);
    }

    @Test
    void updateStudentCourse_studentCourseExpectedToBeUpdated() throws DAOException {
        boolean isUpdated;
        StudentCourse studentCourse = new StudentCourse(4,9);
        when(studentCourseDAO.updateStudentsCourses(studentCourse)).thenReturn(true);
        isUpdated = studentCourseService.updateStudentCourse(studentCourse);

        assertTrue(isUpdated);
        verify(studentCourseDAO).updateStudentsCourses(studentCourse);
    }

    @Test
    void insertStudentCourse_newStudentCourseExpectedToBeCreatedInDataBase() throws DAOException {
        StudentCourse studentCourse = new StudentCourse(5,7);
        studentCourseService.insertStudentCourse(studentCourse);
        verify(studentCourseDAO).insertToStudentsCourses(studentCourse);
    }

    @Test
    void checkStudentSubscriptionToCourse() throws DAOException {
        boolean isSubscribed;

        when(studentCourseDAO.doesSubscriptionExist(1,7)).thenReturn(true);
        isSubscribed = studentCourseService.doesSubscriptionExist(1,7);

        assertTrue(isSubscribed);
        verify(studentCourseDAO).doesSubscriptionExist(1,7);
    }

    @Test
    void addStudentToCourse_studentShouldBeAddedToSpecificCourse() throws DAOException {
        Student student = new Student(1, 3,"Bob", "Coul");
        Course course = new Course(1, "Java", "");
        StudentCourse studentCourse = new StudentCourse(student.getStudentId(),course.getCourseId());
        studentCourseService.addStudentToCourse(student, course);

        verify(studentCourseDAO).insertToStudentsCourses(studentCourse);
    }

    @Test
    void deleteFromStudentFromCourse_studentShouldBeDeletedFromSpecificCourse() throws DAOException {
        Student student = new Student(4, 3, "Jim", "Moor");
        Course course = new Course(6, "Maths", "");
        StudentCourse studentCourse = new StudentCourse(student.getStudentId(), course.getCourseId());


        when(studentCourseDAO.selectFromStudentsCourses(student.getStudentId(), course.getCourseId())).thenReturn(studentCourse);
        studentCourseService.deleteStudentFromCourse(student,course);
        verify(studentCourseDAO).selectFromStudentsCourses(student.getStudentId(), course.getCourseId());
        verify(studentCourseDAO).deleteFromStudentsCourses(studentCourse.getStudentCourseId());
    }
}
