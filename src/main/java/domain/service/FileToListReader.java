package domain.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileToListReader {
    public List<String> readFileToList(String fileName) throws IOException, URISyntaxException {
        URL fileUrl = this.getClass().getClassLoader().getResource(fileName);

        return Files.lines(Paths.get(fileUrl.toURI())).collect(Collectors.toList());
    }
}

