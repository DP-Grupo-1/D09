
package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.Flag;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RendezvousServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------
	@Autowired
	private RendezvousService	rendezvousService;
	@Autowired
	private UserService			userService;


	//Tests-------------------------------------------------------------------------
	@Test
	public void testCreate() {
		super.authenticate("user2");
		final Rendezvous rendezvous = this.rendezvousService.create();
		Assert.isNull(rendezvous.getName());
		Assert.isNull(rendezvous.getDescription());
		Assert.isNull(rendezvous.getMoment());
		Assert.isNull(rendezvous.getPicture());
		Assert.isNull(rendezvous.getLocationLatitude());
		Assert.isNull(rendezvous.getLocationLongitude());
		Assert.notNull(rendezvous.getFinalMode());
		Assert.notNull(rendezvous.getAdultOnly());
		Assert.notNull(rendezvous.getFlag());
		Assert.notNull(rendezvous.getRendezvouses());
		Assert.notNull(rendezvous.getComments());
		Assert.notNull(rendezvous.getCreator());
		Assert.notNull(rendezvous.getAttendants());
		Assert.notNull(rendezvous.getCategories());

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSave2() {//guardar teniendo el flag en delete
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("http://www.samplepicture.com");
		final Date moment = new Date(System.currentTimeMillis() + 5000);
		rendezvous.setMoment(moment);
		rendezvous.setFlag(Flag.DELETED);
		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(false);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test
	public void testSave3() {//al guardar en pasado el flag deberia ser PASSED
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("http://www.samplepicture.com");
		final Date moment = new Date(System.currentTimeMillis() - 5000);
		rendezvous.setMoment(moment);
		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(false);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		Assert.isTrue(save.getFlag().equals(Flag.PASSED));
		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSave4() {
		//un user intentando editar el rendezvous de otro user
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous2"));
		rendezvous.setName("asdf");
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));

		super.authenticate(null);
	}

	@Test
	public void testRSVP() {//normal
		super.authenticate("user2");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.rsvp(rendezvous);
		Assert.isTrue(rendezvous.getAttendants().contains(this.userService.findByPrincipal()));
		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testRSVP2() {//rsvp un manager
		super.authenticate("manager");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.rsvp(rendezvous);
		Assert.isTrue(rendezvous.getAttendants().contains(this.userService.findByPrincipal()));
		super.authenticate(null);
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			{//normal
				"user1", "sample name", "sample description", "http://www.samplepicture.com", new Date(System.currentTimeMillis() + 5000), 62.6, 56.2, true, false, null
			}, {//normal sin locations
				"user1", "sample name", "sample description", "http://www.samplepicture.com", new Date(System.currentTimeMillis() + 5000), null, null, true, false, null
			}, {//test negativo: el nombre vacio
				"user1", "", "sample description", "http://www.samplepicture.com", new Date(System.currentTimeMillis() + 5000), 62.6, 56.2, true, false, ConstraintViolationException.class
			}, {//test negativo: el description vacio
				"user1", "sample name", "", "http://www.samplepicture.com", new Date(System.currentTimeMillis() + 5000), 62.6, 56.2, true, false, ConstraintViolationException.class
			}, {//test negativo: el picture vacio
				"user1", "sample name", "sample description", "", new Date(System.currentTimeMillis() + 5000), 62.6, 56.2, true, false, ConstraintViolationException.class
			}, {//test negativo: el picture sin formato url
				"user1", "sample name", "sample description", "sample picture", new Date(System.currentTimeMillis() + 5000), 62.6, 56.2, true, false, ConstraintViolationException.class
			}, {//test negativo: sin moment
				"user1", "sample name", "sample description", "http://www.samplepicture.com", null, 62.6, 56.2, true, false, ConstraintViolationException.class
			}, {//test negativo: el que guarda un rendezvous es un manager
				"manager", "sample name", "sample description", "http://www.samplepicture.com", new Date(System.currentTimeMillis() + 5000), 62.6, 56.2, true, false, IllegalArgumentException.class
			}, {//test negativo: el que guarda un rendezvous es un usuario sin registrar
				null, "sample name", "sample description", "http://www.samplepicture.com", new Date(System.currentTimeMillis() + 5000), 62.6, 56.2, true, false, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6], (Boolean) testingData[i][7],
				(Boolean) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	protected void templateSave(final String username, final String name, final String description, final String picture, final Date moment, final Double llatitude, final Double llongitude, final Boolean finalMode, final Boolean adultOnly,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Rendezvous rendezvous = this.rendezvousService.create();
			rendezvous.setName(name);
			rendezvous.setDescription(description);
			rendezvous.setPicture(picture);
			rendezvous.setMoment(moment);
			rendezvous.setLocationLatitude(llatitude);
			rendezvous.setLocationLongitude(llongitude);
			rendezvous.setFinalMode(finalMode);
			rendezvous.setAdultOnly(adultOnly);
			final Rendezvous save = this.rendezvousService.save(rendezvous);
			Assert.isTrue(this.rendezvousService.findAll().contains(save));

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverDelete() {
		final Object testingData[][] = {

			{//normal
				"user1", "rendezvous1", null
			}, {//test negativo:un usuario intenta borrar un rendezvous de otro usuario
				"user2", "rendezvous1", IllegalArgumentException.class
			}, {//test negativo:un usuario sin autenticar intenta borrar un rendezvous
				null, "rendezvous2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void templateDelete(final String username, final int rendezvousId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
			this.rendezvousService.deleteByUser(rendezvous);
			Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void testFindByCreatorId() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findByUserId(super.getEntityId("user1"));
		System.out.println("Rendezvouses que ha creado  user1: " + rendezvouses);
	}
	@Test
	public void testFindByUserId() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findByUserId(super.getEntityId("user1"));
		System.out.println("Rendezvouses que ha confirmado user1: " + rendezvouses);
	}
	@Test
	public void testAvgRendezvousPerUser() {
		final Double avg = this.rendezvousService.avgRendezvousPerUser();
		Assert.notNull(avg);
		System.out.println("Media de rendezvouses por usuario: " + avg);
	}
	@Test
	public void testStddevUsersPerRendezvous() {
		final Double stdev = this.rendezvousService.stddevRendezvousPerUser();
		Assert.notNull(stdev);
		System.out.println("Desviacion estandar de rendezvous por usuario: " + stdev);
	}
	@Test
	public void testAvgRSVPsPerUser() {
		final Double avg = this.rendezvousService.avgRSVPsPerUser();
		Assert.notNull(avg);
		System.out.println("Media de confirmaciones por usuario: " + avg);
	}
	@Test
	public void testStddevRSVPsPerUser() {
		final Double stdev = this.rendezvousService.stddevRSVPsPerUser();
		Assert.notNull(stdev);
		System.out.println("Desviacion estandar de confirmaciones por usuario: " + stdev);
	}
	@Test
	public void testTop10RendezvousesByRSVPs() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.top10RendezvousesByRSVPs();
		Assert.notNull(rendezvouses);
		System.out.println("10 Rendezvouses con mas confirmaciones: " + rendezvouses);
	}
	@Test
	public void testAbove75AverageOfAnnouncementsPerRendezvous() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.above75AverageOfAnnouncementsPerRendezvous();
		Assert.notNull(rendezvouses);
		System.out.println("Rendezvouses cuyo numero de anuncions es superior al 75% de la media" + rendezvouses);

	}
	@Test
	public void testLinkedGreaterAveragePlus10() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.LinkedGreaterAveragePlus10();
		Assert.notNull(rendezvouses);
		System.out.println("Rendezvouses vinculado a un nº de rendezvouses mayor a la media en un 10%: " + rendezvouses);
	}

	@Test
	public void testFindByRendezvous() {
		final Collection<Comment> comments = this.rendezvousService.findByRendezvous(super.getEntityId("rendezvous1"));
		Assert.notNull(comments);
		System.out.println("Comentarios del rendezvous 1: " + comments);
	}
	@Test
	public void testFindByCommentId() {
		final Rendezvous rendezvous = this.rendezvousService.findByCommentId(super.getEntityId("comment1"));
		Assert.notNull(rendezvous);
		System.out.println("Rendezvous del comment1: " + rendezvous);
	}
	@Test
	public void testFindByAnnouncementId() {
		final Rendezvous rendezvous = this.rendezvousService.findByAnnouncementId(super.getEntityId("announcement1"));
		Assert.notNull(rendezvous);
		System.out.println("Rendezvous del announcement1: " + rendezvous);
	}
	@Test
	public void testRatioUsersSinRendezvous() {
		final Double ratio = this.rendezvousService.ratioUsersSinRendezvous();
		Assert.notNull(ratio);
		System.out.println("Ratio de usuarios sin rendezvous creados:" + ratio);
	}
	@Test
	public void testStddevRendezvousPerUser() {
		final Double stddev = this.rendezvousService.stddevRendezvousPerUser();
		Assert.notNull(stddev);
		System.out.println("Desviacion estandar de los rendezvouses creados por usuario: " + stddev);
	}
	@Test()
	public void testDelete() {//un admin borra un rendezvous sin nada
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("http://www.samplepicture.com");
		final Date moment = new Date(System.currentTimeMillis() + 5000);
		rendezvous.setMoment(moment);
		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(false);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		super.authenticate(null);
		super.authenticate("admin");
		this.rendezvousService.deleteByAdmin(save);
		this.rendezvousService.flush();

		//	Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);
		Assert.isTrue(!this.rendezvousService.findAll().contains(save));
		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDelete2() {//un usuario borra un rendezvous que esta en final mode
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		rendezvous.setFinalMode(true);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		this.rendezvousService.deleteByUser(save);
		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testDelete3() {//un usuario borra un rendezvous que tiene un flag a DELETE
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByUser(rendezvous);
		this.rendezvousService.deleteByUser(rendezvous);

		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testDelete4() {//un admin borra un rendezvous que tiene un flag a DELETE
		super.authenticate("admin");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByUser(rendezvous);
		this.rendezvousService.deleteByUser(rendezvous);

		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

		super.authenticate(null);
	}
	@Test
	public void testDelete5() {
		super.authenticate("admin");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByAdmin(rendezvous);
		Assert.isTrue(!this.rendezvousService.findAll().contains(rendezvous));
		super.authenticate(null);
	}
}
