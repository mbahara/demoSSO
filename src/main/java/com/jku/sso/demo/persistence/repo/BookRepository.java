package com.jku.sso.demo.persistence.repo;

import com.jku.sso.demo.persistence.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
