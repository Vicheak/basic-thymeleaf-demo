package com.vicheak.core.controller;

import com.vicheak.core.model.Category;
import com.vicheak.core.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ProductController {

    public List<Product> products;

    public ProductController() {
        Category category = Category.builder()
                .id(1)
                .name("Accessory")
                .description("Accessory description")
                .build();

        products = new ArrayList<>();

        products.add(Product.builder()
                .id(1001L)
                .name("Magic Mouse")
                .description("Magic Mouse description")
                .price(130.0)
                .category(category)
                .build());

        products.add(Product.builder()
                .id(1002L)
                .name("ASUS Gaming")
                .description("ASUS Gaming description")
                .price(1800.0)
                .build());

        products.add(Product.builder()
                .id(1003L)
                .name("Gaming Chair")
                .description("Gaming Chair description")
                .price(270.0)
                .category(category)
                .build());

        products.add(Product.builder()
                .id(1004L)
                .name("Electronic Fan")
                .description("Electronic Fan description")
                .price(400.0)
                .category(category)
                .build());

        products.add(Product.builder()
                .id(1005L)
                .name("Laptop Container")
                .description("Laptop Container description")
                .price(15.0)
                .category(category)
                .build());
    }

    @GetMapping("/product")
    public String viewProduct(ModelMap modelMap) {
        modelMap.addAttribute("products", products);
        return "product/product";
    }

    @GetMapping("/product/viewTable")
    public String viewProductTable(ModelMap modelMap) {
        modelMap.addAttribute("products", products);
        return "product/productTable";
    }

    @GetMapping("/product/{id}")
    public String viewProductDetail(@PathVariable Long id,
                                    @RequestParam String name,
                                    ModelMap modelMap) {
        Optional<Product> productOptional = products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst();
        productOptional.ifPresent(product -> modelMap.addAttribute("product", product));
        return "product/productDetail";
    }

}
