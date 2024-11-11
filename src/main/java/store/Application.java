package store;

import static store.constants.fileConstants.FilePath.PRODUCT_MD_PATH;
import static store.constants.fileConstants.FilePath.PROMOTION_MD_PATH;

import store.config.ConfigFileReader;
import store.config.ProductsFileInitializer;
import store.config.PromotionsFileInitializer;
import store.controller.StoreController;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.OrderService;
import store.service.ProductService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ConfigFileReader configFileReader = new ConfigFileReader();
        PromotionRepository promotionRepository = getPromotionRepository(configFileReader);
        ProductRepository productRepository = getProductRepository(configFileReader, promotionRepository);

        StoreController storeController = getStoreController(productRepository);
        storeController.run();
    }

    private static PromotionRepository getPromotionRepository(ConfigFileReader configFileReader) {
        PromotionRepository promotionRepository = new PromotionRepository();
        PromotionsFileInitializer promotionsFileInitializer = new PromotionsFileInitializer(configFileReader,
                promotionRepository);
        promotionsFileInitializer.initialize(PROMOTION_MD_PATH);
        return promotionRepository;
    }

    private static ProductRepository getProductRepository(ConfigFileReader configFileReader,
                                                          PromotionRepository promotionRepository) {
        ProductRepository productRepository = new ProductRepository();
        ProductsFileInitializer productsFileInitializer = new ProductsFileInitializer(configFileReader,
                productRepository,
                promotionRepository);
        productsFileInitializer.initialize(PRODUCT_MD_PATH);
        return productRepository;
    }

    private static StoreController getStoreController(ProductRepository productRepository) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        OrderService orderService = new OrderService(productRepository);
        ProductService productService = new ProductService(productRepository);
        return new StoreController(inputView, outputView, orderService, productService);
    }
}
