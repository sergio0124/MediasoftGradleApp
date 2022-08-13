package reznikov.sergey.MediasoftGradleApp.helper_model;

import reznikov.sergey.MediasoftGradleApp.model.User;

public class UserResponse {

    String username;
    Long id;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.id = user.getId();
    }

    public UserResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
