package reznikov.sergey.MediasoftGradleApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.MediasoftGradleApp.helper_model.MessageRequest;
import reznikov.sergey.MediasoftGradleApp.helper_model.MessageResponse;
import reznikov.sergey.MediasoftGradleApp.model.Emoji;
import reznikov.sergey.MediasoftGradleApp.model.Message;
import reznikov.sergey.MediasoftGradleApp.model.User;
import reznikov.sergey.MediasoftGradleApp.repo.MessageRepo;
import reznikov.sergey.MediasoftGradleApp.repo.UserRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final static int PAGE_SIZE = 10;

    MessageRepo messageRepo;
    UserRepo userRepo;

    public MessageController(@Autowired MessageRepo messageRepo,
                             @Autowired UserRepo userRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/dialog/emoji")
    ResponseEntity<Object> addEmoji(
            @RequestBody(required = false) MessageRequest messageRequest) {

        if (messageRequest.getEmoji() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no emoji found");
        }

        Emoji emoji;
        try {
            emoji = Emoji.valueOf(messageRequest.getEmoji());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("emoji can not be parsed");
        }

        Message message = messageRepo
                .findById(messageRequest.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Message is not found"));

        message.setEmoji(emoji);
        messageRepo.flush();

        return ResponseEntity.ok("ok");
    }


    @PostMapping("/dialog")
    @SendTo("/broadcast/m")
    ResponseEntity<Object> createMessage(
            @RequestBody(required = false) MessageRequest messageRequest,
            @AuthenticationPrincipal User curUser
    ) throws UsernameNotFoundException {

        User recipient = userRepo
                .findByUsername(messageRequest.getRecipientName())
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        Message message = new Message(messageRequest.getText(), curUser, recipient);

        message = messageRepo.save(message);
        messageRepo.flush();
        return ResponseEntity.ok(new MessageResponse(message));
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

        List<Message> messages = messageRepo
                .findByRecipientAndSenderIdFromTo(curUser.getId(), recipient.getId(), 0, PAGE_SIZE);

        result.setViewName("dialog");
        map.put("messages", messages.stream().map(MessageResponse::new).collect(Collectors.toList()));
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

        if (!Objects.equals(message.getSender().getId(), curUser.getId())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body("You can not change messages that was sent not by you");
        }

        message.setText(messageRequest.getText());
        messageRepo.flush();

        return ResponseEntity.ok(new MessageResponse(message));
    }


    @DeleteMapping("/dialog")
    ResponseEntity<Object> deleteMessage(
            @RequestBody(required = false) MessageRequest messageRequest,
            @AuthenticationPrincipal User curUser
    ) throws UsernameNotFoundException {

        Message message = messageRepo
                .findById(messageRequest.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Message is not found"));

        if (!message.getSender().getId().equals(curUser.getId())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body("You can not change messages that was sent not by you");
        }

        messageRepo.delete(message);
        messageRepo.flush();
        return ResponseEntity.ok("Deleted message");
    }


    @PostMapping("/dialog/loadMessages")
    ResponseEntity<Object> loadMessages(
            @RequestBody(required = false) MessageRequest messageRequest,
            @AuthenticationPrincipal User curUser) throws UsernameNotFoundException {

        User recipient = userRepo
                .findByUsername(messageRequest.getRecipientName())
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        List<Message> messages = messageRepo
                .findByRecipientAndSenderIdFromTo(curUser.getId(), recipient.getId(), messageRequest.getRowNumber(), PAGE_SIZE);

        return ResponseEntity.ok(messages.stream().map(MessageResponse::new).collect(Collectors.toList()));

    }
}
