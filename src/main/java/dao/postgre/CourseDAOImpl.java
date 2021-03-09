package dao.postgre;

import dao.CourseDAO;
import dao.DAOException;
import dao.DAOFactory;
import domain.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    private DAOFactory daoFactory = DAOFactory.getInstance();

    private static final String INSERT_COURSES_SQL = "insert into courses (course_name, course_description) values (?,?);";
    private static final String SELECT_COURSE_BY_ID = "select course_id, course_name, course_description from courses where course_id = ?;";
    private static final String SELECT_ALL_COURSES = "select * from courses";
    private static final String DELETE_COURSES_SQL = "delete from courses where course_id = ?;";
    private static final String UPDATE_COURSES_SQL = "update courses set course_name = ?, course_description = ? where course_id = ?;";

    @Override
    public void insertCourse(Course course) throws DAOException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_COURSES_SQL)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to insert course into courses :", e);
        }
    }

    @Override
    public boolean updateCourse(Course course) throws DAOException {
        boolean isRowUpdated;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COURSES_SQL)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.setInt(3, course.getCourseId());

            isRowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to update courses :", e);
        }
        return isRowUpdated;
    }

    @Override
    public Course selectCourse(int id) throws DAOException {
        Course course = null;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                String courseDescription = resultSet.getString("course_description");

                course = new Course(courseId, courseName, courseDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select course ny course_id :", e);
        }
        return course;
    }

    @Override
    public List<Course> selectAllCourses() throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COURSES)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                String courseDescription = resultSet.getString("course_description");

                courses.add(new Course(courseId, courseName, courseDescription));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select all courses :", e);
        }
        return courses;
    }

    @Override
    public boolean deleteCourse(int id) throws DAOException {
        boolean isRowDeleted;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COURSES_SQL)) {
            statement.setInt(1, id);

            isRowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to delete course from courses by course_id :", e);
        }
        return isRowDeleted;
    }
}

