package reznikov.sergey.MediasoftGradleApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.MediasoftGradleApp.helper_model.UserAuthRequest;
import reznikov.sergey.MediasoftGradleApp.model.Role;
import reznikov.sergey.MediasoftGradleApp.model.User;
import reznikov.sergey.MediasoftGradleApp.repo.UserRepo;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/hello")
    ModelAndView getHello() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home_page");
        modelAndView.addObject("users", userRepo.findAll());
        return modelAndView;
    }

    @GetMapping("/")
    String redirectionToHello() {
        return "redirect:/hello";
    }

    @GetMapping("/registration")
    String goRegister() {
        return "registration";
    }

    @PostMapping("/registration")
    @ResponseBody
    ModelAndView registerUser(@RequestBody UserAuthRequest userAuthRequest) {
        ModelAndView result = new ModelAndView();
        Map<String, Object> map = new HashMap<>();

        User newUser = new User();
        if (userRepo.findByUsername(userAuthRequest.getUsername()).isPresent()) {
            result.setViewName("registration");
            map.put("error", "User with this username exists");
            result.addAllObjects(map);
            return result;
        }

        newUser.setPassword(userAuthRequest.getPassword());
        newUser.setUsername(userAuthRequest.getUsername());
        newUser.setRoles(new HashSet<>(Collections.singleton(Role.USER)));

        userRepo.save(newUser);
        result.setViewName("login");
        map.put("status", "Username saved");
        result.addAllObjects(map);
        return result;
    }
}
