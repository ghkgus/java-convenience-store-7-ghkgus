package store.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConfigFileReaderTest {

    private static final String FILE_PATH = "testFile.md";

    private ConfigFileReader configFileReader;

    @BeforeEach
    public void setUp(){
        configFileReader = new ConfigFileReader();
        String contents = "skip first Line \n content1 \n content2 \ncontent3";
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            writer.println(contents.trim());  // 내용 쓰기
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("개행마다 파일이 입력되고 출력되는지 확인한다.")
    @Test
    void Given_CorrectFile_When_ReadFile_Then_Success() {
        List<String> testLines = configFileReader.readFile(FILE_PATH);

        assertThat(testLines)
                .hasSize(3)
                .containsExactly("content1", "content2", "content3");
    }

    @DisplayName("파일이 존재하지 않으면 RuntimeException을 던져야 한다")
    @Test
    void Given_NonExistFile_When_ReadFile_Then_RuntimeException() {
        String nonFile = "nonFile.md";

        assertThatThrownBy(() -> configFileReader.readFile(nonFile))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("[ERROR] 파일이 존재하지 않습니다.");
    }

    @AfterEach
    @DisplayName("테스트 후 임시 파일을 삭제")
    public void tearDown() {
        new File(FILE_PATH).delete();
    }
}