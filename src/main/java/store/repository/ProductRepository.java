package store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import store.domain.Product;

public class ProductRepository {
    private HashMap<String, Product> products = new LinkedHashMap<>();

    public void save(String name, Product product) {
        products.put(name, product);
    }

    public Product findByKey(String name) {
        Product findProduct = products.get(name);

        if (findProduct == null) {
            throw new IllegalArgumentException();
        }
        return findProduct;
    }

    public boolean containsKey(String name) {
        return products.containsKey(name);
    }

    public List<Product> getAllProduct() {
        List<Product> allProduct = new ArrayList<>();

        Set<String> names = products.keySet();
        for (String name : names) {
            Product product = products.get(name);
            allProduct.add(product);
        }

        return allProduct;
    }
}
