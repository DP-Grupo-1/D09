
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
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private ManagerService	managerService;


	//test
	@Test
	public void testCreate() {
		final Manager manager = this.managerService.create();
		Assert.isNull(manager.getName());
		Assert.isNull(manager.getSurname());
		Assert.isNull(manager.getPostalAddress());
		Assert.isNull(manager.getPhoneNumber());
		Assert.isNull(manager.getEmail());
		Assert.notNull(manager.getAdult());
		Assert.notNull(manager.getUserAccount());
		Assert.notNull(manager.getBenefits());
		Assert.isNull(manager.getVAT());
	}
	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{		// Creación correcta de un Manager.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, "22.1", null
			}, {	//  username vacío.
				"", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, "22.1", ConstraintViolationException.class
			}, {	//  password vacío.
				"userNameNuevo", "", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, "22.1", ConstraintViolationException.class
			}, {	//  name vacío.
				"userNameNuevo", "passwordNuevo", "", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, "22.1", ConstraintViolationException.class
			}, {	// surname vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "", 53039, "correonuevo@bien.com", "954678342", false, "22.1", ConstraintViolationException.class
			}, {	//  username con pocos carácteres.
				"us", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, "22.1", ConstraintViolationException.class
			}, {	//  password con pocos carácteres.
				"userNameNuevo", "pa", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, "22.1", ConstraintViolationException.class
			}, {	// email incorrecto.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo.com", "954678342", false, "22.1", ConstraintViolationException.class
			}, {	//  phoneNumber incorrecto.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "AB", false, "22.1", ConstraintViolationException.class
			}, {	//  email vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, null, "954678342", false, "22.1", ConstraintViolationException.class
			}, {//VAT vacio
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "954678342", false, "", ConstraintViolationException.class

			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (boolean) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	@Test
	public void driverCreationPhoneNull() {
		final Object testingData[][] = {
			{		// Creación correcta de un User: phoneNumber vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", 53039, "correonuevo@bien.com", "", false, "22.1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (boolean) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	@Test
	public void driverCreationPostalNull() {
		final Object testingData[][] = {
			{		// Creación correcta de un User: código postal vacío.
				"userNameNuevo", "passwordNuevo", "nombreNuevo", "apellidoNuevo", null, "correonuevo@bien.com", "954678342", false, "22.1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (boolean) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	protected void templateSave(final String username, final String password, final String name, final String surname, final Integer postal, final String email, final String phone, final boolean adult, final String vat, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Manager m = this.managerService.create();
			final UserAccount user = new UserAccount();
			final Collection<Authority> as = new ArrayList<Authority>();
			final Authority a = new Authority();
			a.setAuthority("MANAGER");
			as.add(a);
			user.setAuthorities(as);
			m.setUserAccount(user);
			user.setUsername(username);
			user.setPassword(password);
			m.setUserAccount(user);
			m.setName(name);
			m.setSurname(surname);
			m.setEmail(email);
			m.setPostalAddress(postal);
			m.setPhoneNumber(phone);
			m.setAdult(adult);
			m.setVAT(vat);
			final Manager save = this.managerService.save(m);
			this.managerService.flush();
			Assert.isTrue(this.managerService.findAll().contains(save));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
