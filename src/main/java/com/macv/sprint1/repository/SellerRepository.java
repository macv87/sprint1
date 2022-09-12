package com.macv.sprint1.repository;

import com.macv.sprint1.model.Seller;
import org.springframework.stereotype.Repository;

@Repository
public class SellerRepository extends PersonRepository<Seller> {

    public SellerRepository(){
        add(new Seller(11,"Vendedor 1"));
        add(new Seller(12,"Vendedor 2"));
        add(new Seller(13,"Vendedor 3"));
    }
}
