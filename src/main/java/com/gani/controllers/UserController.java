package com.gani.controllers;

import com.gani.domain.Product;
import com.gani.domain.User;
import com.gani.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Gani on 7/17/17.
 */

@RequestMapping(value = "/user")
@Service
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/list","/"})
    public String listUsers(Model model){

        model.addAttribute("users",userService.listAll());
        return "user/list";
    }

    @RequestMapping("/show/{id}")
    public String getUser(@PathVariable Integer id, Model model){

        model.addAttribute("user",userService.getById(id));
        return "user/show";
    }

    @RequestMapping(value="/edit/{id}")
    public String modifyUser(@PathVariable int id, Model model){

        model.addAttribute("user",userService.getById(id));
        return "user/userform";
    }

    @RequestMapping(value = "/new")
    public String newUser(Model model){

        model.addAttribute("user",new User());

        return "user/userform";
    }


    @RequestMapping( method= RequestMethod.POST)
    public String createOrUpdateUser(User user){

        User savedUser = userService.createOrUpdate(user);

        return "redirect:/user/show/"+savedUser.getId();
    }

    @RequestMapping(value="/delete/{id}")
    public String deleteuser(@PathVariable int id){
        userService.delete(id);

        return "redirect:/user/list";
    }
}
