
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service under test ------------------------------

	@Autowired
	private CategoryService	categoryService;


	//-Comprobar que si un Admin crea una categoria es correcto
	@Test
	public void testCreate() {
		super.authenticate("admin");

		final Category category;
		category = this.categoryService.create();
		Assert.isNull(category.getName());
		Assert.isNull(category.getParent());
		Assert.notNull(category.getChildrens());

		super.unauthenticate();

	}

	//-Comprobar que si un actor que no sea admin crea la category falla
	@Test(expected = IllegalArgumentException.class)
	public void testCreate2() {
		super.authenticate("user1");
		final Category category;
		category = this.categoryService.create();
		Assert.isNull(category.getName());
		Assert.isNull(category.getParent());
		Assert.notNull(category.getChildrens());
		super.authenticate(null);
	}

	@Test
	//normal
	public void testSave() {
		super.authenticate("admin");
		final Category category = this.categoryService.create();
		category.setName("sampleCategory");
		category.setDescription("this is a sample category");
		final Category save = this.categoryService.save(category);
		Assert.isTrue(this.categoryService.findAll().contains(save));
		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	//un actor distinto de admin intenta guardar un category
	public void testSave2() {
		super.authenticate("manager1");
		final Category category = this.categoryService.create();
		category.setName("sampleCategory");
		category.setDescription("this is a sample category");
		final Category save = this.categoryService.save(category);
		Assert.isTrue(this.categoryService.findAll().contains(save));
		super.authenticate(null);
	}
	@Test
	//borrar una categoria que no tiene ninguna relacion
	public void testDelete() {
		super.authenticate("admin");
		final Category category = this.categoryService.create();
		category.setName("sampleCategory");
		category.setDescription("this is a sample category");
		final Category save = this.categoryService.save(category);
		Assert.isTrue(this.categoryService.findAll().contains(save));
		this.categoryService.delete(save);
		Assert.isTrue(!this.categoryService.findAll().contains(save));

		super.authenticate(null);
	}
	@Test
	//borrar una categoria de la base de datos
	public void testDelete2() {
		super.authenticate("admin");
		final Category category = this.categoryService.findOne(super.getEntityId("category2"));
		this.categoryService.delete(category);
		Assert.isTrue(!this.categoryService.findAll().contains(category));

		super.authenticate(null);
	}
}
