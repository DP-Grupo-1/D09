
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//Requisito 22 punto 3:Media y desviacion estandar de las replies de los commentarios
	@Query("select avg(c.replies.size) from Comment c")
	Double avgRepliesPerComment();

	@Query("select sqrt(sum(c.replies.size*c.replies.size)/count(c.replies.size)" + "-(avg(c.replies.size)*avg(c.replies.size))) " + "from Comment c")
	Double stdevRepliesPerComment();

}
