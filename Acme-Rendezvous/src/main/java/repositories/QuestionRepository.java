
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Answer;
import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("select q from Question q where q.creator.id = ?1 and q.rendezvous.id = ?2")
	Collection<Question> findAllByPrincipalAndRendezvous(int principalId, int rendezvousId);

	@Query("select q from Question q where q.rendezvous.id = ?1")
	Collection<Question> findAllByRendezvous(int rendezvousId);

	//Requisito 22.1 punto 1: La media de preguntas por rendezvous.
	@Query("select count(q)*1.0/(select count(r) from Rendezvous r) from Question q")
	Double avgQuestionsPerRendezvous();

	@Query("select avg(q.answers.size) from Question q")
	Double avgAnswersPerQuestions();

	@Query("select sqrt(sum(q.answers.size*q.answers.size)/count(q.answers.size)-" + "(avg(q.answers.size)*avg(q.answers.size) ))" + " from Question q")
	Double stdevAnswersPerQuestions();

	@Query("select q from Question q where ?1 in elements(q.answers) and q.rendezvous.id = ?2")
	Question findQuestionByAnswer(Answer answer, int rendezvousId);
}
