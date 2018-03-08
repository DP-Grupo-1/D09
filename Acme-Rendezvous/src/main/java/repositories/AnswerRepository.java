package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{

	@Query("select a from Answer a where a.answerer.id = ?1")
	Collection<Answer>findAllByAnswerer(int userId);
	
	@Query("select q.answers from Question q where q.rendezvous.id = ?1")
	Collection<Answer>findAllByRendezvous(int rendezvousId);
}
