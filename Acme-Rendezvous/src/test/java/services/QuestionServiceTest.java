
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class QuestionServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------

	@Autowired
	private QuestionService	questionService;


	// Tests ---------------------------------------------------------------

	@Test
	public void driverCorrect() {
		final Object testingData[][] = {
			{
				"user1", 227, "questionToAnswer", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverByAdmin() {
		final Object testingData[][] = {
			{
				"admin", 227, "questionToAnswer", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverNotLogged() {
		final Object testingData[][] = {
			{
				null, 227, "questionToAnswer", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverNotQuestion() {
		final Object testingData[][] = {
			{
				"user1", 227, null, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestion((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de un Question logueado como User. 
				"user1", 229, null
			}, {	// Display correcto de un Question logueado como Administrator.
				"admin", 230, null
			}, {	// Display correcto de un Question, sin estar logueado en el sistema.
				null, 229, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{	// Delete Question correcto.
				"user1", 229, null
			}, {
				// Delete Question: no logueado
				null, 230, IllegalArgumentException.class
			}, {
				// Delete Question: no user
				"admin", 230, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateQuestionDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------

	protected void templateQuestion(final String username, final int rendezvousId, final String question, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Question q = this.questionService.create(rendezvousId);
			q.setQuestionToAnswer(question);

			this.questionService.save(q);
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final int questionId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Question q = this.questionService.findOne(questionId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateQuestionDelete(final String username, final int questionId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Question q = this.questionService.findOne(questionId);
			this.questionService.deleteByUser(q);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
