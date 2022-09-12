package com.macv.sprint1.repository;

import com.macv.sprint1.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final List<Product> products=new ArrayList<>();

    public boolean existsWithId(int id){
        return getById(id).isPresent();
    }

    public Optional<Product> getById(int id){
        return products.stream().filter(s-> s.getProductId()== id).findFirst();
    }

    public boolean add(Product product){
        products.add(product);
        return true;
    }
}
