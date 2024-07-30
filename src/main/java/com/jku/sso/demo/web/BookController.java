package com.jku.sso.demo.web;

import com.jku.sso.demo.persistence.model.Book;
import com.jku.sso.demo.persistence.repo.BookRepository;
import com.jku.sso.demo.web.exception.BookIdMismatchException;
import com.jku.sso.demo.web.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_admin_books')")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        Book book1 = bookRepository.save(book);
        return book1;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin_books')")
    public void delete(@PathVariable long id) {
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin_books')")
    public Book updateBook(@RequestBody Book book, @PathVariable long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}
