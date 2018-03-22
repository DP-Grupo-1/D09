
package controllers.any;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.RendezvousService;
import domain.Manager;
import forms.RegisterManager;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	//Services------------------------------------------------------------------
	@Autowired
	ManagerService		managerService;

	@Autowired
	RendezvousService	rendezvousService;


	//	List--------------------------------------------------------------------------
	/*
	 * @RequestMapping(value = "/list", method = RequestMethod.GET)
	 * public ModelAndView list(@RequestParam(required = false) final Integer rendezvousId) {
	 * ModelAndView res;
	 * final Collection<Manager> managers = this.managerService.findAll();
	 * 
	 * res = new ModelAndView("manager/list");
	 * 
	 * if (rendezvousId != null) {
	 * // id = rendezvousId.toString();
	 * final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
	 * res.addObject("managers", rendezvous.getAttendants());
	 * res.addObject("rendezvousId", rendezvousId);
	 * res.addObject("uri", "manager/list.do?rendezvousId=" + rendezvousId);
	 * } else {
	 * res.addObject("requestURI", "manager/list.do");
	 * res.addObject("managers", managers);
	 * }
	 * return res;
	 * }
	 */

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//		ModelAndView res;
	//		final Collection<manager> managers = this.managerService.findAll();
	//		res = new ModelAndView("manager/list");
	//		res.addObject("managers", managers);
	//		res.addObject("requestURI", "manager/list.do");
	//		return res;
	//	}
	//Display-------------------------------------------------------------------
	/*
	 * @RequestMapping(value = "/display", method = RequestMethod.GET)
	 * public ModelAndView display(@RequestParam final int managerId) {
	 * Assert.notNull(managerId);
	 * ModelAndView res;
	 * final manager manager = this.managerService.findOne(managerId);
	 * final Collection<Rendezvous> attendances = manager.getAttendances();
	 * 
	 * res = new ModelAndView("manager/display");
	 * res.addObject("manager", manager);
	 * res.addObject("rendezvouses", attendances);
	 * res.addObject("requestURI", "manager/display.do");
	 * return res;
	 * }
	 */

	//Create-----------------------------------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final RegisterManager manager = new RegisterManager();
		manager.setAccept(false);
		res = this.createEditModelAndView(manager);

		return res;
	}

	//Edit----------------------------------------------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("register") @Valid final RegisterManager registerManager, final BindingResult binding) {
		Assert.notNull(registerManager);
		ModelAndView res;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res = this.createEditModelAndView(registerManager);
		} else
			try {
				Md5PasswordEncoder encoder;
				String password;

				encoder = new Md5PasswordEncoder();

				final Manager manager = this.managerService.reconstruct(registerManager, binding);
				password = encoder.encodePassword(manager.getUserAccount().getPassword(), null);
				manager.getUserAccount().setPassword(password);
				this.managerService.save(manager);
				res = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable error) {
				//				if (registermanager.getAccept() == false)
				//					res = this.createEditModelAndView(registermanager, "manager.error.accept");
				res = this.createEditModelAndView(registerManager, "manager.error");
			}
		return res;

	}
	private ModelAndView createEditModelAndView(final RegisterManager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final RegisterManager manager, final String message) {
		ModelAndView result;

		result = new ModelAndView("manager/register");
		result.addObject("register", manager);
		result.addObject("message", message);

		return result;
	}
}
