package com.ecomm.backshop.service;

import com.ecomm.backshop.config.JwtRequestFilter;
import com.ecomm.backshop.model.Cart;
import com.ecomm.backshop.model.Product;
import com.ecomm.backshop.model.User;
import com.ecomm.backshop.repository.CartRepository;
import com.ecomm.backshop.repository.ProductRepository;
import com.ecomm.backshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService{


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;


    public void deleteCartItem(Integer cartId) {
        cartRepository.deleteById(cartId);

    }


    public Cart addToCart(Integer productId) {
        Product product = productRepository.findById(productId).get();
        String userName = JwtRequestFilter.CURRENT_USER;
        User user = null;
        if(userName != null) {
            user = userRepository.findById(userName).get();
        }

        List<Cart> cartList = cartRepository.findByUser(user);
        List<Cart> filteredList = cartList.stream().filter( x-> x.getProduct().getProductId() == productId).collect(Collectors.toList());

        if(!filteredList.isEmpty()){
            return null;
        }


        if(product != null && user != null){

            Cart cart = new Cart(product, user);
            return cartRepository.save(cart);

        }

        return null;

    }


    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userRepository.findById(username).get();
        return cartRepository.findByUser(user);
    }
}
