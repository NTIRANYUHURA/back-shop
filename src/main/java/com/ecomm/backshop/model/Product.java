package com.ecomm.backshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Integer productId;

    private String productName;

    @Column(length=2000)
    private String productDescription;

    private Double productPrice;



    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL  )
    @JoinTable(name = "product_images",
            joinColumns = {
                    @JoinColumn(name = "product_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id")

            })
    private Set<ImageModel> productImages;



}
