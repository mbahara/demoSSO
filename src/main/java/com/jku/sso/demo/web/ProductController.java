package com.jku.sso.demo.web;

import com.jku.sso.demo.persistence.model.Product;
import com.jku.sso.demo.persistence.repo.ProductRepository;
import com.jku.sso.demo.web.exception.ProductIdMismatchException;
import com.jku.sso.demo.web.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product findOne(@PathVariable long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_admin_products')")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        Product product1 = productRepository.save(product);
        return product1;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin_products')")
    public void delete(@PathVariable long id) {
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin_products')")
    public Product updateProduct(@RequestBody Product product, @PathVariable long id) {
        if (product.getId() != id) {
            throw new ProductIdMismatchException();
        }
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productRepository.save(product);
    }
}
