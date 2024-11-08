package store.config;

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
            throw new RuntimeException("[ERROR] 파일이 존재하지 않습니다.");
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] 파일을 읽을 수 없습니다.");
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
