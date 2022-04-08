package com.sqa.Insurance.controller;

import com.sqa.Insurance.model.User;
import com.sqa.Insurance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public String profile(@PathVariable("id") long id, Model model){
        User user = userRepository.findById(id);
        model.addAttribute(user);
        return "profile";
    }

    @GetMapping("/user/create")
    public String createUser(Model model){
        model.addAttribute("user", new User());
        return "userCreate";
    }

    @PostMapping("/user")
    public String storeUser(@ModelAttribute User user, Model model){
        try {
            if(user.getName() == null || user.getSalary() == null || user.getAddress() == null ||
                    user.getPhone() == null || user.getCccd() == null || user.getPayment_date() == null || user.getDue_date() == null)
            {
                model.addAttribute("error", "Please fill all field ");
                return "userCreate";
            }
            Pattern p1 = Pattern.compile("^0\\d{9}$");
            Matcher m1 = p1.matcher(user.getPhone());
            if(!m1.matches()){
                model.addAttribute("error", "Phone validate");
                return "userCreate";
            }

            Pattern p2 = Pattern.compile("^\\d{7,9}$");
            Matcher m2 = p2.matcher(String.valueOf(user.getSalary()));
            if(!m2.matches()){
                model.addAttribute("error", "Salary validate");
                return "userCreate";
            }

            Pattern p3 = Pattern.compile("^\\d{9}$|^\\d{12}$");
            Matcher m3 = p3.matcher(String.valueOf(user.getCccd()));
            if(!m3.matches()){
                model.addAttribute("error", "CCCD validate");
                return "userCreate";
            }

            user.setIs_active(false);
            user.setUsername(user.getType() + String.valueOf((int)(Math.random()*100000)+10000) + String.valueOf((int)(Math.random()*100000)+10000));
            userRepository.save(user);
            return "redirect:/";

        }catch (Exception e){
            model.addAttribute("error", "Account exist");
            return "userCreate";
        }
    }

}