
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import services.QuestionService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import domain.User;
import forms.AnswerQuestions;

@Controller
@RequestMapping("/question/user")
public class QuestionUserController extends AbstractController {

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	//Creation--------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer rendezvousId) {
		ModelAndView result;
		Question question;
		question = this.questionService.create(rendezvousId);
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getCreator().equals(principal));
		result = this.createEditModelAndViewQuestion(question);
		result.addObject("requestURI", "question/user/edit.do?rendezvousId=" + rendezvousId);

		return result;
	}

	//Edit----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editQuestion(@RequestParam final int questionId) {
		final ModelAndView res;
		final Question question = this.questionService.findOne(questionId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(question.getRendezvous().getCreator().equals(principal));
		res = this.createEditModelAndViewQuestion(question);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveQuestion(@Valid final Question question, final BindingResult binding) {

		ModelAndView result;
		//question = this.questionService.reconstruct(question, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndViewQuestion(question);
		else

			try {

				this.questionService.save(question);
				result = new ModelAndView("redirect:/welcome/index.do");

			}

			catch (final Throwable oops) {
				result = this.createEditModelAndViewQuestion(question, "question.comit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Question question, final BindingResult binding) {

		ModelAndView result;
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(question.getCreator().equals(principal));
		if (binding.hasErrors())
			result = this.createEditModelAndViewQuestion(question);
		else
			try {
				this.questionService.deleteByUser(question);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewQuestion(question, "question.commit.error");
			}
		return result;
	}

	//Edit----------------------------------------------------------------------
	@RequestMapping(value = "/answerQuestions", method = RequestMethod.GET)
	public ModelAndView answerQuestions(@RequestParam final int rendezvousId) {

		ModelAndView result;
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final Collection<Question> questions = this.questionService.findAllByRendezvous(rendezvousId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(!(rendezvous.getCreator().equals(principal)));
		Assert.isTrue(!(rendezvous.getAttendants().contains(principal)));
		final List<Answer> answers = new ArrayList<Answer>();
		for (int i = 0; i < questions.size(); i++) {
			final Answer ans = new Answer();
			ans.setAnswerer(principal);
			answers.add(ans);
		}
		final AnswerQuestions answerQuestions = new AnswerQuestions();
		answerQuestions.setQuestions(questions);
		answerQuestions.setAnswers(answers);

		result = this.createEditModelAndViewAnswer(answerQuestions);
		result.addObject("answerQuestions", answerQuestions);
		result.addObject("requestURI", "question/user/answerQuestions.do?rendezvousId=" + rendezvousId);

		return result;
	}

	@RequestMapping(value = "/answerQuestions", method = RequestMethod.POST, params = "save")
	public ModelAndView answerQuestions(@Valid final AnswerQuestions answerQuestions, final BindingResult binding) {

		ModelAndView result;
		final Question question = (Question) answerQuestions.getQuestions().toArray()[0];
		final Rendezvous rendezvous = question.getRendezvous();

		if (binding.hasErrors())
			result = this.createEditModelAndViewAnswer(answerQuestions);
		else

			try {
				Boolean enBlanco = false;
				for (final Answer s : answerQuestions.getAnswers())
					if (s.getWritten() == null || s.getWritten() == "") {
						enBlanco = true;
						break;
					}
				if (enBlanco)
					result = this.createEditModelAndViewAnswer(answerQuestions, "answer.commit.error");
				else {
					this.answerService.saveAll(answerQuestions.getAnswers(), answerQuestions.getQuestions());
					result = new ModelAndView("redirect:/welcome/index.do");
				}

			}

			catch (final Throwable oops) {
				result = this.createEditModelAndViewAnswer(answerQuestions, "question.comit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndViewQuestion(final Question question) {
		ModelAndView result;

		result = this.createEditModelAndViewQuestion(question, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewQuestion(final Question question, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("question/edit");
		result.addObject("question", question);
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createEditModelAndViewAnswer(final AnswerQuestions answerQuestions) {
		ModelAndView result;

		result = this.createEditModelAndViewAnswer(answerQuestions, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewAnswer(final AnswerQuestions answerQuestions, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("question/user/answerQuestions");
		result.addObject("answerQuestions", answerQuestions);
		result.addObject("message", messageCode);
		return result;
	}

}
