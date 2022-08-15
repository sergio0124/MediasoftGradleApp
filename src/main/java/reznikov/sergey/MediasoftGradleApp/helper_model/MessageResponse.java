package reznikov.sergey.MediasoftGradleApp.helper_model;

import reznikov.sergey.MediasoftGradleApp.model.Emoji;
import reznikov.sergey.MediasoftGradleApp.model.Message;

import java.sql.Timestamp;
import java.time.Instant;

public class MessageResponse {

    private Long id;

    private String text;

    private Timestamp time = Timestamp.from(Instant.now());

    private Emoji emoji = null;

    private UserResponse sender;

    private UserResponse recipient;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.text = message.getText();
        this.time = message.getTime();
        this.emoji = message.getEmoji();
        this.sender = new UserResponse(message.getSender());
        this.recipient = new UserResponse(message.getRecipient());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    public void setEmoji(Emoji emoji) {
        this.emoji = emoji;
    }

    public UserResponse getSender() {
        return sender;
    }

    public void setSender(UserResponse sender) {
        this.sender = sender;
    }

    public UserResponse getRecipient() {
        return recipient;
    }

    public void setRecipient(UserResponse recipient) {
        this.recipient = recipient;
    }
}
