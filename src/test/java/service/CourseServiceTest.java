package service;

import dao.CourseDAO;
import dao.DAOException;
import dao.postgre.CourseDAOImpl;
import domain.model.Course;
import domain.service.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseServiceTest {

    @Mock
    CourseDAO courseDAO = new CourseDAOImpl();
    CourseService courseService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.courseService = new CourseService(courseDAO);
    }

    @Test
    void getAllCourses_listOfAllCoursesExpected() throws DAOException {
        Course[] expectedCourses = new Course[] {
                new Course(1, "Java", ""),
                new Course(2, "Python", "")
        };
        when(courseDAO.selectAllCourses()).thenReturn( Arrays.asList(expectedCourses));

        Course[] actualCourses = courseService.selectAllCourses().toArray(new Course[0]);

        assertArrayEquals(expectedCourses, actualCourses);
        verify(courseDAO).selectAllCourses();
    }

    @Test
    void getCourseById_singleCourseExpected() throws DAOException {
        Course expectedCourse = new Course(2, "History", "History is the study of the past.");
        when(courseDAO.selectCourse(2)).thenReturn(expectedCourse);
        Course actualCourse = courseService.selectCourse(2);

        assertEquals(expectedCourse, actualCourse);
        verify(courseDAO).selectCourse(2);

    }

    @Test
    void deleteCourseBYId_courseExpectedToBeDeleted() throws DAOException {
        boolean isDeleted;
        when(courseDAO.deleteCourse(1)).thenReturn(true);
        isDeleted = courseService.deleteCourse(1);

        assertTrue(isDeleted);
        verify(courseDAO).deleteCourse(1);
    }

    @Test
    void updateCourse_courseExpectedToBeUpdated() throws DAOException {
        boolean isUpdated;
        Course course = new Course("Java", "Programming");
        when(courseDAO.updateCourse(course)).thenReturn(true);
        isUpdated = courseService.updateCourse(course);

        assertTrue(isUpdated);
        verify(courseDAO).updateCourse(course);
    }

    @Test
    void insertCourse_newCourseExpectedToBeCreatedInDataBase() throws DAOException {
        Course course = new Course("Java", "");
        courseService.insertCourse(course);
        verify(courseDAO).insertCourse(course);
    }
}
