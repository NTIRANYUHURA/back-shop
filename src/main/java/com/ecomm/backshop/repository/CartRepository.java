package com.ecomm.backshop.repository;

import com.ecomm.backshop.model.Cart;
import com.ecomm.backshop.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    public List<Cart> findByUser(User user);
}
