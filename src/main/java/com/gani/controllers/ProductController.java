package com.gani.controllers;

import com.gani.domain.Product;
import com.gani.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Gani on 7/5/17.
 */
@RequestMapping(value = "/product")
@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping({"/list","/"})
    public String listProducts(Model model){

        model.addAttribute("products",productService.listAll());

        return "product/list";
    }

    @RequestMapping("/show/{id}")
    public String getProduct(@PathVariable int id, Model model){

        model.addAttribute("product",productService.getById(id));
        return "product/show";
    }

    @RequestMapping(value = "/new")
    public String newProduct(Model model){

        model.addAttribute("product",new Product());

        return "product/productform";
    }

    @RequestMapping( method= RequestMethod.POST)
    public String createOrUpdateProduct(Product product){

        Product savedproduct = productService.createOrUpdate(product);

        return "redirect:/product/show/"+savedproduct.getId();
    }


    @RequestMapping(value="/edit/{id}")
    public String modifyProduct(@PathVariable int id, Model model){

        model.addAttribute("product",productService.getById(id));
        return "product/productform";
    }

    @RequestMapping(value="/delete/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.delete(id);

        return "redirect:/product/list";
    }
}
