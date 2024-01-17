package com.ecomm.backshop.service;

import com.ecomm.backshop.config.JwtRequestFilter;
import com.ecomm.backshop.model.*;
import com.ecomm.backshop.repository.CartRepository;
import com.ecomm.backshop.repository.OrderDetailRepository;
import com.ecomm.backshop.repository.ProductRepository;
import com.ecomm.backshop.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "placed";

    private static final String KEY = "rzp_test_O0JcRUjcZuy6FV";
    private static final String KEY_SECRET = "CdCDdjNDbO4utdswv8rYdaeN";
    private static final String CURRENCY = "INR";
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;


    public List<OrderDetail> getAllOrderDetails(String status){
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(status.equals("All")){

            orderDetailRepository.findAll().forEach(
                    x-> orderDetails.add(x)
            );

        } else {

            orderDetailRepository.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );

        }



        return orderDetails;
    }


    public List<OrderDetail> getOrderDetails(){

        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userRepository.findById(currentUser).get();

        return orderDetailRepository.findByUser(user);

    }

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout){

        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o : productQuantityList) {
            Product product = productRepository.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userRepository.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(

                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductPrice() * o.getQuantity(),
                    product,
                    user,
                    orderInput.getTransactionId()
            );

            if(!isSingleProductCheckout){
                List<Cart> carts =  cartRepository.findByUser(user);
                carts.stream().forEach(x-> cartRepository.deleteById(x.getCartId()));

            }

            orderDetailRepository.save(orderDetail);

        }



    }

    public void markOrderAsDelivered(Integer orderId) {

        OrderDetail orderDetail =  orderDetailRepository.findById(orderId).get();

        if(orderDetail != null){
            orderDetail.setOrderStatus("commande livré");
            orderDetailRepository.save(orderDetail);
        }

    }

    public TransactionDetails createTransaction(Double amount) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100) );
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

            Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
            return transactionDetails;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
        return transactionDetails;
    }
}
