package store.utils.parser;

import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.PromotionRepository;
import store.utils.validator.ParserValidator;

public class ProductsFileParser {

    private final PromotionRepository promotionRepository;

    public ProductsFileParser(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Product parsePromotionProductFile(List<String> product) {
        String name = getName(product);
        int price = getPrice(product);
        int promotionQuantity = getPromotionQuantity(product);
        Promotion promotion = getPromotion(product);

        return new Product(name, price, 0, promotionQuantity, promotion);
    }

    public Product parseOriginalProductFile(List<String> product) {
        String name = getName(product);
        int price = getPrice(product);
        int originalQuantity = getOriginalQuantity(product);
        Promotion promotion = getPromotion(product);

        return new Product(name, price, originalQuantity, 0, promotion);
    }

    public String getName(List<String> product) {
        ParserValidator.validateString(product.getFirst());
        return product.getFirst();
    }

    public int getPrice(List<String> product) {
        ParserValidator.validateNumber(product.get(1));

        try {
            return Integer.parseInt(product.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수범위의 가격이 아닙니다.");
        }
    }

    public int getPromotionQuantity(List<String> product) {
        ParserValidator.validateNumber(product.get(2));

        try {
            return Integer.parseInt(product.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수범위의 수량이 아닙니다.");
        }
    }

    public int getOriginalQuantity(List<String> product) {
        ParserValidator.validateNumber(product.get(2));

        try {
            return Integer.parseInt(product.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수범위의 수량이 아닙니다.");
        }
    }

    public Promotion getPromotion(List<String> product) {
        // null 일 경우 promotion.md에 없어서 containkey 했을 때 없을 것이다. => 어떻게 해야할까 -> ???
        ParserValidator.validateString(product.getLast());
        if (!promotionRepository.containsKey(product.getLast())) {
            Promotion promotion = new Promotion("null", 0, 0, null, null);
            return promotion;
        }
        return promotionRepository.findByKey(product.getLast());
    }
}
