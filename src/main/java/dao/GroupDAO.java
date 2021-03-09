package dao;

import domain.model.Group;
import java.util.List;

public interface GroupDAO {
    void insertGroup(Group group) throws DAOException;

    boolean updateGroup(Group group) throws DAOException;

    Group selectGroup(int id) throws DAOException;

    List<Group> selectAllGroups() throws DAOException;

    boolean deleteGroup(int id) throws DAOException;

    List<Group> getGroupsWithLessOrEqualStudents(int numberOfStudents) throws DAOException;
}

