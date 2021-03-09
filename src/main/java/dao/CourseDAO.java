package dao;

import domain.model.Course;
import java.util.List;

public interface CourseDAO {
    void insertCourse(Course course) throws DAOException;

    boolean updateCourse(Course course) throws DAOException;

    Course selectCourse(int id) throws DAOException;

    List<Course> selectAllCourses() throws DAOException;

    boolean deleteCourse(int id) throws DAOException;
}

