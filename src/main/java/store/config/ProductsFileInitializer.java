package store.config;

import java.util.Arrays;
import java.util.List;
import store.domain.Product;
import store.repository.ProductRepository;
import store.utils.parser.ProductsFileParser;

public class ProductsFileInitializer {

    private final ConfigFileReader configFileReader;
    private final ProductRepository productRepository;

    public ProductsFileInitializer(ConfigFileReader configFileReader, ProductRepository productRepository) {
        this.configFileReader = configFileReader;
        this.productRepository = productRepository;
    }

    public void initialize(String productsPath) {
        List<String> productsLines = configFileReader.readFile(productsPath);

        initializeProducts(productsLines);
    }

    private void initializeProducts(List<String> productsLines) {
        for (String productsLine : productsLines) {
            List<String> product = Arrays.asList(productsLine.trim().split(","));
            validateProductSize(product);
            if (isPromotionAppliedProduct(product.getLast())) {
                updatePromotionProduct(product);
            } else {
                updateOriginalProduct(product);
            }
        }
    }

    private void validateProductSize(List<String> product) {
        if (product.size() != 4) {
            throw new IllegalArgumentException("[ERROR] 파일의 형식이 잘못되었습니다.");
        }
    }

    private boolean isPromotionAppliedProduct(String appliedPromotion) {
        return !(appliedPromotion.equals("null"));
    }

    private void updatePromotionProduct(List<String> product) {
        Product newProducts = ProductsFileParser.parsePromotionProductFile(product);
        String name = ProductsFileParser.getName(product);
        int promotionQuantity = ProductsFileParser.getPromotionQuantity(product);
        String promotion = ProductsFileParser.getPromotion(product);
        if (productRepository.containsKey(name)) {
            Product existedProduct = productRepository.findByKey(name);
            existedProduct.updatePromotionProduct(promotionQuantity, promotion);
            return;
        }
        productRepository.save(name, newProducts);
    }

    private void updateOriginalProduct(List<String> product) {
        Product newProducts = ProductsFileParser.parseOriginalProductFile(product);
        String name = ProductsFileParser.getName(product);
        int originalQuantity = ProductsFileParser.getOriginalQuantity(product);
        if (productRepository.containsKey(name)) {
            Product existedProduct = productRepository.findByKey(name);
            existedProduct.updateOriginalProduct(originalQuantity);
            return;
        }
        productRepository.save(name, newProducts);
    }
}
