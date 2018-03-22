
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
import domain.Benefit;
import domain.Rendezvous;
import domain.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private RequestService		requestService;
	@Autowired
	private BenefitService		benefitService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Tests
	@Test
	public void testCreate() {
		final Request request = this.requestService.create();
		Assert.isNull(request.getBenefit());
		Assert.isNull(request.getComment());
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			//normal
			{
				"user1", "benefit1", "rendezvous1", null
			},
			//intentar solicitar un servicio para un rendezvous distinto al del usuario
			{
				"user1", "benefit2", "rendezvous2", IllegalArgumentException.class
			},
			//			//intentar solicitar un admin
			{
				"admin", "benefit1", "rendezvous1", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);
	}
	protected void templateSave(final String username, final int benefitId, final int rendezvousId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Request request = this.requestService.create();
			request.setComment("asdf");
			final Benefit benefit = this.benefitService.findOne(benefitId);
			final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
			final Request save = this.requestService.save(request, benefit, rendezvous);
			Assert.isTrue(this.requestService.findAll().contains(save));
			super.authenticate(null);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSave() {
		//Intentar hacer una solicitud con un benefit cancelado
		super.authenticate("admin");
		final Benefit benefit = this.benefitService.findOne(super.getEntityId("benefit2"));

		this.benefitService.cancelBenefit(benefit);
		Assert.isTrue(benefit.getFlag().equals("CANCELLED"));
		super.authenticate(null);
		super.authenticate("user1");
		final Rendezvous rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));

		final Request request = this.requestService.create();
		request.setComment("asdf");
		final Request save = this.requestService.save(request, benefit, rendezvous);
		Assert.isTrue(this.requestService.findAll().contains(save));

		super.authenticate(null);

	}

	@Test
	public void testDelete() {
		super.authenticate("user1");
		final Request request = this.requestService.findOne(super.getEntityId("request1"));
		this.requestService.delete(request);
		Assert.isTrue(!this.requestService.findAll().contains(request));

		super.authenticate(null);

	}

	@Test
	public void testQuerys() {

		final Benefit benefit1 = this.benefitService.findOne(super.getEntityId("benefit1"));
		final Benefit benefit2 = this.benefitService.findOne(super.getEntityId("benefit2"));
		final Collection<Request> request1 = this.requestService.findAllByBenefit(benefit2);
		final Collection<Request> request2 = this.requestService.findAllByBenefit(benefit1.getId());
		Assert.notNull(request1);
		System.out.println("Solicitudes del servicio nº1: " + request2);
		System.out.println("Solicitudes del servicio nº2: " + request1);
	}

}
