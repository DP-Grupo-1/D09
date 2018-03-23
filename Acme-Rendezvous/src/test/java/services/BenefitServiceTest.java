
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Benefit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BenefitServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private BenefitService		benefitService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private RendezvousService	rendezvousService;


	//Tests
	@Test
	public void testCreate() {
		final Benefit benefit = this.benefitService.create();
		Assert.isNull(benefit.getName());
		Assert.isNull(benefit.getDescription());
		Assert.isNull(benefit.getPicture());
		Assert.notNull(benefit.getFlag());
		Assert.notNull(benefit.getRendezvouses());
	}

	@Test
	//el benefit lo actualiza un manager
	public void testUpdate() {
		super.authenticate("manager1");
		final Benefit benefit = this.benefitService.create();
		benefit.setName("sample name");
		benefit.setDescription("sample description");
		benefit.setPicture("http://www.picture.com");
		final Benefit save = this.benefitService.save(benefit);
		save.setDescription("asdf");
		final Benefit save2 = this.benefitService.save(save);
		Assert.isTrue(this.benefitService.findAll().contains(save2));
		super.authenticate(null);
	}
	@Test
	//el benefit lo actualiza un admin
	public void testUpdate2() {
		super.authenticate("manager1");
		final Benefit benefit = this.benefitService.create();
		benefit.setName("sample name");
		benefit.setDescription("sample description");
		benefit.setPicture("http://www.picture.com");
		final Benefit save = this.benefitService.save(benefit);
		super.authenticate(null);
		super.authenticate("admin");
		save.setDescription("asdf");
		final Benefit save2 = this.benefitService.saveByAdmin(save);
		Assert.isTrue(this.benefitService.findAll().contains(save2));
		super.authenticate(null);
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			//normal
			{
				"manager1", "sample name", "sample description", "http://www.picture.com", null
			},
			//un usuario intentando guardar un benefit
			{
				"user1", "sample name", "sample description", "http://www.picture.com", IllegalArgumentException.class
			},
			//el name esta vacio
			{
				"manager1", "", "sample description", "http://www.picture.com", ConstraintViolationException.class
			},
			//description esta vacio
			{
				"manager1", "sample name", "", "http://www.picture.com", ConstraintViolationException.class
			},
			//picture no esta en formato url
			{
				"manager1", "sample name", "sample description", "picture", ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void templateSave(final String username, final String name, final String description, final String picture, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Benefit benefit = this.benefitService.create();
			benefit.setName(name);
			benefit.setDescription(description);
			benefit.setPicture(picture);
			final Benefit save = this.benefitService.save(benefit);
			this.benefitService.flush();
			Assert.isTrue(this.benefitService.findAll().contains(save));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverSave2() {
		final Object testingData[][] = {
			//intentar modificar un benefit que tiene requests
			{
				"manager1", "benefit1", "sample description", IllegalArgumentException.class
			},//un manager intentando actualizar un benefit que no es suyo
			{
				"manager1", "benefit2", "sample description", IllegalArgumentException.class
			},
			//un administrador intentando actualizar un benefit
			{
				"admin", "benefit2", "sample description", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSave2((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateSave2(final String username, final int benefitId, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Benefit benefit = this.benefitService.findOne(benefitId);
			benefit.setDescription(description);
			final Benefit save = this.benefitService.save(benefit);
			this.benefitService.flush();
			Assert.isTrue(this.benefitService.findAll().contains(save));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test
	public void testSaveByAdmin() {
		super.authenticate("admin");
		final Benefit benefit = this.benefitService.create();
		benefit.setName("nombre1");
		benefit.setDescription("sample description");
		benefit.setPicture("http://www.picture.com");
		final Benefit save = this.benefitService.saveByAdmin(benefit);
		Assert.isTrue(this.benefitService.findAll().contains(save));

		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	//un usuario intenta guardar un benefit
	public void testSaveByAdmin2() {
		super.authenticate("user1");
		final Benefit benefit = this.benefitService.create();
		benefit.setName("nombre1");
		benefit.setDescription("sample description");
		benefit.setPicture("http://www.picture.com");
		final Benefit save = this.benefitService.saveByAdmin(benefit);
		Assert.isTrue(this.benefitService.findAll().contains(save));

		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	//intentar eliminar un benefit con requests
	public void testDelete() {
		super.authenticate("manager1");

		final Benefit benefit = this.benefitService.findOne(super.getEntityId("benefit1"));
		this.benefitService.delete(benefit);
		Assert.isTrue(!this.benefitService.findAll().contains(benefit));

		super.authenticate(null);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			//normal
			{
				"manager1", "manager1", null
			},
			//un manager intenta borrar el benefit de otro manager
			{
				"manager2", "manager1", IllegalArgumentException.class
			},//un admin intentando borrar un benefit
			{
				"manager1", "admin", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateDelete(final String username1, final String username2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username1);
			final Benefit benefit = this.benefitService.create();
			benefit.setName("sample name");
			benefit.setDescription("sample description");
			benefit.setPicture("http://www.picture.com");
			final Benefit save = this.benefitService.save(benefit);
			super.unauthenticate();
			super.authenticate(username2);
			this.benefitService.delete(save);
			Assert.isTrue(!this.benefitService.findAll().contains(save));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	//cancelar 
	public void testCancelBenefit() {
		super.authenticate("admin");
		final Benefit benefit = this.benefitService.findOne(super.getEntityId("benefit2"));

		this.benefitService.cancelBenefit(benefit);
		Assert.isTrue(benefit.getFlag().equals("CANCELLED"));
		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	//intentar cancelar un benefit que ya esta cancelado
	public void testCancelBenefit2() {
		super.authenticate("admin");
		final Benefit benefit = this.benefitService.findOne(super.getEntityId("benefit2"));

		this.benefitService.cancelBenefit(benefit);
		this.benefitService.cancelBenefit(benefit);
		Assert.isTrue(benefit.getFlag().equals("CANCELLED"));
		super.authenticate(null);

	}
}
