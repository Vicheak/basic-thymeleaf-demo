package com.vicheak.core.controller;

import com.vicheak.core.dto.CreateProductDto;
import com.vicheak.core.model.Category;
import com.vicheak.core.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ProductController {

    public List<Product> products;

    public List<Category> categories;

    public ProductController() {
        Category category = Category.builder()
                .id(1)
                .name("Accessory")
                .description("Accessory description")
                .build();

        products = new ArrayList<>();

        categories = new ArrayList<>();

        categories.add(category);

        categories.add(Category.builder()
                .id(2)
                .name("Electronic")
                .description("Electronic description")
                .build());

        categories.add(Category.builder()
                .id(3)
                .name("Soft")
                .description("Soft description")
                .build());

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
                                    @RequestParam(required = false) String name,
                                    ModelMap modelMap) {
        Optional<Product> productOptional = products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst();
        productOptional.ifPresent(product -> modelMap.addAttribute("product", product));
        return "product/productDetail";
    }

    @GetMapping("/product/form")
    public String viewProductForm(CreateProductDto createProductDto,
                                  ModelMap modelMap) {
        modelMap.addAttribute("createProductDto", createProductDto);
        modelMap.addAttribute("categories", categories);
        return "product/productForm";
    }

    //PRG = Post, Redirect, Get
    @PostMapping("/product/create")
    public String createNewProduct(@Valid CreateProductDto createProductDto,
                                   BindingResult bindingResult,
                                   ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            //System.out.println("Has errors!");

            modelMap.addAttribute("categories", categories);
            return "product/productForm";
        }

        //System.out.println(createProductDto);

        //find category
        Category category = null;
        if (Objects.nonNull(createProductDto.categoryId())) {
            Optional<Category> categoryOptional = categories.stream()
                    .filter(cat -> cat.getId().equals(createProductDto.categoryId()))
                    .findFirst();

            if (categoryOptional.isPresent()) category = categoryOptional.get();
        }

        //save dto to the list
        Product newProduct = Product.builder()
                .id(products.get(products.size() - 1).getId() + 1L)
                .name(createProductDto.name())
                .description(createProductDto.description())
                .price(createProductDto.price())
                .category(Objects.nonNull(category) ? category : null)
                .build();

        products.add(newProduct);

        return "redirect:/product";
    }

}
