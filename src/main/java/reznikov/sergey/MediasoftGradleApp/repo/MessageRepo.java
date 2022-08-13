package reznikov.sergey.MediasoftGradleApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.MediasoftGradleApp.model.Message;
import reznikov.sergey.MediasoftGradleApp.model.User;

import java.util.Collection;
import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findMessagesBySenderIsInAndRecipientIsIn(Collection<User> sender, Collection<User> recipient);

}
