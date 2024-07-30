package com.jku.sso.demo.persistence.repo;

import com.jku.sso.demo.persistence.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>
{
}
