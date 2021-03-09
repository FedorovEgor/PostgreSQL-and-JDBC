package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLScriptRunner {
    public void executeScript(String sqlScript) throws DAOException {
        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlScript)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Unable to run a script :", e);
        }
    }
}
