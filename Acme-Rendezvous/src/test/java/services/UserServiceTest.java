
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private UserService	userService;


	//Tests----------------------------------------------------------

	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un User.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, null
			}, {	// Creación errónea de un User: username vacío.
				"", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, ConstraintViolationException.class
			}, {	// Creación errónea de un User: password vacío.
				"userNameNuevo", "", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, ConstraintViolationException.class
			}, {	// Creación errónea de un User: name vacío.
				"userNameNuevo", "passwordNuevo", "", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, ConstraintViolationException.class
			}, {	// Creación errónea de un User: surname vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "", 53039, "correonuevo@bien.com", "954678342", false, ConstraintViolationException.class
			}, {	// Creación errónea de un User: username con pocos carácteres.
				"us", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, ConstraintViolationException.class
			}, {	// Creación errónea de un User: password con pocos carácteres.
				"userNameNuevo", "pa", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, ConstraintViolationException.class
			}, {	// Creación errónea de un User: email incorrecto.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo.com", "954678342", false, ConstraintViolationException.class
			}, {	// Creación errónea de un User: phoneNumber incorrecto.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "AB", false, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationPostalNull() {
		final Object testingData[][] = {
			{		// Creación correcta de un User: código postal vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", null, "correonuevo@bien.com", "954678342", false, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationEmailNull() {
		final Object testingData[][] = {
			{		// Creación correcta de un User: email vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "", "954678342", false, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationPhoneNull() {
		final Object testingData[][] = {
			{		// Creación correcta de un User: phoneNumber vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "", false, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(boolean) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de un User ya creado y logueado como tal. 
				"user1", 223, null
			}, {	// Display correcto de un User distinto al que está logueado.
				"user2", 223, null
			}, {	// Display correcto de un User, sin estar logueado en el sistema.
				null, 223, null
			}, {	// Display erróneo de un User que no existe con uno logueado.
				"user1", 100000, IllegalArgumentException.class
			}, {		// Display erróneo de un User que no existe sin estar logueado.
				null, 100000, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverFindByReplyId() {
		final Object testingData[][] = {
			{
				"user1", 202, null
			}, {
				"admin", 204, null
			}, {
				null, 202, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindByReplyId((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverFindByCommentId() {
		final Object testingData[][] = {
			{
				"user1", 201, null
			}, {
				"admin", 203, null
			}, {
				null, 201, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindByCommentId((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String password, final String name, final String surname, final Integer postal, final String email, final String phone, final boolean adult, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final User u = this.userService.create();
			final UserAccount user = new UserAccount();
			final Collection<Authority> as = new ArrayList<Authority>();
			final Authority a = new Authority();
			a.setAuthority("USER");
			as.add(a);
			user.setAuthorities(as);
			u.setUserAccount(user);
			user.setUsername(username);
			user.setPassword(password);
			u.setUserAccount(user);
			u.setName(name);
			u.setSurname(surname);
			u.setEmail(email);
			u.setPostalAddress(postal);
			u.setPhoneNumber(phone);
			u.setAdult(adult);
			this.userService.save(u);
			this.userService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final int userId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final User u = this.userService.findOne(userId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateFindByReplyId(final String username, final Integer replyId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final User u = this.userService.findByReplyId(replyId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateFindByCommentId(final String username, final Integer commentId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final User u = this.userService.findByCommentId(commentId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
