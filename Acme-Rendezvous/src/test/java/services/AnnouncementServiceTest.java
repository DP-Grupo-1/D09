
package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Announcement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------

	@Autowired
	private AnnouncementService	announcementService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverAnnouncementCorrect() {
		final Object testingData[][] = {
			{	// Creación correcta de una Announcement.
				"user1", new Date(System.currentTimeMillis() - 100), "título1", "descripción1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAnnouncement((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	@Test
	public void driverAnnouncementNoLogin() {
		final Object testingData[][] = {
			{	// Creación incorrecta de una Announcement: sin loguear
				null, new Date(System.currentTimeMillis() - 100), "título2", "descripción2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAnnouncement((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	@Test
	public void driverAnnouncementByAdmin() {
		final Object testingData[][] = {
			{   // Creación incorrecta de una Announcement: admin logueado
				"admin", new Date(System.currentTimeMillis() - 100), "título3", "descripción3", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAnnouncement((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	@Test
	public void driverAnnouncementNoTitle() {
		final Object testingData[][] = {
			{   // Creación incorrecta de una Announcement: sin título
				"user1", new Date(System.currentTimeMillis() - 100), null, "descripción4", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAnnouncement((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	@Test
	public void driverAnnouncementNoDescripttion() {
		final Object testingData[][] = {
			{   // Creación incorrecta de una Announcement: sin descripción
				"user1", new Date(System.currentTimeMillis() - 100), "título5", null, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAnnouncement((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de un Announcement logueado como User. 
				"user1", 210, null
			}, {	// Display correcto de un Announcement logueado como Administrator.
				"admin", 210, null
			}, {	// Display correcto de un Announcement, sin estar logueado en el sistema.
				null, 210, null
			}, {	// Display erróneo de un Announcement que no existe con un User logueado.
				"user1", 100000, IllegalArgumentException.class
			}, {	// Display erróneo de un Announcement que no existe con un Administrator logueado.
				"admin", 100000, IllegalArgumentException.class
			}, {	// Display erróneo de un Announcement que no existe sin estar logueado.
				null, 100000, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverAnnouncementDelete() {
		final Object testingData[][] = {
			{	// Delete Announcement correcto.
				"admin", 210, null
			}, {
				// Delete Announcement: no logueado
				null, 210, IllegalArgumentException.class
			}, {
				// Delete Announcement: no administrador
				"user1", 210, IllegalArgumentException.class
			}, {
				// Delete Announcement que no existe
				"admin", 1000000, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAnnouncementDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateAnnouncement(final String username, final Date moment, final String title, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final int rendezvousId = 227;
		try {
			this.authenticate(username);
			final Announcement a = this.announcementService.create(rendezvousId);
			a.setMoment(moment);
			a.setTitle(title);
			a.setDescription(description);

			this.announcementService.save(a);
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final int announcementId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Announcement a = this.announcementService.findOne(announcementId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateAnnouncementDelete(final String username, final int announcementId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Announcement a = this.announcementService.findOne(announcementId);
			this.announcementService.delete(a);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
