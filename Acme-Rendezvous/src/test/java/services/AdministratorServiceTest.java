
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{	// Display correcto de un Administrator ya logueado como tal. 
				"admin", 59, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverAdminNotExitsAndLogged() {
		final Object testingData[][] = {
			{	// Display erróneo de un Administrator que no existe con uno logueado.
				"admin", 100, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverNotLogged() {
		final Object testingData[][] = {
			{	// Display correcto de un Administrator, sin estar logueado en el sistema.
				null, 59, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverAdminNotExitsNoLogged() {
		final Object testingData[][] = {
			{	// Display erróneo de un Administrator que no existe sin estar logueado.
				null, 100, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateDisplaying(final String username, final int adminId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Administrator admin = this.administratorService.findOne(adminId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
