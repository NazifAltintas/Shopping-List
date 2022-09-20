package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {

        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        }
        return new Product();
    }

    public String deleteProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException(id + " does not exist ");
        } else {
            productRepository.deleteById(id);
            return "Product with id is " + id + " is successfully deleted";
        }

    }

    public Product updateProduct(Integer id, Product newProduct) {

        Product existingProductById = productRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " does not exist"));

        //To update name

        if (newProduct.getName() == null) {
            throw new IllegalArgumentException("Name is mandatory to update data...");
        } else if (!existingProductById.getName().equals(newProduct.getName())) {
            existingProductById.setName(newProduct.getName());
        }

        //To update price

        if (newProduct.getPrice() == null) {
            throw new IllegalArgumentException("Price is mandatory to update data...");
        } else if (!(existingProductById.getPrice() == newProduct.getPrice())) {
            existingProductById.setPrice(newProduct.getPrice());
        }

        //To update amount

        //To update price

        if (newProduct.getAmount() == null) {
            throw new IllegalArgumentException("Amount is mandatory to update data...");
        } else if (!(existingProductById.getAmount() == newProduct.getAmount())) {
            existingProductById.setAmount(newProduct.getAmount());
        }

        return productRepository.save(existingProductById);
    }

    public String addNewProduct(Product newProduct) {
        Optional<Product> existingProductByName = productRepository.findProductByName(newProduct.getName());

        if (existingProductByName.isPresent()) {
            throw new IllegalStateException("Product name exist, enter different product....");
        }
        if (newProduct.getName() == null) {
            throw new IllegalStateException("Without using name, new data cannot be created.");
        }
        if (newProduct.getAmount() == null) {
            throw new IllegalStateException("Please enter the amount of product.");
        }
        if (newProduct.getPrice() == null) {
            throw new IllegalStateException("Please enter the price of product.");
        }
//        //to create connection with DB, look at jdbc
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nazif", "root", "nazif2022");
//        Statement st = con.createStatement();
//
//        String sqlQuery = "SELECT MAX(id) FROM product";
//        ResultSet result = st.executeQuery(sqlQuery);
//
//        Integer maxId = 0;
//
//        while (result.next()) {
//            maxId = result.getInt(1);
//        }
//
//        newProduct.setId(maxId + 1);
        productRepository.save(newProduct);

        return "Product is added.";

    }

    public Product getProductByName(String name) {

        return productRepository.findProductByName(name).get();
    }


    public Double getTotalPrice() {
        Double sum = productRepository.findAll().stream()
                .map(x -> x.totalPrice())
                .reduce(0.0, Double::sum);
        sum= (Double.valueOf(Math.round(100*sum)))/100;
        return sum;
    }


}

