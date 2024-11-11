package store;

import static store.constants.FilePath.PRODUCT_MD_PATH;
import static store.constants.FilePath.PROMOTION_MD_PATH;

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

        PromotionRepository promotionRepository = new PromotionRepository();
        PromotionsFileInitializer promotionsFileInitializer = new PromotionsFileInitializer(configFileReader, promotionRepository);
        promotionsFileInitializer.initialize(PROMOTION_MD_PATH);

        ProductRepository productRepository = new ProductRepository();
        ProductsFileInitializer productsFileInitializer = new ProductsFileInitializer(configFileReader, productRepository, promotionRepository);
        productsFileInitializer.initialize(PRODUCT_MD_PATH);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        OrderService orderService = new OrderService(productRepository);
        ProductService productService = new ProductService(productRepository);
        StoreController storeController = new StoreController(inputView, outputView, orderService, productService);
        storeController.run();
    }
}
