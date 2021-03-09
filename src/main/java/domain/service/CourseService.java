package domain.service;

import dao.CourseDAO;
import dao.DAOException;
import dao.postgre.CourseDAOImpl;
import domain.model.Course;

import java.util.List;

public class CourseService {

    private CourseDAO courseDAO;

    public CourseService() {
        courseDAO = new CourseDAOImpl();
    }

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void insertCourse(Course course) throws DAOException {
        courseDAO.insertCourse(course);
    }

    public boolean updateCourse(Course course) throws DAOException {
        return courseDAO.updateCourse(course);
    }

    public Course selectCourse(int id) throws DAOException {
        return courseDAO.selectCourse(id);
    }

    public List<Course> selectAllCourses() throws DAOException {
        return courseDAO.selectAllCourses();
    }

    public boolean deleteCourse(int id) throws DAOException {
        return courseDAO.deleteCourse(id);
    }

}

