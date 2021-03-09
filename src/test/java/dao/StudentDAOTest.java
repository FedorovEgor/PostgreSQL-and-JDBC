package dao;

import static org.junit.jupiter.api.Assertions.*;

import dao.postgre.StudentDAOImpl;
import domain.model.Student;
import domain.service.ScriptRunner;
import org.junit.jupiter.api.*;

import java.util.List;

public class StudentDAOTest {
    StudentDAO studentDAO = new StudentDAOImpl();

    @BeforeAll
    static void setupDb() throws DAOException {
        ScriptRunner scriptRunner = new ScriptRunner();
        scriptRunner.executeScript("sqlscript.sql");
        scriptRunner.executeScript("initData.sql");
    }

    @Test
    void getAllStudents_listSizeOfAllStudentsExpected() throws DAOException {
        int expectedStudentsNumber = 3;
        int actualStudentsNumber = studentDAO.selectAllStudents().size();

        assertEquals(expectedStudentsNumber, actualStudentsNumber);
    }

    @Test
    void getStudentById_singleStudentIsExpected() throws DAOException {
        Student expectedStudent = new Student(1,2, "Betty", "Walker");
        Student actualStudent = studentDAO.selectStudent(1);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void deleteStudentById_studentExpectedToBeDeleted() throws DAOException {
        boolean isDeleted = studentDAO.deleteStudent(2);

        assertTrue(isDeleted);
    }

    @Test
    void updateStudent_studentExpectedToBeUpdated() throws DAOException {
        Student expectedStudent = studentDAO.selectStudent(3);
        expectedStudent.setGroupId(3);
        studentDAO.updateStudent(expectedStudent);

        Student actualStudent = studentDAO.selectStudent(3);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void selectStudentsWithoutGroups_listOfStudentsWithoutGroupsExpected() throws DAOException {
        studentDAO.insertStudent(new Student("Charles", "Baker"));

        Student[] expectedStudents = new Student[]{new Student(6,0,"Charles", "Baker")};
        Student[] actualStudents = studentDAO.selectStudentsWithoutGroup().toArray(new Student[0]);

        assertArrayEquals(expectedStudents, actualStudents);
    }

    @Test
    void insertStudent_newStudentExpectedToBeCreatedInDataBase() throws DAOException {
        int initialStudentsNumber = studentDAO.selectAllStudents().size();

        int expectedStudentsNumber = initialStudentsNumber + 1;
        studentDAO.insertStudent(new Student(4,"Barry", "Barton"));
        int actualStudentsNumber = studentDAO.selectAllStudents().size();

        assertEquals(expectedStudentsNumber, actualStudentsNumber);
    }

    @Test
    void getAllStudentsByCourseName_listOfAllStudentsRelatedToCourseExpected() throws DAOException {

        Student[] expectedStudents = new Student[] {
                new Student(1, 2, "Betty", "Walker"),
                new Student(4, 2, "Mary", "Lee")
        };

        List<Student> listOfStudents = studentDAO.getAllStudentsByCourseName("History");
        Student[] actualStudents = listOfStudents.toArray(new Student[0]);

        assertArrayEquals(expectedStudents, actualStudents);
    }

    @Test
    void getAllStudentsByGroupName_listOfAllStudentsInTheNamedGroupIsExpected() throws DAOException {

        Student[] expectedStudents = new Student[] {
                new Student(1, 2, "Betty", "Walker"),
                new Student(4, 2, "Mary", "Lee")
        };

        List<Student> listOfStudents = studentDAO.getAllStudentsByGroupName("ed-48");
        Student[] actualStudents = listOfStudents.toArray(new Student[0]);

        assertArrayEquals(expectedStudents, actualStudents);
    }

}

