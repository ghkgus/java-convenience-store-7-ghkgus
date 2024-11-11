package store.config;

import static store.constants.FileErrorMessage.INVALID_FILE_CONTENT_FORM;

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
            ProductsFileParser productsFileParser = new ProductsFileParser(promotionRepository);
            if (isPromotionAppliedProduct(product.getLast())) {
                updatePromotionProduct(productsFileParser, product);
            } else {
                updateOriginalProduct(productsFileParser, product);
            }
        }
    }

    private void validateProductSize(List<String> product) {
        if (product.size() != 4) {
            throw new IllegalArgumentException(INVALID_FILE_CONTENT_FORM.getMessage());
        }
    }

    private boolean isPromotionAppliedProduct(String promotionName) {
        return !(promotionName.equals("null"));
    }

    private void updatePromotionProduct(ProductsFileParser productsFileParser, List<String> product) {
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

    private void updateOriginalProduct(ProductsFileParser productsFileParser, List<String> product) {
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
