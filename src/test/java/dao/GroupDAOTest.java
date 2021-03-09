package dao;

import static org.junit.jupiter.api.Assertions.*;

import dao.postgre.GroupDAOImpl;
import domain.model.Group;
import domain.service.ScriptRunner;
import org.junit.jupiter.api.*;

import java.util.List;

public class GroupDAOTest {
    GroupDAO groupDAO = new GroupDAOImpl();

    @BeforeAll
    static void setupDb() throws DAOException {
        ScriptRunner scriptRunner = new ScriptRunner();
        scriptRunner.executeScript("sqlscript.sql");
        scriptRunner.executeScript("initData.sql");
    }

    @Test
    void getAllGroups_listSizeOfAllGroupsExpected() throws DAOException {
        int expectedGroupCount = 5;
        int actualGroupCount = groupDAO.selectAllGroups().size();

        assertEquals(expectedGroupCount, actualGroupCount);
    }

    @Test
    void getGroupById_singleGroupExpected() throws DAOException {
        Group expectedGroup = new Group(1, "ef-41");
        Group actualGroup = groupDAO.selectGroup(1);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void deleteGroupById_groupExpectedToBeDeleted() throws DAOException {
        boolean isDeleted;
        isDeleted = groupDAO.deleteGroup(3);

        assertTrue(isDeleted);
    }

    @Test
    void updateGroup_groupExpectedToBeUpdated() throws DAOException {
        Group expectedGroup = groupDAO.selectGroup(4);
        expectedGroup.setGroupName("new");
        groupDAO.updateGroup(expectedGroup);

        Group actualGroup = groupDAO.selectGroup(4);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void insertGroup_newGroupExpectedToBeCreatedInDataBase() throws DAOException {
        int initialGroupsNumber = groupDAO.selectAllGroups().size();

        groupDAO.insertGroup(new Group("pk-66"));
        int expectedGroupsNumber = initialGroupsNumber + 1;
        int actualGroupsNumber = groupDAO.selectAllGroups().size();

        assertEquals(expectedGroupsNumber, actualGroupsNumber);
    }

    @Test
    void getGroupsWithRequestedNumberOfMembers_listOfGroupsIsExpected() throws DAOException {

        Group[] expectedGroups = new Group[] {
                new Group(1, "ef-41"),
                new Group(2, "ed-48"),
                new Group(4, "er-48")
        };

        List<Group> listOfGroups = groupDAO.getGroupsWithLessOrEqualStudents(10);
        Group[] actualGroups = listOfGroups.toArray(new Group[0]);

        assertArrayEquals(expectedGroups, actualGroups);
    }

}

