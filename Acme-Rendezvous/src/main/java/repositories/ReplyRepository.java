
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Reply;
import domain.User;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	@Query("select u from User u where ?1 in elements(u.replies)")
	User findUserByReply(Reply reply);
}
