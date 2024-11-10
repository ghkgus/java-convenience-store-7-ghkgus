package store.config;

import java.util.Arrays;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.utils.parser.ProductsFileParser;

public class ProductsFileInitializer {

    private final ConfigFileReader configFileReader;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public ProductsFileInitializer(ConfigFileReader configFileReader, ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.configFileReader = configFileReader;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void initialize(String productsPath) {
        List<String> productsLines = configFileReader.readFile(productsPath);

        initializeProducts(productsLines);
    }

    private void initializeProducts(List<String> productsLines) {
        for (String productsLine : productsLines) {
            List<String> product = Arrays.asList(productsLine.trim().split(","));
            validateProductSize(product);
            //Promotion promotion = promotionRepository.findByKey(product.getLast());
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

    private boolean isPromotionAppliedProduct(String promotionName) {
        return !(promotionName.equals("null"));
    }

    private void updatePromotionProduct(List<String> product) {
        ProductsFileParser productsFileParser = new ProductsFileParser(promotionRepository);
        Product newProducts = productsFileParser.parsePromotionProductFile(product);
        String name = productsFileParser.getName(product);
        if (productRepository.containsKey(name)) {
            Product existedProduct = productRepository.findByKey(name);
            int promotionQuantity = productsFileParser.getPromotionQuantity(product);
            Promotion promotion = promotionRepository.findByKey(product.getLast());
            existedProduct.updatePromotionProduct(promotionQuantity, promotion);
            return;
        }
        productRepository.save(name, newProducts);
    }

    private void updateOriginalProduct(List<String> product) {
        ProductsFileParser productsFileParser = new ProductsFileParser(promotionRepository);
        Product newProducts = productsFileParser.parseOriginalProductFile(product);
        String name = productsFileParser.getName(product);
        if (productRepository.containsKey(name)) {
            Product existedProduct = productRepository.findByKey(name);
            int originalQuantity = productsFileParser.getOriginalQuantity(product);
            existedProduct.updateOriginalProduct(originalQuantity);
            return;
        }
        productRepository.save(name, newProducts);
    }
}
