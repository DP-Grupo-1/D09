
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Benefit;
import domain.Category;
import domain.Rendezvous;

@Service
@Transactional
public class CategoryService {

	//Managed repository ----------------------------
	@Autowired
	private CategoryRepository	categoryRepository;

	//Supporting services--------------------------------
	@Autowired
	public AdministratorService	administratorService;

	@Autowired
	public RendezvousService	rendezvousService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods ------------------------

	public Category create() {
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		final Category res = new Category();
		final Collection<Category> childrens = new ArrayList<Category>();
		final Collection<Benefit> benefits = new ArrayList<Benefit>();

		res.setChildrens(childrens);
		res.setBenefits(benefits);
		return res;
	}

	public Category save(final Category category) {

		Assert.notNull(category);

		//Authority
		final Administrator a = this.administratorService.findByPrincipal();
		Assert.notNull(a);

		//Una categoría no puede ser padre ni hija de sí misma
		Assert.isTrue(!(category.getId() == (category.getParent().getId())));
		Assert.isTrue(!this.isSonOfItself(category.getId()));

		//Si no tiene categoría padre, se le asigna CATEGORY
		if (category.getParent() == null)
			category.setParent(this.findCATEGORY());

		if (category.getId() != 0) {
			final Category bd = this.categoryRepository.findOne(category.getId());
			Assert.notNull(bd);
			Assert.isTrue(!this.hasABrotherWithSameName(category.getId(), category.getName()));

		}
		//Authority

		Category result = new Category();

		result = this.categoryRepository.save(category);

		return result;

	}

	public void delete(final Category category) {

		Assert.notNull(category);
		final Category bd = this.categoryRepository.findOne(category.getId());
		Assert.notNull(bd);

		//No se puede borrar la categoría CATEGORY
		Assert.isTrue(!(category.equals(this.findCATEGORY())));

		//Authority
		final Administrator a = this.administratorService.findByPrincipal();
		Assert.notNull(a);

		final Category parent = category.getParent();
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findByCategory(category.getId());
		for (final Rendezvous r : rendezvouses)
			r.getCategories().remove(category);
		parent.getChildrens().remove(category);

		this.categoryRepository.delete(category);
	}

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();

	}

	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);

	}

	public Category findCATEGORY() {
		return this.categoryRepository.findCATEGORY();
	}

	public boolean hasABrotherWithSameName(final int categoryId, final String categoryName) {
		for (final Category c : this.categoryRepository.findSubcategoriesById(categoryId))
			if (c.getName().equals(categoryName))
				return true;
		return false;
	}

	public boolean isSonOfItself(final int categoryId) {
		for (final Category c : this.categoryRepository.findSubcategoriesById(categoryId))
			if (c.getId() == categoryId)
				return true;
		return false;
	}

	public Collection<Category> findChildren(final Integer categoryId) {
		Collection<Category> res;
		final Category categoryFather = this.categoryRepository.findCATEGORY();
		if (categoryId == null)
			res = this.categoryRepository.findChildren(categoryFather.getId());
		else
			res = this.categoryRepository.findChildren(categoryId);

		return res;
	}

	public Collection<Category> findByBenefitId(final int benefitId) {

		return this.categoryRepository.findByBenefitId(benefitId);
	}

	public Category reconstruct(final Category category, final BindingResult binding) {
		Category res;
		if (category.getId() == 0)
			res = category;
		else {
			res = this.categoryRepository.findOne(category.getId());
			res.setName(category.getName());
			res.setDescription(category.getDescription());
			res.setChildrens(category.getChildrens());
			res.setBenefits(category.getBenefits());
			if (category.getParent() == null)
				category.setParent(this.findCATEGORY());
			res.setParent(category.getParent());

			this.validator.validate(res, binding);
		}

		return res;
	}

}
