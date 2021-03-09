import dao.DAOException;
import domain.service.TableDataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws DAOException, IOException, URISyntaxException {
        TableDataGenerator tableDataGenerator = new TableDataGenerator();
        tableDataGenerator.generateData();
    }
}
