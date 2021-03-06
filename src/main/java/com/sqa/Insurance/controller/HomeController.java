package com.sqa.Insurance.controller;

import com.sqa.Insurance.model.User;
import com.sqa.Insurance.repository.UserRepository;
import com.sqa.Insurance.service.HomeSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class HomeController {

    @Autowired
    HomeSevice homeSevice;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().compareTo("admin") == 0){

            List<User> users = homeSevice.getAllUserExceptAdmin();
            long[] amounts = new long[users.size() + 1];
            for(int i = 0; i<users.size(); i++){
                if(users.get(i).getSalary() != null && users.get(i).getType() != null){
                    amounts[i] = (long)(users.get(i).getSalary() * 45/1000);
                    if(users.get(i).getType().compareTo("THAIS") ==0){
                        amounts[i] *= 6;
                    }
                    else{
                        amounts[i] *= 12;
                    }
                }
            }
            model.addAttribute("users", users);
            model.addAttribute("amounts", amounts);
            return "indexAdmin";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user, Model model){

        User userFound = homeSevice.findByUsername(user.getUsername());
        if(userFound == null || userFound.getCccd().compareTo(user.getCccd()) !=0 ||
                userFound.getName().compareTo(user.getName())!=0 ||
                userFound.getPhone().compareTo(user.getPhone())!=0 ){
            model.addAttribute("error", true);
            return "register";
        }
        else if (userFound.getIs_active()== true){
            model.addAttribute("error1", true);
        }
        else{

                model.addAttribute(user);
                return "chooseimg";
            }




        return null;
    }


    @PostMapping("/chooseimg")
    public String chooseimgPost(@ModelAttribute User userReceive, @RequestParam("image_front") MultipartFile multipartFile1,@RequestParam("image_back") MultipartFile multipartFile2) throws IOException {
        User user = homeSevice.findByUsername(userReceive.getUsername());
        user.setIs_active(true);
        String fileName1 =  String.valueOf((int)(Math.random() * 100000)) + StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        user.setImg_front(fileName1);
        String fileName2 =  String.valueOf((int)(Math.random() * 100000)) + StringUtils.cleanPath(multipartFile2.getOriginalFilename());
        user.setImg_back(fileName2);

        user.setPassword(passwordEncoder.encode("123456"));
        User savedUser = homeSevice.saveUser(user);
        String uploadDir = "user-photos/" + savedUser.getId();
        saveFile(uploadDir, fileName1, multipartFile1);
        saveFile(uploadDir, fileName2, multipartFile2);
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = homeSevice.findByUsername(authentication.getName());
        if(user== null) {
            model.addAttribute("error", true);
            return "profile";
        }
        long amount = (long)(user.getSalary() * 45/1000);
        if(user.getType().compareTo("THAIS") ==0){
            amount = amount * 6;
        }
        else{
            amount = amount *12;
        }

        model.addAttribute(user);
        model.addAttribute("amount", String.valueOf(amount)+ " ??");
        return "profile";
    }

    @GetMapping("/payment")
    public String payment(Model model){
        model.addAttribute("user", new User());
        return "payment";
    }

    @PostMapping("/payment")
    public String paymentPost(@ModelAttribute User userReceive, Model model){
        User user = homeSevice.getUserByUsernameAndCCCD(userReceive.getUsername(), userReceive.getCccd());
        if(user== null) {
            model.addAttribute("error", true);
            return "payment";
        }
        long amount = (long)(user.getSalary() * 45/1000);
        if(user.getType().compareTo("THAIS") ==0){
            amount = amount * 6;
        }
        else{
            amount = amount *12;
        }

        model.addAttribute(user);
        model.addAttribute("amount", String.valueOf(amount)+ " ??");
        return "paymentInfo";
    }

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}