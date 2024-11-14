package store.repository;

import java.util.HashMap;
import java.util.Map;
import store.domain.Promotion;

public class PromotionRepository {

    private final Map<String, Promotion> promotions = new HashMap<>();

    public void save(String name, Promotion promotion) {
        promotions.put(name, promotion);
    }

    public Promotion findByKey(String name) {
        Promotion findPromotion = promotions.get(name);

        if (findPromotion == null) {
            throw new IllegalArgumentException();
        }

        return findPromotion;
    }

    public boolean containsKey(String name) {
        return promotions.containsKey(name);
    }
}
