package dao;

import domain.model.Student;
import java.util.List;

public interface StudentDAO {
    void insertStudent(Student student) throws DAOException;

    boolean updateStudent(Student student) throws DAOException;

    Student selectStudent(int id) throws DAOException;

    List<Student> selectAllStudents() throws DAOException;

    boolean deleteStudent(int id) throws DAOException;

    List<Student> selectStudentsWithoutGroup() throws DAOException;

    List<Student> getAllStudentsByCourseName(String courseName) throws DAOException;

    List<Student> getAllStudentsByGroupName(String groupName) throws DAOException;
}

