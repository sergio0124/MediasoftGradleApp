package reznikov.sergey.MediasoftGradleApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.MediasoftGradleApp.helper_model.MessageRequest;
import reznikov.sergey.MediasoftGradleApp.model.Message;
import reznikov.sergey.MediasoftGradleApp.model.User;
import reznikov.sergey.MediasoftGradleApp.repo.MessageRepo;
import reznikov.sergey.MediasoftGradleApp.repo.UserRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserRepo userRepo;


    @PostMapping("/create")
    @ResponseBody
    ResponseEntity<Object> createMessage(
            @RequestBody MessageRequest messageRequest) throws UsernameNotFoundException {

        User sender = userRepo
                .findById(messageRequest.getSenderId())
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        User recipient = userRepo
                .findById(messageRequest.getRecipientId())
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        Message message = new Message(messageRequest.getText(), sender, recipient);

        messageRepo.save(message);
        return ResponseEntity.ok("Message is created");
    }


    @GetMapping("/dialog")
    ModelAndView getDialogMessages(
            @RequestParam("recipient_name") String recipient_name,
            @AuthenticationPrincipal User curUser
    ) {

        ModelAndView result = new ModelAndView();
        Map<String, Object> map = new HashMap<>();

        if (recipient_name == null) {
            map.put("error", "no data in request");
            result.addAllObjects(map);
            return result;
        }

        User recipient = userRepo
                .findByUsername(recipient_name)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        var tmpUsersList = List.of(curUser, recipient);
        List<Message> messages = messageRepo
                .findMessagesBySenderIsInAndRecipientIsIn(tmpUsersList, tmpUsersList);

        result.setViewName("dialog");
        map.put("messages", messages);
        result.addAllObjects(map);
        return result;
    }
}
