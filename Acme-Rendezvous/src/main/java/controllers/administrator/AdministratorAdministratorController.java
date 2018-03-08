
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController {

	//Services----------------------------------------------------
	@Autowired
	AdministratorService	administratorService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;

		final UserAccount actual = LoginService.getPrincipal();
		administrator = this.administratorService.findByUserAccount(actual);

		Assert.notNull(administrator);

		result = this.createEditModelAndView(administrator);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(administrator);
		else
			try {
				this.administratorService.save(administrator);
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable error) {
				res = this.createEditModelAndView(administrator, "actor.commit.error");
			}
		return res;
	}

	private ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", message);

		return result;
	}
}
