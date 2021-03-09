package dao.postgre;

import dao.DAOException;
import dao.DAOFactory;
import dao.GroupDAO;
import domain.model.Group;
import domain.service.GroupService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAOImpl implements GroupDAO {

    private DAOFactory daoFactory = DAOFactory.getInstance();

    private static final String INSERT_GROUPS_SQL = "insert into groups (group_name) values (?) ;";
    private static final String SELECT_GROUP_BY_ID = "select group_id, group_name from groups where group_id = ?;";
    private static final String SELECT_ALL_GROUPS = "select * from groups";
    private static final String DELETE_GROUP_BY_ID = "delete from groups where group_id = ?;";
    private static final String UPDATE_GROUPS_SQL = "update groups set group_name = ? where group_id = ?;";
    private static final String GET_GROUPS_WITH_LESS_OR_EQUAL_STUDENTS = "select group_id, count(*) from students group by group_id having count(*) <= ? order by group_id;";

    @Override
    public void insertGroup(Group group) throws DAOException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_GROUPS_SQL)) {
            statement.setString(1, group.getGroupName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to insert into groups", e);
        }
    }

    @Override
    public boolean updateGroup(Group group) throws DAOException {
        boolean isRowUpdated;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_GROUPS_SQL)) {
            statement.setString(1, group.getGroupName());
            statement.setInt(2, group.getGroupId());

            isRowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to update groups :", e);
        }
        return isRowUpdated;
    }

    @Override
    public Group selectGroup(int id) throws DAOException {
        Group group = null;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_GROUP_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                String groupName = resultSet.getString("group_name");

                group = new Group(groupId, groupName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select group from groups by group_id :", e);
        }
        return group;
    }

    @Override
    public List<Group> selectAllGroups() throws DAOException {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GROUPS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                String groupName = resultSet.getString("group_name");

                groups.add(new Group(groupId, groupName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to select all groups :", e);
        }
        return groups;
    }

    @Override
    public boolean deleteGroup(int id) throws DAOException {
        boolean isRowDeleted;
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_GROUP_BY_ID)) {
            statement.setInt(1, id);

            isRowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to delete group by group_id :", e);
        }
        return isRowDeleted;
    }

    public List<Group> getGroupsWithLessOrEqualStudents(int numberOfStudents) throws DAOException {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_GROUPS_WITH_LESS_OR_EQUAL_STUDENTS)) {
            statement.setInt(1, numberOfStudents);

            ResultSet resultSet = statement.executeQuery();

            GroupService groupService = new GroupService();

            while(resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                Group group = groupService.selectGroup(groupId);
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to get groups with requested number of members :", e);
        }
        return groups;
    }
}

