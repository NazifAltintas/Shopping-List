package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/")
    public String showShoppingList(Model model) {

        model.addAttribute("shoppingList", productService.getAllProduct());
        model.addAttribute("totalPrice", productService.getTotalPrice());
        return "index";
    }

    @GetMapping(path = "/product/{id}")
    public String findProductById(@PathVariable String id, Model model) {

        Integer aInteger;

        try {
            aInteger = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Give a proper a number");
            aInteger = 1;
        }

        Product product = productService.getProductById(aInteger);
        model.addAttribute(product);

        return "redirect:";
    }

    @GetMapping(path = "/cancelProduct/index")
    public String indexPage(Model model) {
        model.addAttribute("shoppingList", productService.getAllProduct());
        model.addAttribute("totalPrice", productService.getTotalPrice());
        return "index";
    }

    @GetMapping(path = "/cancelProduct/{id}")
    public String deleteProductById(@PathVariable String id, Model model) {

        Integer aInteger;

        try {
            aInteger = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Give a proper a number");
            aInteger = 1;
        }

        productService.deleteProductById(aInteger);

        return "redirect:index";
    }


    @GetMapping(path = "/updateProduct/{id}")
    public String updateProductById(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "updatePage";
    }

    @PostMapping(path = "update")
    public String saveUpdating(@ModelAttribute("product") Product product,Model model){
        model.addAttribute("shoppingList", productService.getAllProduct());
        model.addAttribute("totalPrice", productService.getTotalPrice());
        try {

            if (product.getName().equals("")) {
                throw new IllegalStateException("Without using name, new data cannot be created.");

            }
            productService.updateProduct(product.getId(), product);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return "exception";
        }

        return "index";
    }

    @PostMapping(path = "/addProduct")
    public String addItem(Model model, Product newProduct) {

        model.addAttribute("shoppingList", productService.getAllProduct());
        model.addAttribute("totalPrice", productService.getTotalPrice());
//        if (newProduct.getAmount() equals("")) {
//            throw new IllegalStateException("Please enter the amount of product.");
//        }
//        if (newProduct.getPrice()equals("")) {
//            throw new IllegalStateException("Please enter the price of product.");
//        }
        try {

            if (newProduct.getName().equals("")) {
                throw new IllegalStateException("Without using name, new data cannot be created.");

            }
            productService.addNewProduct(newProduct);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return "exception";
        }

        return "redirect:";
    }


}
