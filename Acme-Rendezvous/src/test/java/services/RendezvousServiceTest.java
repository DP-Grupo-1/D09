
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Rendezvous;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
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
		System.out.println(rendezvous.getFinalMode());
		System.out.println(rendezvous.getAdultOnly());
		System.out.println(rendezvous.getFlag());
		System.out.println(rendezvous.getRendezvouses());
		System.out.println(rendezvous.getComments());
		System.out.println(rendezvous.getAttendants());
		System.out.println(rendezvous.getCreator());
		super.authenticate(null);
	}
	@Test
	public void testFindByCreatorId() {
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findByCreatorId(111);
		System.out.println(rendezvouses);
	}

	@Test
	public void testDelete() {
		super.authenticate("admin");
		final List<Rendezvous> rendezvouses = (List<Rendezvous>) this.rendezvousService.findAll();
		final Rendezvous rendezvous = rendezvouses.get(0);
		System.out.println(rendezvouses);
		this.rendezvousService.deleteByAdmin(rendezvous);
		final Collection<Rendezvous> rendezvouses2 = this.rendezvousService.findAll();
		System.out.println(rendezvouses2);
		Assert.isTrue(!this.rendezvousService.findAll().contains(rendezvous));
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

}
