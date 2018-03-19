
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	//Services ----------------------------------------------------------
	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private AdministratorService	administratorService;


	//Creation--------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = null;

		try {
			final Administrator administrator = this.administratorService.findByPrincipal();
			Assert.notNull(administrator);
			final Collection<Category> categories = this.categoryService.findAll();

			final Category category = this.categoryService.create();

			result = this.createEditModelAndView(category);
			result.addObject("category", category);
			result.addObject("categories", categories);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			System.out.println(oops);
		}

		return result;
	}

	//Edition----------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {

		ModelAndView result;
		Category category;
		category = this.categoryService.findOne(categoryId);
		try {

			Assert.notNull(category);
			final Collection<Category> categories = this.categoryService.findAll();

			result = this.createEditModelAndView(category);
			result.addObject("category", category);
			result.addObject("categories", categories);

		} catch (final Throwable error) {
			System.out.println(error);
			result = this.createEditModelAndView(category, "category.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, final BindingResult binding) {
		ModelAndView result;
		category = this.categoryService.reconstruct(category, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(category);
		} else
			try {

				this.categoryService.save(category);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable error) {
				System.out.println(error);
				result = this.createEditModelAndView(category, "category.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Category category, final BindingResult binding) {

		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category, "category.commit.error");
		}
		return result;
	}

	//Listing ----------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Category> categories = new ArrayList<Category>();
		categories = this.categoryService.findAll();

		result = new ModelAndView("category/administrator/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	// Ancillary methods -----------------------------------------
	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;
		result = this.createEditModelAndView(category, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/administrator/edit");
		result.addObject("category", category);
		result.addObject("message", message);
		return result;
	}
}
