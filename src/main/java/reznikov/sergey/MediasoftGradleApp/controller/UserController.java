package reznikov.sergey.MediasoftGradleApp.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reznikov.sergey.MediasoftGradleApp.helper_model.SearchRequest;
import reznikov.sergey.MediasoftGradleApp.helper_model.UserResponse;
import reznikov.sergey.MediasoftGradleApp.repo.UserRepo;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepo userRepo;

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

}
