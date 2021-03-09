package domain.service;

import dao.DAOException;
import dao.postgre.StudentDAOImpl;
import dao.StudentDAO;
import domain.model.Student;

import java.util.List;

public class StudentService {

    private StudentDAO studentDAO;

    public StudentService() {
        studentDAO = new StudentDAOImpl();
    }

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void insertStudent(Student student) throws DAOException {
        studentDAO.insertStudent(student);
    }

    public boolean updateStudent(Student student) throws DAOException {
        return studentDAO.updateStudent(student);
    }

    public Student selectStudent(int id) throws DAOException {
        return studentDAO.selectStudent(id);
    }

    public List<Student> selectAllStudents() throws DAOException {
        return studentDAO.selectAllStudents();
    }

    public boolean deleteStudent(int id) throws DAOException {
        return studentDAO.deleteStudent(id);
    }

    public List<Student> selectStudentsWithoutGroup() throws DAOException {
        return studentDAO.selectStudentsWithoutGroup();
    }

    public List<Student> getAllStudentsByGroupName(String groupName) throws DAOException {
        return studentDAO.getAllStudentsByGroupName(groupName);
    }

    public List<Student> getAllStudentsByCourseName(String courseName) throws DAOException {
        return studentDAO.getAllStudentsByCourseName(courseName);
    }
}

