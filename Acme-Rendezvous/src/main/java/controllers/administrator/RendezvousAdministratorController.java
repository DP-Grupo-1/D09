
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import controllers.AbstractController;
import domain.Rendezvous;

@Controller
@RequestMapping("/rendezvous/administrator")
public class RendezvousAdministratorController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;


	//Edition----------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvousId) {

		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		Assert.notNull(rendezvous);
		result = this.createEditModelAndView(rendezvous);
		result.addObject("rendezvous", rendezvous);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(rendezvous);
		else
			try {
				final Rendezvous saved = this.rendezvousService.save(rendezvous);
				result = new ModelAndView("redirect:../display.do?rendezvousId=" + saved.getId());
			} catch (final Throwable error) {
				result = this.createEditModelAndView(rendezvous, "rendezvous.comit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int rendezvousId) {


		ModelAndView result;
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		this.rendezvousService.deleteByAdmin(rendezvous);
		result = new ModelAndView("redirect:../../welcome/index.do");
		
		return result;
	}

	// Ancillary methods -----------------------------------------
	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous) {
		ModelAndView result;
		result = this.createEditModelAndView(rendezvous, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous, final String message) {
		ModelAndView result;

		result = new ModelAndView("rendezvous/administrator/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("message", message);
		return result;
	}
}
