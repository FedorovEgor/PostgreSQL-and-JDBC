package dao.postgre;

import dao.DAOException;
import dao.DAOFactory;
import dao.StudentDAO;
import domain.model.Student;
import domain.service.StudentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private DAOFactory daoFactory = DAOFactory.getInstance();

    private static final String INSERT_STUDENTS_SQL = "insert into students (group_id, first_name, last_name) values (?,?,?);";
    private static final String SELECT_STUDENT_BY_ID = "select student_id, group_id, first_name, last_name from students where student_id = ?;";
    private static final String SELECT_ALL_STUDENTS = "select * from students";
    private static final String DELETE_STUDENTS_SQL = "delete from students where student_id = ?;";
    private static final String UPDATE_STUDENTS_SQL = "update students set group_id = ?,first_name = ?, last_name = ? where student_id = ?;";
    private static final String SELECT_STUDENTS_WITHOUT_GROUPS = "select student_id, group_id, first_name, last_name from students where group_id = 0;";
    private static final String GET_STUDENTS_BY_COURSE_NAME = "select student_id from students_courses natural join courses where course_name = ? order by student_id;";
    private static final String GET_STUDENTS_BY_GROUP_NAME = "select student_id from students natural join groups where group_name = ? order by student_id;";

    @Override
    public void insertStudent(Student student) throws DAOException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STUDENTS_SQL)) {
            statement.setInt(1, student.getGroupId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to insert student into students :", e);
        }
    }

    @Override
    public boolean updateStudent(Student student) throws DAOException {
        boolean isRowUpdated;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENTS_SQL)) {
            statement.setInt(1, student.getGroupId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getStudentId());

            isRowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to update students :", e);
        }
        return isRowUpdated;
    }

    @Override
    public Student selectStudent(int id) throws DAOException {
        Student student = null;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int groupId = resultSet.getInt("group_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                student = new Student(studentId, groupId, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select student by student_id :", e);
        }
        return student;
    }

    @Override
    public List<Student> selectAllStudents() throws DAOException{
        List<Student> students = new ArrayList<>();

        try(Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STUDENTS)){

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int groupId = resultSet.getInt("group_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                students.add(new Student(studentId, groupId, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select all students :", e);
        }
        return students;
    }

    @Override
    public boolean deleteStudent(int id) throws DAOException {
        boolean isRowDeleted;
        try(Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_STUDENTS_SQL)) {
            statement.setInt(1, id);

            isRowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to delete student by student_id :", e);
        }
        return isRowDeleted;
    }

    public List<Student> selectStudentsWithoutGroup() throws DAOException {
        List<Student> studentsWithoutGroup = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENTS_WITHOUT_GROUPS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int groupId = resultSet.getInt("group_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                studentsWithoutGroup.add(new Student(studentId, groupId, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select students with group_id = 0 from students", e);
        }
        return studentsWithoutGroup;
    }

    public List<Student> getAllStudentsByCourseName(String courseName) throws DAOException {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STUDENTS_BY_COURSE_NAME)) {

            statement.setString(1, courseName);

            ResultSet resultSet = statement.executeQuery();

            StudentService studentService = new StudentService();

            while(resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                Student student = studentService.selectStudent(studentId);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to get students related to course with requested name :", e);
        }
        return students;
    }

    public List<Student> getAllStudentsByGroupName(String groupName) throws DAOException {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STUDENTS_BY_GROUP_NAME)) {

            statement.setString(1, groupName);

            ResultSet resultSet = statement.executeQuery();

            StudentService studentService = new StudentService();

            while(resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                Student student = studentService.selectStudent(studentId);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to get students related to requested group :", e);
        }
        return students;
    }
}

