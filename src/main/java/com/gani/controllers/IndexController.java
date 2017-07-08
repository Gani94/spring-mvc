package com.gani.controllers;

import com.gani.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Gani on 7/4/17.
 */
@Controller
public class IndexController {



    @RequestMapping("/index")
    public String index(){

        return "index";
    }
}
