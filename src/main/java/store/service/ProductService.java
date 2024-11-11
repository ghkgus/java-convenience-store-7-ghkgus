package store.service;

import static store.domain.GiftItemType.APPLIED_PROMOTION_WITHOUT_GIFT;
import static store.domain.GiftItemType.APPLIED_PROMOTION_WITH_GIFT;
import static store.domain.GiftItemType.NO_PROMOTION;
import static store.domain.GiftItemType.PARTIAL_PROMOTION_WITH_REGULAR_PRICE;

import java.util.ArrayList;
import java.util.List;
import store.domain.GiftItemType;
import store.domain.Product;
import store.domain.Receipt;
import store.dto.CurrentStockInfo;
import store.dto.UserReceipt;
import store.dto.UserOrderItem;
import store.repository.ProductRepository;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<CurrentStockInfo> getCurrentStockInfos() {
        List<Product> allProduct = productRepository.getAllProduct();

        List<CurrentStockInfo> currentStockInfos = new ArrayList<>();
        for (Product product : allProduct) {
            currentStockInfos.add(product.getCurrentStockInfo());
        }

        return currentStockInfos;
    }

    public long calculateTotalPrice(List<UserOrderItem> orderItems) {
        long totalPrice = 0;
        for (UserOrderItem orderItem : orderItems) {
            Product product = productRepository.findByKey(orderItem.getName());
            CurrentStockInfo currentStockInfo = product.getCurrentStockInfo();

            totalPrice += (long) orderItem.getTotalQuantity() * currentStockInfo.getPrice();
        }
        return totalPrice;
    }

    public long calculateTotalQuantity(List<UserOrderItem> orderItems) {
        long totalQuantity = 0;
        for (UserOrderItem orderItem : orderItems) {
            Product product = productRepository.findByKey(orderItem.getName());

            totalQuantity += (long) orderItem.getTotalQuantity();
        }
        return totalQuantity;
    }

    public long calculatePromotionDiscount(List<UserOrderItem> orderItems) {
        int discountPrice = 0;
        for (UserOrderItem orderItem : orderItems) {
            Product product = productRepository.findByKey(orderItem.getName());
            CurrentStockInfo currentStockInfo = product.getCurrentStockInfo();

            discountPrice += (orderItem.getGiftQuantity() * currentStockInfo.getPrice());
        }
        return discountPrice;
    }

    public int calculateMembershipDiscount(List<UserOrderItem> orderItems) {
        int nonPromotionTotalPrice = 0;
        for (UserOrderItem orderItem : orderItems) {
            Product product = productRepository.findByKey(orderItem.getName());
            CurrentStockInfo currentStockInfo = product.getCurrentStockInfo();
            if (orderItem.getType() == NO_PROMOTION) {
                nonPromotionTotalPrice += (orderItem.getTotalQuantity() * currentStockInfo.getPrice());
            }
        }
        return calculateDiscountForNonPromotion(nonPromotionTotalPrice);
    }

    public List<UserReceipt> calculateOrderProductPrice(List<UserOrderItem> orderItems) {
        List<UserReceipt> receipt = new ArrayList<>();
        for (UserOrderItem orderItem : orderItems) {
            Product product = productRepository.findByKey(orderItem.getName());
            CurrentStockInfo currentStockInfo = product.getCurrentStockInfo();
            int totalPricePerProduct = orderItem.getTotalQuantity() * currentStockInfo.getPrice();
            Receipt itemReceipt = new Receipt(orderItem, totalPricePerProduct);
            receipt.add(itemReceipt.getOderItem());
        }
        return receipt;
    }

    public void updateStockAfterSale(List<UserOrderItem> orderItems) {
        for (UserOrderItem orderItem : orderItems) {
            Product product = productRepository.findByKey(orderItem.getName());
            int totalQuantity = orderItem.getTotalQuantity();
            GiftItemType type = orderItem.getType();
            reduceStockQuantity(product, totalQuantity, type);
        }
    }

    private int calculateDiscountForNonPromotion(int nonPromotionTotalPrice) {
        int discountPrice = 0;
        discountPrice = (int) (nonPromotionTotalPrice * 0.3);
        if (discountPrice > 8000) {
            discountPrice = 8000;
        }
        return discountPrice;
    }

    private void reduceStockQuantity(Product product, int totalQuantity, GiftItemType type) {
        if (type == NO_PROMOTION) {
            product.updateStockForNonPromotion(totalQuantity);
        }
        if (type == PARTIAL_PROMOTION_WITH_REGULAR_PRICE) {
            product.updateStockForPartialPromotion(totalQuantity);
        }
        if (type == APPLIED_PROMOTION_WITH_GIFT || type == APPLIED_PROMOTION_WITHOUT_GIFT) {
            product.updateStockForPromotion(totalQuantity);
        }
    }
}
