
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnswerRepository;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class AnswerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnswerRepository	answerRepository;

	@Autowired
	private QuestionService		questionService;
	@Autowired
	private RendezvousService		rendezvousService;
	@Autowired
	private UserService			userService;


	// Constructors -----------------------------------------------------------

	public AnswerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Answer create(final int questionId) {
		final Answer result;
		result = new Answer();
		return result;
	}

	public Answer findOne(final int answerId) {
		final Answer answer = this.answerRepository.findOne(answerId);
		return answer;
	}

	public Answer save(final Answer answer, final Question question) {
		try {
			final User principal = this.userService.findByPrincipal();
			answer.setAnswerer(principal);
			final Answer saved = this.answerRepository.save(answer);
			final Collection<Answer> answers = question.getAnswers();
			answers.add(answer);
			question.setAnswers(answers);
			this.questionService.save(question);
			Rendezvous rendezvous = question.getRendezvous();
			if(!(rendezvous.getAttendants().contains(principal))){
				this.rendezvousService.rsvp(rendezvous);
			}
			return saved;
		} catch(final Exception oops) {
			System.out.println(oops.getMessage());
			return null;
		}
	}
	
	public void saveAll(final List<Answer> answers, final Collection<Question> questions) {
		for(int i = 0;i < questions.size();i++){
			Question question = (Question) questions.toArray()[i];
			Answer created = this.create(question.getId());
			String answer = answers.get(i).getWritten();
			created.setWritten(answer);
			this.save(created, question);
		}
	}

	public void delete(final Answer answer) {

		Assert.notNull(answer);

		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		Assert.isTrue(answer.getAnswerer().equals(user));
		this.answerRepository.delete(answer);
	}
	
	public void deleteAll(final Collection<Answer> answers) {
		
		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		this.answerRepository.delete(answers);
	}
	
	public Collection<Answer> findAllByAnswerer(int userId){
		Collection<Answer> answers = new ArrayList<Answer>();
		answers = this.answerRepository.findAllByAnswerer(userId);
		return answers;
	}
	
	public Collection<Answer> findAllByrendezvous(int rendezvousId){
		Collection<Answer> answers = new ArrayList<Answer>();
		answers = this.answerRepository.findAllByRendezvous(rendezvousId);
		return answers;
	}

}
