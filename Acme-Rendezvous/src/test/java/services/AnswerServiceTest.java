
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Answer;
import domain.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnswerServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------

	@Autowired
	private AnswerService	answerService;

	@Autowired
	private QuestionService	questionService;


	// Tests ---------------------------------------------------------------

	@Test
	public void driverCorrect() {
		final Object testingData[][] = {
			{
				"user1", 229, "answer", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverByAdmin() {
		final Object testingData[][] = {
			{
				"admin", 229, "answer", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverNotLogged() {
		final Object testingData[][] = {
			{
				null, 229, "answer", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverNotAnswer() {
		final Object testingData[][] = {
			{
				"user1", 229, null, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de un answerlogueado como User. 
				"user1", 225, null
			}, {	// Display correcto de un answerlogueado como Administrator.
				"admin", 225, null
			}, {	// Display correcto de un Question, sin estar logueado en el sistema.
				null, 225, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//	@Test
	//	public void driverDelete() {
	//		final Object testingData[][] = {
	//			{	// Delete answercorrecto.
	//				"user1", 229, null
	//			}, {
	//				// Delete Question: no logueado
	//				null, 230, IllegalArgumentException.class
	//			}, {
	//				// Delete Question: no user
	//				"admin", 230, IllegalArgumentException.class
	//			}
	//		};
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateQuestionDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}

	// Templates ----------------------------------------------------------

	protected void templateQuestion(final String username, final int questionId, final String written, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Answer a = this.answerService.create(questionId);
			a.setWritten(written);

			final Question q = this.questionService.findOne(questionId);

			this.answerService.save(a, q);
			this.answerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final int answerId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Answer a = this.answerService.findOne(answerId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//	protected void templateQuestionDelete(final String username, final int questionId, final Class<?> expected) {
	//		Class<?> caught;
	//		caught = null;
	//		try {
	//			this.authenticate(username);
	//			final Answer a = this.answerService.findOne(questionId);
	//			this.questionService.deleteByUser(q);
	//			this.unauthenticate();
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//		this.checkExceptions(expected, caught);
	//	}
}
