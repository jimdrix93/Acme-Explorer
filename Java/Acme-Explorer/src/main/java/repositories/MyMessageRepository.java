/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MyMessage;

@Repository
public interface MyMessageRepository extends JpaRepository<MyMessage, Integer> {

	@Query("select f.messages from Actor a join a.folders f where a.id = ?1 and f.name like %?2%")
	Collection<MyMessage> findAllByFolder(int id, String name);
	@Query("select m from MyMessage m where m.sender.id = ?1 and m.copy = true")
	Collection<MyMessage> findSendedMessagesByActor(int actorId);
	@Query("select m from MyMessage m where m.recipient.id = ?1 and m.copy = false")
	Collection<MyMessage> findRecipientMessagesByActor(int actorId);

}
