package domain.service;

import dao.DAOException;
import dao.DAOFactory;
import dao.SQLScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ScriptRunner {
    SQLScriptRunner sqlScriptRunner;

    public ScriptRunner() {
        sqlScriptRunner = new SQLScriptRunner();
    }

    public void executeScript(String sqlScriptName) throws DAOException {
        String sqlScriptString = readScriptToString(sqlScriptName);
        sqlScriptRunner.executeScript(sqlScriptString);
    }

    private String readScriptToString(String sqlScriptName) {
        URL fileUrl = this.getClass().getClassLoader().getResource(sqlScriptName);

        String s;
        StringBuilder sb = new StringBuilder();
        try {
            FileReader reader = new FileReader(new File(fileUrl.getFile()));
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

