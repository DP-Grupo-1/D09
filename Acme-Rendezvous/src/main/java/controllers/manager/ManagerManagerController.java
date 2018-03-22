
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("/manager/manager")
public class ManagerManagerController {

	//Services------------------------------------------------------------------
	@Autowired
	ManagerService	managerService;


	//Edit------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView res;
		final Manager manager = this.managerService.findByPrincipal();
		Assert.notNull(manager);
		res = this.createEditModelAndView(manager);
		return res;
	}
	//Save----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		Assert.notNull(manager);
		ModelAndView res;
		//manager = this.managerService.reconstruct(manager, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res = this.createEditModelAndView(manager);
		} else
			try {

				this.managerService.save(manager);
				res = new ModelAndView("redirect:../../welcome/index.do");
			} catch (final Throwable error) {
				res = this.createEditModelAndView(manager, "manager.error");
			}
		return res;

	}
	private ModelAndView createEditModelAndView(final Manager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;

		result = new ModelAndView("manager/edit");
		result.addObject("manager", manager);
		result.addObject("message", message);
		return result;
	}

}
