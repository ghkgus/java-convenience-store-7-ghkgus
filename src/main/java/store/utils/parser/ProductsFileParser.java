package store.utils.parser;

import java.util.List;
import store.domain.Product;
import store.utils.validator.ParserValidator;

public class ProductsFileParser {

    public static Product parsePromotionProductFile(List<String> product) {
        String name = getName(product);
        int price = getPrice(product);
        int promotionQuantity = getPromotionQuantity(product);
        String promotion = getPromotion(product);

        return new Product(name, price, 0, promotionQuantity, promotion);
    }

    public static Product parseOriginalProductFile(List<String> product) {
        String name = getName(product);
        int price = getPrice(product);
        int originalQuantity = getOriginalQuantity(product);

        return new Product(name, price, originalQuantity, 0, "null");
    }

    public static String getName(List<String> product) {
        ParserValidator.validateString(product.getFirst());
        return product.getFirst();
    }

    public static int getPrice(List<String> product) {
        ParserValidator.validateNumber(product.get(1));

        try {
            return Integer.parseInt(product.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수범위의 가격이 아닙니다.");
        }
    }

    public static int getPromotionQuantity(List<String> product) {
        ParserValidator.validateNumber(product.get(2));

        try {
            return Integer.parseInt(product.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수범위의 수량이 아닙니다.");
        }
    }

    public static int getOriginalQuantity(List<String> product) {
        ParserValidator.validateNumber(product.get(2));

        try {
            return Integer.parseInt(product.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수범위의 수량이 아닙니다.");
        }
    }

    public static String getPromotion(List<String> product) {
        ParserValidator.validateString(product.getLast());

        return product.getLast();
    }
}
