package reznikov.sergey.MediasoftGradleApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.MediasoftGradleApp.helper_model.SearchRequest;
import reznikov.sergey.MediasoftGradleApp.helper_model.UserAuthRequest;
import reznikov.sergey.MediasoftGradleApp.helper_model.UserResponse;
import reznikov.sergey.MediasoftGradleApp.model.Role;
import reznikov.sergey.MediasoftGradleApp.model.User;
import reznikov.sergey.MediasoftGradleApp.repo.UserRepo;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    UserRepo userRepo;

    public UserController(@Autowired UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/findByWord")
    ResponseEntity<Object> findMarches(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(
                userRepo
                        .findUsersByUsernameContainsIgnoreCase(searchRequest.getText())
                        .stream()
                        .map(UserResponse::new)
                        .collect(Collectors.toList())
        );
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
