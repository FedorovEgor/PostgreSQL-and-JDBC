package dao.postgre;

import dao.DAOException;
import dao.DAOFactory;
import dao.StudentCourseDAO;
import domain.model.StudentCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseDAOImpl implements StudentCourseDAO {

    private DAOFactory daoFactory = DAOFactory.getInstance();

    private static final String INSERT_STUDENTS_COURSES_SQL = "insert into students_courses (student_id, course_id) values (?,?);";
    private static final String SELECT_STUDENT_COURSE_BY_ID = "select id, student_id, course_id from students_courses where id = ?;";
    private static final String SELECT_STUDENT_COURSE_BY_BOTH_IDS = "select * from students_courses where student_id = ? and course_id = ?;";
    private static final String SELECT_ALL_STUDENTS_COURSES = "select * from students_courses";
    private static final String DELETE_STUDENTS_COURSES_SQL = "delete from students_courses where id = ?;";
    private static final String UPDATE_STUDENTS_COURSES_SQL = "update students_courses set student_id = ?,course_id = ? where id = ?;";
    private static final String GET_SUBSCRIPTION_SQL = "select 1 from students_courses where student_id = ? and course_id = ?";

    @Override
    public void insertToStudentsCourses(StudentCourse studentCourse) throws DAOException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STUDENTS_COURSES_SQL)) {
            statement.setInt(1, studentCourse.getStudent_id());
            statement.setInt(2, studentCourse.getCourse_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to insert student_course to students_courses :", e);
        }
    }

    @Override
    public boolean updateStudentsCourses(StudentCourse studentCourse) throws DAOException {
        boolean isRowUpdated;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENTS_COURSES_SQL)) {
            statement.setInt(1, studentCourse.getStudent_id());
            statement.setInt(2, studentCourse.getCourse_id());
            statement.setInt(3, studentCourse.getStudentCourseId());

            isRowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to update students_courses :", e);
        }
        return isRowUpdated;
    }

    @Override
    public StudentCourse selectFromStudentsCourses(int id) throws DAOException {
        StudentCourse studentCourse = null;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_COURSE_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int studentsCoursesId = resultSet.getInt("id");
                int studentId = resultSet.getInt("student_id");
                int courseId = resultSet.getInt("course_id");

                studentCourse = new StudentCourse(studentsCoursesId, studentId, courseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select student_course from students_courses by id :", e);
        }
        return studentCourse;
    }

    public StudentCourse selectFromStudentsCourses(int idOfStudent, int idOfCourse) throws DAOException {
        StudentCourse studentCourse = null;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_COURSE_BY_BOTH_IDS)) {
            statement.setInt(1, idOfStudent);
            statement.setInt(2, idOfCourse);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int studentsCoursesId = resultSet.getInt("id");
                int studentId = resultSet.getInt("student_id");
                int courseId = resultSet.getInt("course_id");

                studentCourse = new StudentCourse(studentsCoursesId, studentId, courseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select student_course from students_courses by student_id and course_id :", e);
        }
        return studentCourse;
    }

    @Override
    public List<StudentCourse> selectAllStudentsCourses() throws DAOException {
        List<StudentCourse> studentsCourses = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STUDENTS_COURSES)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int studentCourseId = resultSet.getInt("id");
                int studentId = resultSet.getInt("student_id");
                int courseId = resultSet.getInt("course_id");

                studentsCourses.add(new StudentCourse(studentCourseId, studentId, courseId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select all students_courses :", e);
        }
        return studentsCourses;
    }

    @Override
    public boolean deleteFromStudentsCourses(int id) throws DAOException {
        boolean isRowDeleted;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENTS_COURSES_SQL)) {
            statement.setInt(1, id);

            isRowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to delete student_course from students_courses :", e);
        }
        return isRowDeleted;
    }

    @Override
    public boolean doesSubscriptionExist(int studentId, int courseId) throws DAOException {
        boolean isSubscribed = false;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SUBSCRIPTION_SQL)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1)
                    isSubscribed = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to get subscription info :", e);
        }
        return isSubscribed;
    }
}

