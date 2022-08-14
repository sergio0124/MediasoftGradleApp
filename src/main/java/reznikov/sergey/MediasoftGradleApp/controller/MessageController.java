package reznikov.sergey.MediasoftGradleApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import javax.persistence.PostUpdate;
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


    @PostMapping("/dialog")
    ResponseEntity<Object> getDialogMessages(
            @RequestBody(required = false) MessageRequest messageRequest,
            @AuthenticationPrincipal User curUser
    ) throws UsernameNotFoundException {

        User recipient = userRepo
                .findByUsername(messageRequest.getRecipientName())
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        Message message = new Message(messageRequest.getText(), curUser, recipient);

        message = messageRepo.save(message);
        messageRepo.flush();
        return ResponseEntity.ok(message);
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
                .findMessagesBySenderIsInAndRecipientIsInOrderByTimeAsc(tmpUsersList, tmpUsersList);

        result.setViewName("dialog");
        map.put("messages", messages);
        map.put("username", curUser.getUsername());
        map.put("recipient_name", recipient_name);
        result.addAllObjects(map);
        return result;
    }


    @PutMapping("/dialog")
    ResponseEntity<Object> updateMessage(
            @RequestBody(required = false) MessageRequest messageRequest,
            @AuthenticationPrincipal User curUser
    ) throws UsernameNotFoundException {

        Message message = messageRepo
                .findById(messageRequest.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Message is not found"));

        if (message.getSender().getId() != curUser.getId()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body("You can not change messages that was sent not by you");
        }

        message.setText(messageRequest.getText());
        messageRepo.flush();

        return ResponseEntity.ok(message);
    }
}
