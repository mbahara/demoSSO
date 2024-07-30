package com.jku.sso.demo;

import com.jku.sso.demo.persistence.model.Book;
import com.jku.sso.demo.persistence.model.Product;
import com.jku.sso.demo.persistence.repo.BookRepository;
import com.jku.sso.demo.persistence.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final BookRepository bookRepository;

    public DataLoader(ProductRepository userRepository, BookRepository bookRepository) {
        this.productRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load initial data into the database
        productRepository.save(new Product("Apple iPhone 14", "Latest model with A15 Bionic chip", 999.99));
        productRepository.save(new Product("Samsung Galaxy S22", "Flagship model with Exynos 2200", 899.99));
        productRepository.save(new Product("Sony WH-1000XM4", "Noise-cancelling wireless headphones", 349.99));
        productRepository.save(new Product("Dell XPS 13", "13-inch laptop with Intel i7 processor", 1299.99));
        productRepository.save(new Product("Apple MacBook Pro", "16-inch laptop with M1 chip", 2499.99));
        productRepository.save(new Product("Nike Air Max 270", "Comfortable running shoes", 150.00));
        productRepository.save(new Product("Adidas Ultraboost", "High-performance running shoes", 180.00));
        productRepository.save(new Product("Sony PlayStation 5", "Next-gen gaming console", 499.99));
        productRepository.save(new Product("Microsoft Xbox Series X", "Powerful gaming console", 499.99));
        productRepository.save(new Product("Apple Watch Series 7", "Smartwatch with advanced health features", 399.99));

        bookRepository.save(new Book("To Kill a Mockingbird", "978-0-06-112008-4", 15.99));
        bookRepository.save(new Book("1984", "978-0-452-28423-4", 9.99));
        bookRepository.save(new Book("The Great Gatsby", "978-0-7432-7356-5", 10.99));
        bookRepository.save(new Book("The Catcher in the Rye", "978-0-316-76948-0", 8.99));
        bookRepository.save(new Book("Moby-Dick", "978-0-14-243724-7", 12.99));
        bookRepository.save(new Book("Pride and Prejudice", "978-0-14-143951-8", 7.99));
        bookRepository.save(new Book("War and Peace", "978-0-14-044793-4", 13.99));
        bookRepository.save(new Book("The Odyssey", "978-0-14-026886-7", 11.99));
        bookRepository.save(new Book("Crime and Punishment", "978-0-14-044913-6", 9.99));
        bookRepository.save(new Book("The Hobbit", "978-0-618-00221-3", 14.99));
    }
}