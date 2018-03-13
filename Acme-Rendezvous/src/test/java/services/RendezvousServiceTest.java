
package services;

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
import domain.User;

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
		final User user = this.userService.findByPrincipal();
		System.out.println(user.getId());
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
		//		System.out.println(rendezvous.getFinalMode());
		//		System.out.println(rendezvous.getAdultOnly());
		//		System.out.println(rendezvous.getFlag());
		//		System.out.println(rendezvous.getRendezvouses());
		//		System.out.println(rendezvous.getComments());
		//		System.out.println(rendezvous.getAttendants());
		//		System.out.println(rendezvous.getCreator());
		//		System.out.println(rendezvous.getCategories());
		super.authenticate(null);
	}
	@Test
	public void testFindByCreatorId() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findByCreatorId(super.getEntityId("user1"));
		System.out.println(rendezvouses);
	}
	@Test
	public void testSave() {//normal
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.create();
		rendezvous.setName("sample name");
		rendezvous.setDescription("sample description");
		rendezvous.setPicture("http://www.samplepicture.com");
		rendezvous.setLocationLatitude(62.6);
		rendezvous.setLocationLongitude(56.2);
		rendezvous.setFinalMode(true);
		rendezvous.setAdultOnly(false);

		super.authenticate(null);

	}
	@Test
	public void testSave2() {//intentar modificar un rendezvous que esta en final mode

		super.authenticate("user1");
		final Rendezvous a = this.rendezvousService.findOneOnly(super.getEntityId("rendezvous1"));
		a.setFinalMode(true);
		final Rendezvous save = this.rendezvousService.save(a);
		System.out.println("finalMode del rendezvous1: " + save.getFinalMode());
		save.setName("random");
		final Rendezvous save2 = this.rendezvousService.save(save);
		Assert.isTrue(this.rendezvousService.findAll().contains(save2));

		super.authenticate(null);

	}

	@Test
	public void testDelete() {
		super.authenticate("admin");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));

		this.rendezvousService.deleteByAdmin(rendezvous);

		Assert.isTrue(rendezvous.getFlag() == Flag.DELETED);
		super.authenticate(null);
	}
	//	@Test
	//	public void testAvgRendezvousPerUser() {
	//		final Double avg = this.rendezvousService.avgRendezvousPerUser();
	//		System.out.println("Media de rendezvouses por usuario: " + avg);
	//	}
	//	@Test
	//	public void testRatioCreators() {
	//		final Double ratio = this.rendezvousService.ratioCreators();
	//		System.out.println("Ratio de usuarios que han creado al menos un rendezvous: " + ratio);
	//	}

	//	@Test
	//	public void testRatioUsersSinRendezvous() {
	//		final Double ratio = this.rendezvousService.ratioUsersSinRendezvous();
	//		System.out.println("Ratio de usuarios que nunca han creado un rendezvous" + ratio);
	//	}
	@Test
	public void testRandom() {
		final Rendezvous a = this.rendezvousService.findOneOnly(super.getEntityId("rendezvous1"));
		final Rendezvous b = this.rendezvousService.findOneOnly(super.getEntityId("rendezvous2"));
		System.out.println("finalMode del rendezvous2: " + b.getFinalMode());
	}
}
