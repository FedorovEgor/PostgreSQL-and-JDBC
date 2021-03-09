package domain.service;

import dao.DAOException;
import domain.model.Course;
import domain.model.Group;
import domain.model.Student;
import domain.model.StudentCourse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

public class TableDataGenerator {

    public void generateData() throws DAOException, IOException, URISyntaxException {
        createTables();
        initializeCourses();
        initializeGroups(10);
        initializeStudents(200);
        assignToGroups();
        assignCourses();
    }

    private void createTables() throws DAOException {
        ScriptRunner scriptRunner = new ScriptRunner();
        scriptRunner.executeScript("sqlscript.sql");
    }

    private void initializeCourses() throws IOException, DAOException, URISyntaxException {
        FileToListReader fileToListReader = new FileToListReader();

        List<String> courseNames = fileToListReader.readFileToList("courses.txt");
        List<String> courseDescriptions = fileToListReader.readFileToList("courseDescriptions.txt");
        CourseService courseService = new CourseService();

        for (int i = 0; i < courseNames.size(); i++) {
            Course course = new Course(courseNames.get(i), courseDescriptions.get(i));
            courseService.insertCourse(course);
        }
    }

    private void initializeGroups(int numberOfGroupsToGenerate) throws DAOException {
        GroupService groupService = new GroupService();
        GroupNameGenerator nameGenerator = new GroupNameGenerator();
        for (int i = 0; i < numberOfGroupsToGenerate; i++) {
            Group group = new Group(nameGenerator.getRandomGroupName());
            groupService.insertGroup(group);
        }
    }

    private void initializeStudents(int numberOfStudents) throws DAOException, IOException, URISyntaxException {
        FileToListReader fileToListReader = new FileToListReader();

        List<String> firstNames = fileToListReader.readFileToList("firstNames.txt");
        List<String> lastNames = fileToListReader.readFileToList("lastNames.txt");
        StudentService studentService = new StudentService();
        for (int i = 0; i < numberOfStudents; i++) {
            Student student = new Student(getRandomName(firstNames), getRandomName(lastNames));
            studentService.insertStudent(student);
        }
    }

    private String getRandomName(List<String> listOfNames) {
        Random random = new Random();
        return listOfNames.get(random.nextInt(listOfNames.size()));
    }

    public void assignToGroups() throws DAOException {
        List<Student> studentsWithoutGroups;
        Student student;
        Random random = new Random();
        StudentService studentService = new StudentService();
        for (int i = 1; i <= 10; i++) {
            studentsWithoutGroups = studentService.selectStudentsWithoutGroup();
            int membersCount = random.nextInt(21) + 10;
            for (int j = 0; j < membersCount; j++) {
                student = studentsWithoutGroups.get(random.nextInt(studentsWithoutGroups.size()));
                student.setGroupId(i);
                studentService.updateStudent(student);
            }
        }
    }

    public void assignCourses() throws DAOException {
        StudentService studentService = new StudentService();
        StudentCourseService studentCourseService = new StudentCourseService();
        List<Student> listOfAllStudents = studentService.selectAllStudents();
        Random random = new Random();

        for (Student eachStudent : listOfAllStudents) {
            int numberOfCourses = random.nextInt(3) + 1;
            for (int i = 0; i < numberOfCourses; i++) {
                int randomCourseId = random.nextInt(10) + 1;
                if (studentCourseService.doesSubscriptionExist(eachStudent.getStudentId(), randomCourseId)) {
                    i--;
                    continue;
                }
                studentCourseService.insertStudentCourse(new StudentCourse(eachStudent.getStudentId(), randomCourseId));
            }
        }

    }

}

