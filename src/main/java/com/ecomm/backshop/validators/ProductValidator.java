package com.ecomm.backshop.validators;

import com.ecomm.backshop.model.Product;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductValidator {
    public static List<String> validate(Product product) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasLength(product.getProductName())) {
            errors.add("veiller renseigner le nom du produit");
        }

        if (!StringUtils.hasLength(product.getProductDescription())) {
            errors.add("veiller renseigner la description ");
        }

        if (!StringUtils.hasLength(String.valueOf(product.getProductPrice()))) {
            errors.add("veiller renseigner le prix");
        }




        return errors;

    }

}
