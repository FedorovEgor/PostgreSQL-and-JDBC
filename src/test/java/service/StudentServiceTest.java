package service;

import dao.DAOException;
import dao.StudentDAO;
import dao.postgre.StudentDAOImpl;
import domain.model.Student;
import domain.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceTest {

    @Mock
    StudentDAO studentDAO = new StudentDAOImpl();
    StudentService studentService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.studentService = new StudentService(studentDAO);
    }

    @Test
    void getAllStudents_listOfAllStudentsExpected() throws DAOException {
        Student[] expectedStudents = new Student[] {
                new Student(1, "Paul", "Howard"),
                new Student(4, "Thomas", "Reed")
        };

        when(studentDAO.selectAllStudents()).thenReturn(Arrays.asList(expectedStudents));
        Student[] actualStudents = studentService.selectAllStudents().toArray(new Student[0]);
        assertArrayEquals(expectedStudents, actualStudents);
        verify(studentDAO).selectAllStudents();
    }

    @Test
    void getStudentById_singleStudentIsExpected() throws DAOException {
        Student expectedStudent = new Student(15, 2, "John", "Moor");
        when(studentDAO.selectStudent(15)).thenReturn(expectedStudent);
        Student actualStudent = studentService.selectStudent(15);

        assertEquals(expectedStudent, actualStudent);
        verify(studentDAO).selectStudent(15);
    }

    @Test
    void deleteStudentById_studentExpectedToBeDeleted() throws DAOException {
        boolean isDeleted;
        when(studentDAO.deleteStudent(1)).thenReturn(true);
        isDeleted = studentService.deleteStudent(1);

        assertTrue(isDeleted);
        verify(studentDAO).deleteStudent(1);
    }

    @Test
    void updateStudent_studentExpectedToBeUpdated() throws DAOException {
        boolean isUpdated;
        Student student = new Student(1, 1, "Egor", "Fedorov");
        when(studentDAO.updateStudent(student)).thenReturn(true);
        isUpdated = studentService.updateStudent(student);

        assertTrue(isUpdated);
        verify(studentDAO).updateStudent(student);
    }

    @Test
    void selectStudentsWithoutGroups_listOfStudentsWithoutGroupsExpected() throws DAOException {
        Student[] expectedStudents = new Student[] {
                new Student(0, "Paul", "Howard"),
                new Student(0, "Thomas", "Reed")
        };

        when(studentDAO.selectStudentsWithoutGroup()).thenReturn(Arrays.asList(expectedStudents));
        Student[] actualStudents = studentService.selectStudentsWithoutGroup().toArray(new Student[0]);
        assertArrayEquals(expectedStudents, actualStudents);
        verify(studentDAO).selectStudentsWithoutGroup();
    }

    @Test
    void insertStudent_newStudentExpectedToBeCreatedInDataBase() throws DAOException {
        Student student = new Student(2, "Thomas", "Reed");
        studentService.insertStudent(student);
        verify(studentDAO).insertStudent(student);
    }

    @Test
    void getAllStudentsByCourseName_listOfAllStudentsRelatedToCourseExpected() throws DAOException {

        Student[] expectedStudents = new Student[] {
                new Student(1, 2, "Betty", "Walker"),
                new Student(4, 2, "Mary", "Lee")
        };

       when(studentDAO.getAllStudentsByCourseName("History")).thenReturn(Arrays.asList(expectedStudents));
       Student[] actualStudents = studentService.getAllStudentsByCourseName("History").toArray(new Student[0]);
       assertArrayEquals(expectedStudents, actualStudents);

       verify(studentDAO).getAllStudentsByCourseName("History");
    }

    @Test
    void getAllStudentsByGroupName_listOfAllStudentsInTheNamedGroupIsExpected() throws DAOException {

        Student[] expectedStudents = new Student[] {
                new Student(1, 2, "Betty", "Walker"),
                new Student(4, 2, "Mary", "Lee")
        };

        when(studentDAO.getAllStudentsByGroupName("ed-48")).thenReturn(Arrays.asList(expectedStudents));
        Student[] actualStudents = studentService.getAllStudentsByGroupName("ed-48").toArray(new Student[0]);
        assertArrayEquals(expectedStudents, actualStudents);

        verify(studentDAO).getAllStudentsByGroupName("ed-48");
    }
}
