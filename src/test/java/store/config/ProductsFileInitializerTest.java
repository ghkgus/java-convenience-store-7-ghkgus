package store.config;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.repository.ProductRepository;

class ProductsFileInitializerTest {

    private ConfigFileReader configFileReader;
    private ProductRepository productRepository;
    private ProductsFileInitializer productsFileInitializer;
    private static final String FILE_PATH = "testFile.md";
    private static final String TEMP_FILE_PATH = "testFailFile.md";

    @BeforeEach
    void setUp() {
        configFileReader = new ConfigFileReader();
        productRepository = new ProductRepository();

        productsFileInitializer = new ProductsFileInitializer(configFileReader, productRepository);
    }

    @DisplayName("한 줄씩 읽을 때, 한줄에 4개의 값이 들어와있을 경우 똑바로 검증된다.")
    @Test
    void Given_OneLineFourWord_When_ValidateSize_Then_Success() {
        String productLine = " \n 콜라,1000,5,프로모션";
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            writer.println(productLine.trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        productsFileInitializer.initialize(FILE_PATH);
        assertThat(true).isTrue();
    }

    @DisplayName("한 줄에 4개의 값이 아닌 경우 예외가 발생한다.")
    @Test
    void Given_OneLineNotFourWord_When_ValidateSize_Then_ThrowException() throws FileNotFoundException{
        String productLine = "콜라,1000,5,프로모션 \n 콜라,1000,5,프로모션,invalid";
        try (PrintWriter writer = new PrintWriter(TEMP_FILE_PATH)) {
            writer.println(productLine.trim());
        }
        assertThatThrownBy(() -> productsFileInitializer.initialize(TEMP_FILE_PATH))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 파일의 형식이 잘못되었습니다.");
    }

    @AfterEach
    @DisplayName("테스트 후 임시 파일을 삭제")
    public void tearDown() {
        new File(FILE_PATH).delete();
        new File(TEMP_FILE_PATH).delete();
    }
}