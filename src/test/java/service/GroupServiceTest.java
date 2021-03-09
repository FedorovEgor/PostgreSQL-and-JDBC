package service;
import dao.DAOException;
import dao.GroupDAO;
import dao.postgre.GroupDAOImpl;
import domain.model.Group;
import domain.service.GroupService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupServiceTest {

    @Mock
    GroupDAO groupDAO = new GroupDAOImpl();
    GroupService groupService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.groupService = new GroupService(groupDAO);
    }

    @Test
    void getAllGroups_listOfAllGroupsExpected() throws DAOException {

        Group[] expectedGroups = new Group[] {
                new Group(1, "ed-48"),
                new Group(2, "ef-75")
        };

        when(groupDAO.selectAllGroups()).thenReturn(Arrays.asList(expectedGroups));
        Group[] actualGroups = groupService.selectAllGroups().toArray(new Group[0]);
        assertArrayEquals(expectedGroups, actualGroups);

        verify(groupDAO).selectAllGroups();
    }

    @Test
    void getGroupById_singleGroupExpected() throws DAOException {
        Group expectedGroup = new Group(1, "ed-48");

        when(groupDAO.selectGroup(1)).thenReturn(expectedGroup);
        Group actualGroup = groupService.selectGroup(1);
        assertEquals(expectedGroup, actualGroup);

        verify(groupDAO).selectGroup(1);
    }

    @Test
    void deleteGroupById_groupExpectedToBeDeleted() throws DAOException {
        boolean isDeleted;
        when(groupDAO.deleteGroup(1)).thenReturn(true);
        isDeleted = groupService.deleteGroup(1);

        assertTrue(isDeleted);
        verify(groupDAO).deleteGroup(1);
    }

    @Test
    void updateGroup_groupExpectedToBeUpdated() throws DAOException {
        boolean isUpdated;

        Group group = new Group(1, "ef-98");
        when(groupDAO.updateGroup(group)).thenReturn(true);
        isUpdated = groupService.updateGroup(group);

        assertTrue(isUpdated);
        verify(groupDAO).updateGroup(group);
    }

    @Test
    void insertGroup_newGroupExpectedToBeCreatedInDataBase() throws DAOException {
        Group group = new Group(1, "ed-48");
        groupService.insertGroup(group);
        verify(groupDAO).insertGroup(group);
    }

    @Test
    void getGroupsWithRequestedNumberOfMembers_listOfGroupsIsExpected() throws DAOException {

        Group[] expectedGroups = new Group[] {
                new Group(1, "ef-41"),
                new Group(2, "ed-48"),
                new Group(4, "er-48")
        };

        when(groupDAO.getGroupsWithLessOrEqualStudents(10)).thenReturn(Arrays.asList(expectedGroups));
        Group[] actualGroups = groupService.getGroupsWithLessOrEqualStudents(10).toArray(new Group[0]);
        assertArrayEquals(expectedGroups, actualGroups);

        verify(groupDAO).getGroupsWithLessOrEqualStudents(10);
    }
}
