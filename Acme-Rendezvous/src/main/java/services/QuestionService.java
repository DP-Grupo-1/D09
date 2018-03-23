
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuestionRepository;
import domain.Administrator;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class QuestionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private QuestionRepository		questionRepository;

	@Autowired
	private RendezvousService		rendezvousService;
	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------------------------

	public QuestionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Question create(final int rendezvousId) {

		Question result;
		result = new Question();
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final Collection<Answer> answers = new ArrayList<Answer>();
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(principal.equals(rendezvous.getCreator()));
		result.setAnswers(answers);
		result.setCreator(principal);
		result.setRendezvous(rendezvous);

		return result;
	}

	public Question findOne(final int questionId) {
		final Question question = this.questionRepository.findOne(questionId);
		Assert.isTrue(question.getId() != 0);
		return question;
	}

	public Question save(final Question question) {

		final Question saved = this.questionRepository.save(question);
		return saved;
	}

	public void deleteByUser(final Question question) {

		Assert.notNull(question);

		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		Assert.isTrue(question.getCreator().equals(user));
		this.questionRepository.delete(question);
	}
	public void deleteByAdmin(final Question question) {
		Assert.notNull(question);
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		this.questionRepository.delete(question);

	}
	//	public void quitarQuestionAnswer(final Question question) {
	//		final Collection<Answer> answers = question.getAnswers();
	//		if (!answers.isEmpty())
	//			for (final Answer a : answers)
	//				answers.remove(a.getAnswerer());
	//
	//	}
	public Collection<Question> findAllByPrincipalAndRendezvous(final int principalId, final int rendezvousId) {

		Collection<Question> questions = new ArrayList<Question>();
		questions = this.questionRepository.findAllByPrincipalAndRendezvous(principalId, rendezvousId);
		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		return questions;

	}

	public Collection<Question> findAllByRendezvous(final int rendezvousId) {
		return this.questionRepository.findAllByRendezvous(rendezvousId);
	}

	public Double avgQuestionsPerRendezvous() {
		return this.questionRepository.avgQuestionsPerRendezvous();
	}

	//Requisito 22.1 punto 1: La desviación estándar de preguntas creadas por rendezvous.
	public Double stddevQuestionPerRendezvous() {
		Double stddev = 0.0;
		final Double avgQuestions = this.avgQuestionsPerRendezvous();

		stddev = Math.sqrt(this.sumQuestions() / this.totalQuestions() - avgQuestions * avgQuestions);

		return stddev;
	}

	private Integer totalQuestions() {
		Integer numQuestions = 0;
		for (final Rendezvous r1 : this.rendezvousService.findAll()) {
			final Collection<Question> questions = this.findAllByRendezvous(r1.getId());
			numQuestions = numQuestions + questions.size();
		}
		return numQuestions;
	}

	private Integer sumQuestions() {
		Integer sumQuestions = 0;
		for (final Rendezvous r2 : this.rendezvousService.findAll()) {
			final Collection<Question> questions = this.findAllByRendezvous(r2.getId());
			sumQuestions = sumQuestions + questions.size() * questions.size();
		}
		return sumQuestions;
	}

	public Double avgAnswersPerQuestions() {
		Double res;
		res = this.questionRepository.avgAnswersPerQuestions();
		return res;
	}
	public Double stdevAnswerPerQuestions() {
		Double res;
		res = this.questionRepository.stdevAnswersPerQuestions();
		return res;
	}

	//	//Prune domain object------------------------------------------------------------
	//	public Question reconstruct(final Question question, final BindingResult binding) {
	//		Question res;
	//		if (question.getId() == 0)
	//			res = question;
	//		else {
	//			res = this.questionRepository.findOne(question.getId());
	//			res.setQuestionToAnswer(question.getQuestionToAnswer());
	//			this.validator.validate(res, binding);
	//		}
	//		return res;
	//	}

	public Question reconstruct(final Question question, final BindingResult binding) {
		Question res;
		if (question.getId() == 0)
			res = question;
		else {
			res = this.questionRepository.findOne(question.getId());
			res.setQuestionToAnswer(question.getQuestionToAnswer());
			//   res.setAnswers(question.getAnswers());
			// res.setCreator(question.getCreator());
			// res.setRendezvous(question.getRendezvous());
			this.validator.validate(res, binding);
		}
		return res;
	}

	public void flush() {
		this.questionRepository.flush();
		// TODO Auto-generated method stub

	}

}
