package store.config;

import static store.constants.fileConstants.FileErrorMessage.INVALID_FILE;
import static store.constants.fileConstants.FileErrorMessage.UNREADABLE_FILE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileReader {

    public List<String> readFile(String filePath) {
        List<String> newFiles = new ArrayList<>();
        try {
            getFile(filePath, newFiles);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(INVALID_FILE.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(UNREADABLE_FILE.getMessage());
        }
        return newFiles;
    }

    private void getFile(String filePath, List<String> newFiles) throws IOException {
        FileReader fileReader;
        String line = "";
        fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();

        while ((line = bufferedReader.readLine()) != null) {
            newFiles.add(line.trim());
        }
    }
}
