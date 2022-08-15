package reznikov.sergey.MediasoftGradleApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reznikov.sergey.MediasoftGradleApp.model.Message;
import reznikov.sergey.MediasoftGradleApp.model.User;

import java.util.Collection;
import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findMessagesBySenderIsInAndRecipientIsInOrderByTimeAsc(Collection<User> sender, Collection<User> recipient);

    @Query(value =
            "select * from (select * from message m    \n" +
                    "              WHERE (m.recipient_id = ?2 OR m.sender_id = ?2) \n" +
                    "                  AND (m.recipient_id = ?1 OR m.sender_id = ?1)\n" +
                    "        ORDER BY time DESC OFFSET (?3) ROWS FETCH NEXT (?4) ROWS ONLY) as fdfd\n" +
                    "ORDER BY time ASC",
            nativeQuery = true
    )
    List<Message> findByRecipientAndSenderIdFromTo(long senderId, long recipientId, int skip, int rowNumber);
}
