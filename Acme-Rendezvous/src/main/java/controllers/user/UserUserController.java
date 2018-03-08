
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController {

	//Services------------------------------------------------------------------
	@Autowired
	UserService	userService;


	//Edit------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView res;
		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		res = this.createEditModelAndView(user);
		return res;
	}
	//Save----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid User user, final BindingResult binding) {
		Assert.notNull(user);
		ModelAndView res;
		user = this.userService.reconstruct(user, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res = this.createEditModelAndView(user);
		} else
			try {
				
				this.userService.save(user);
				res = new ModelAndView("redirect:../../welcome/index.do");
			} catch (final Throwable error) {
				res = this.createEditModelAndView(user, "user.error");
			}
		return res;

	}
	private ModelAndView createEditModelAndView(final User user) {
		ModelAndView result;

		result = this.createEditModelAndView(user, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final User user, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("message", message);
		return result;
	}
}
