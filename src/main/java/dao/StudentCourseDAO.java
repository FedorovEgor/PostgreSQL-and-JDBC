package dao;

import domain.model.StudentCourse;
import java.util.List;

public interface StudentCourseDAO {

    void insertToStudentsCourses(StudentCourse studentCourse) throws DAOException;

    boolean updateStudentsCourses(StudentCourse studentCourse) throws DAOException;

    StudentCourse selectFromStudentsCourses(int id) throws DAOException;
    StudentCourse selectFromStudentsCourses(int studentId, int courseId) throws DAOException;

    List<StudentCourse> selectAllStudentsCourses() throws DAOException;

    boolean deleteFromStudentsCourses(int id) throws DAOException;

    boolean doesSubscriptionExist(int studentId, int courseId) throws DAOException;
}

