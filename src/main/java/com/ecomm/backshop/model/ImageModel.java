package com.ecomm.backshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "image_model")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private String Type;


    @Column( length = 4048576 )
    @Lob
    private byte[] picByte;

    public ImageModel(){

    }

    public ImageModel(String name, String type, byte[] picByte) {
        this.name = name;
        Type = type;
        this.picByte = picByte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}