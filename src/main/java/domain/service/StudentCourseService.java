package domain.service;

import dao.DAOException;
import dao.StudentCourseDAO;
import dao.postgre.CourseDAOImpl;
import dao.postgre.StudentCourseDAOImpl;
import domain.model.Course;
import domain.model.Student;
import domain.model.StudentCourse;

import java.util.List;

public class StudentCourseService {

    private StudentCourseDAO studentCourseDAO;

    public StudentCourseService() {
        studentCourseDAO = new StudentCourseDAOImpl();
    }

    public StudentCourseService(StudentCourseDAO studentCourseDAO) {
        this.studentCourseDAO = studentCourseDAO;
    }

    public void insertStudentCourse(StudentCourse studentCourse) throws DAOException {
        studentCourseDAO.insertToStudentsCourses(studentCourse);
    }

    public boolean updateStudentCourse(StudentCourse studentCourse) throws DAOException {
        return studentCourseDAO.updateStudentsCourses(studentCourse);
    }

    public StudentCourse selectStudentCourse(int id) throws DAOException {
        return studentCourseDAO.selectFromStudentsCourses(id);
    }

    public StudentCourse selectByStudentAndCourseIds(int studentId, int courseId) throws DAOException {
        return studentCourseDAO.selectFromStudentsCourses(studentId, courseId);
    }

    public List<StudentCourse> selectAllStudentCourses() throws DAOException {
        return studentCourseDAO.selectAllStudentsCourses();
    }

    public boolean deleteStudentCourse(int id) throws DAOException {
        return studentCourseDAO.deleteFromStudentsCourses(id);
    }

    public boolean doesSubscriptionExist(int studentId, int courseId) throws DAOException {
        return studentCourseDAO.doesSubscriptionExist(studentId, courseId);
    }

    public void addStudentToCourse(Student student, Course course) throws DAOException {
        StudentCourse studentCourse = new StudentCourse(student.getStudentId(), course.getCourseId());
        studentCourseDAO.insertToStudentsCourses(studentCourse);
    }

    public void deleteStudentFromCourse(Student student, Course course) throws DAOException {
        StudentCourse studentCourse = studentCourseDAO.selectFromStudentsCourses(student.getStudentId(), course.getCourseId());
        studentCourseDAO.deleteFromStudentsCourses(studentCourse.getStudentCourseId());
    }
}

