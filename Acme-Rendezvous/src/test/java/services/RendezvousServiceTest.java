
package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
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
	@Test
	public void testFindByCreatorId() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findByCreatorId(super.getEntityId("user1"));
		System.out.println("Rendezvouses del user1: " + rendezvouses);
	}
	@Test
	public void testSave() {//normal
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("http://www.samplepicture.com");
		final Date moment = new Date(System.currentTimeMillis() + 5000);
		rendezvous.setMoment(moment);
		rendezvous.setLocationLatitude(62.6);
		rendezvous.setLocationLongitude(56.2);
		rendezvous.setFinalMode(true);
		rendezvous.setAdultOnly(false);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));

		super.authenticate(null);

	}
	@Test
	public void testSave2() {//intentar modificar un rendezvous que esta en final mode

		super.authenticate("user1");
		final Rendezvous a = this.rendezvousService.findOneOnly(super.getEntityId("rendezvous1"));
		a.setFinalMode(true);
		final Rendezvous save = this.rendezvousService.save(a);
		save.setName("random");
		final Rendezvous save2 = this.rendezvousService.save(save);
		Assert.isTrue(this.rendezvousService.findAll().contains(save2));

		super.authenticate(null);

	}
	@Test
	public void testSave3() {//normal sin locations
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

	}
	@Test
	public void testSave4() {//con el nombre vacio
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		//	rendezvous.setName("");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("http://www.samplepicture.com");
		final Date moment = new Date(System.currentTimeMillis() + 5000);
		rendezvous.setMoment(moment);
		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(false);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test
	public void testSave5() {//con el description vacio
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		//rendezvous.setDescription("");
		rendezvous.setPicture("http://www.samplepicture.com");
		rendezvous.setFinalMode(false);
		final Date moment = new Date(System.currentTimeMillis() + 5000);
		rendezvous.setMoment(moment);
		rendezvous.setAdultOnly(false);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test
	public void testSave6() {//el picture no esta en formato url
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("sample picture");
		final Date moment = new Date(System.currentTimeMillis() + 5000);
		rendezvous.setMoment(moment);
		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(true);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test
	public void testSave7() {//no tiene picture
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		final Date moment = new Date(System.currentTimeMillis() + 5000);
		rendezvous.setMoment(moment);
		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(true);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test
	public void testSave8() {//no tiene moment
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("http://www.picture.com");

		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(true);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		Assert.isTrue(this.rendezvousService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test
	public void testSave9() {//guardar teniendo el flag en delete
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
	public void testDelete() {//un admin borra un rendezvous
		super.authenticate("admin");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));

		this.rendezvousService.deleteByAdmin(rendezvous);

		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);
		super.authenticate(null);
	}

	@Test
	public void testDelete2() {//un usuario borra un rendezvous de otro usuario 
		super.authenticate("user2");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByUser(rendezvous);
		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

		super.authenticate(null);
	}
	@Test
	public void testDelete3() {//un usuario borra un rendezvous 
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByUser(rendezvous);
		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

		super.authenticate(null);
	}
	@Test
	public void testDelete4() {//un usuario borra un rendezvous que esta en final mode
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		rendezvous.setFinalMode(true);
		final Rendezvous save = this.rendezvousService.save(rendezvous);
		this.rendezvousService.deleteByUser(save);
		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

		super.authenticate(null);
	}
	@Test
	public void testDelete5() {//un usuario sin autentificar borra un rendezvous 
		super.authenticate(null);
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByUser(rendezvous);
		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

	}
	@Test
	public void testDelete6() {//un usuario borra un rendezvous que tiene un flag a DELETE
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByUser(rendezvous);
		this.rendezvousService.deleteByUser(rendezvous);

		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

		super.authenticate(null);
	}
	@Test
	public void testDelete7() {//un admin borra un rendezvous que tiene un flag a DELETE
		super.authenticate("admin");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.deleteByUser(rendezvous);
		this.rendezvousService.deleteByUser(rendezvous);

		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);

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
	@Test
	public void testRSVP2() {//rsvp un manager
		super.authenticate("manager");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.rsvp(rendezvous);
		Assert.isTrue(rendezvous.getAttendants().contains(this.userService.findByPrincipal()));
		super.authenticate(null);
	}

}
