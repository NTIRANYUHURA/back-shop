package com.ecomm.backshop.service;

import com.ecomm.backshop.config.JwtRequestFilter;
import com.ecomm.backshop.model.Cart;
import com.ecomm.backshop.model.Product;
import com.ecomm.backshop.model.User;
import com.ecomm.backshop.repository.CartRepository;
import com.ecomm.backshop.repository.ProductRepository;
import com.ecomm.backshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;



    public Product addNewProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts(int pageNumber, String searchKey){
        Pageable pageable = PageRequest.of(pageNumber,12);
        if(searchKey.equals("")){

            return (List<Product>) productRepository.findAll(pageable);
        } else {

            return  (List<Product>) productRepository.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable);

        }



    }

    public Product getProductDetailsById(Integer productId){
        return productRepository.findById(productId).get();
    }

    public void deleteProductDetails(Integer productId){
        productRepository.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId ) {
        if (isSingleProductCheckout  && productId != 0) {

            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productRepository.findById(productId).get();
            list.add(product);
            return list;
        } else {

            String username = JwtRequestFilter.CURRENT_USER;
            User user = userRepository.findById(username).get();
            List<Cart> carts = cartRepository.findByUser(user);

            return carts.stream().map(Cart::getProduct).collect(Collectors.toList());


        }



    }

}
