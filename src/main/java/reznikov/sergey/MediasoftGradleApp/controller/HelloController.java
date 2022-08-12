package reznikov.sergey.MediasoftGradleApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.MediasoftGradleApp.helper_model.UserAuthModel;
import reznikov.sergey.MediasoftGradleApp.model.Role;
import reznikov.sergey.MediasoftGradleApp.model.User;
import reznikov.sergey.MediasoftGradleApp.repo.UserRepo;

import java.util.Collections;
import java.util.HashSet;

@Controller
public class HelloController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/hello")
    ModelAndView getHello(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        modelAndView.addObject("users", userRepo.findAll());
        return modelAndView;
    }

    @GetMapping("/")
    String redirectionToHello(){
        return "redirect:/hello";
    }

    @GetMapping("/registration")
    String goRegister(){
        return "registration";
    }

    @PostMapping("/registration")
    @ResponseBody
    ResponseEntity<Object> registerUser(@RequestBody UserAuthModel userAuthModel){
        User newUser = new User();
        if(userRepo.findByUsername(userAuthModel.getUsername()).isPresent()){
            return ResponseEntity.ok().body("User with this username exists");
        }

        newUser.setPassword(userAuthModel.getPassword());
        newUser.setUsername(userAuthModel.getUsername());
        newUser.setRoles(new HashSet<>(Collections.singleton(Role.USER)));

        Object result = userRepo.save(newUser);
        return ResponseEntity.ok().body(result);
    }
}
