package reznikov.sergey.MediasoftGradleApp.helper_model;

public class MessageRequest {

    private Long id;
    private String text;
    private String recipientName;
    private String emoji;
    private Integer rowNumber;
    private Integer neededToLoad;

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getNeededToLoad() {
        return neededToLoad;
    }

    public void setNeededToLoad(Integer neededToLoad) {
        this.neededToLoad = neededToLoad;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
}
