package reznikov.sergey.MediasoftGradleApp.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
