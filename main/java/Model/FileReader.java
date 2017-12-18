package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Markus on 18.12.2017.
 */
public class FileReader {
    public ArrayList<String> fileReader(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        return new ArrayList<String>(Files.readAllLines(filePath));
    }
}
