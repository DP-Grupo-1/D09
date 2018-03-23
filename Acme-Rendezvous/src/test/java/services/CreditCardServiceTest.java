
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de una tarjeta de crédito.
				"user1", "holderName", "VISA", "4662031000040705", 4, 19, 321, null
			}, {	// Creación errónea de una tarjeta de crédito: nombre vacío.
				"user1", "", "VISA", "4662031000040705", 4, 19, 321, ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: marca vacía.
				"user1", "holderName", "", "4662031000040705", 4, 19, 321, ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: número vacío.
				"user1", "holderName", "VISA", "", 4, 19, 321, ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: número con formato incorrecto.
				"user1", "holderName", "VISA", "4662031000040705", 4, 19, 321, ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: mes incorrecto.
				"user1", "holderName", "VISA", "4662031000040705", 15, 19, 321, ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: año incorrecto.
				"user1", "holderName", "VISA", "4662031000040705", 4, 234, 321, ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: cvv incorrecto.
				"user1", "holderName", "VISA", "4662031000040705", 4, 19, 3251, ConstraintViolationException.class
			}, {	// Creación errónea de una tarjeta de crédito: user incorrecto.
				"user41", "holderName", "VISA", "4662031000040705", 4, 19, 321, IllegalArgumentException.class
			}, {	// Creación errónea de una tarjeta de crédito: mes nulo.
				"user1", "holderName", "VISA", "4662031000040705", null, 19, 321, NullPointerException.class
			}, {	// Creación errónea de una tarjeta de crédito: año nulo.
				"user1", "holderName", "VISA", "4662031000040705", 4, null, 321, NullPointerException.class
			}, {	// Creación errónea de una tarjeta de crédito: cvv nulo.
				"user1", "holderName", "VISA", "4662031000040705", 4, 19, null, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de una tarjeta de crédito.
				"user1", 218, null
			}, {	// Display erróneo de una tarjeta de crédito.
				"user1", 300, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String holderName, final String brandName, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final CreditCard cc = this.creditCardService.create();
			cc.setHolderName(holderName);
			cc.setBrandName(brandName);
			cc.setNumber(number);
			cc.setExpirationMonth(expirationMonth);
			cc.setExpirationYear(expirationYear);
			cc.setCvv(cvv);
			this.creditCardService.save(cc);
			this.creditCardService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final int creditCardId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final CreditCard cc = this.creditCardService.findOne(creditCardId);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
