package domain.service;

import dao.DAOException;
import dao.GroupDAO;
import dao.postgre.GroupDAOImpl;
import domain.model.Group;

import java.util.List;

public class GroupService {

    private GroupDAO groupDAO;

    public GroupService() {
        groupDAO = new GroupDAOImpl();
    }

    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public void insertGroup(Group group) throws DAOException {
        groupDAO.insertGroup(group);
    }

    public boolean updateGroup(Group group) throws DAOException {
        return groupDAO.updateGroup(group);
    }

    public Group selectGroup(int id) throws DAOException {
        return groupDAO.selectGroup(id);
    }

    public List<Group> selectAllGroups() throws DAOException {
        return groupDAO.selectAllGroups();
    }

    public boolean deleteGroup(int id) throws DAOException {
        return groupDAO.deleteGroup(id);
    }

    public List<Group> getGroupsWithLessOrEqualStudents(int numberOfStudents) throws DAOException {
        return groupDAO.getGroupsWithLessOrEqualStudents(numberOfStudents);
    }
}

