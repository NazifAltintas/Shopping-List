package com.example;

import javax.persistence.*;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer amount;

    public Product() {

    }

    public Product(String name, Double price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Double totalPrice() {
        Double totalPrice;
        try {
            totalPrice = (Double.valueOf(Math.round(100 * getPrice() * getAmount()))) / 100;
        } catch (NumberFormatException e) {
            System.out.println("Give a proper a number");
            totalPrice = 0.0;
        }
        return totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

