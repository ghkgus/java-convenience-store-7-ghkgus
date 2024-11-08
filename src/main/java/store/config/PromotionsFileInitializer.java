package store.config;

import java.util.Arrays;
import java.util.List;
import store.domain.Promotion;
import store.repository.PromotionRepository;
import store.utils.parser.PromotionsFileParser;

public class PromotionsFileInitializer {
    private final ConfigFileReader configFileReader;
    private final PromotionRepository promotionRepository;

    public PromotionsFileInitializer(ConfigFileReader configFileReader, PromotionRepository promotionRepository) {
        this.configFileReader = configFileReader;
        this.promotionRepository = promotionRepository;
    }

    public void initialize(String promotionsPath) {
        List<String> promotionsLines = configFileReader.readFile(promotionsPath);

        initializePromotions(promotionsLines);
    }

    private void initializePromotions(List<String> promotionsLines) {
        for (String promotionsLine : promotionsLines) {
            List<String> promotion = Arrays.asList(promotionsLine.trim().split(","));
            validateSize(promotion);
            Promotion newPromotions = PromotionsFileParser.getPromotions(promotion);
            promotionRepository.save(promotion.getFirst(), newPromotions);
        }
    }

    private void validateSize(List<String> promotion) {
        if (promotion.size() != 5) {
            throw new IllegalArgumentException("[ERROR] 파일의 형식이 잘못되었습니다.");
        }
    }

}
