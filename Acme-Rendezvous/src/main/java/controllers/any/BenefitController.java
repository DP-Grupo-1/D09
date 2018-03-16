
package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BenefitService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Benefit;
import domain.Rendezvous;

@Controller
@RequestMapping("/benefit")
public class BenefitController extends AbstractController {

	//Services------------------------------------------------------------------
	@Autowired
	UserService			userService;

	@Autowired
	BenefitService		benefitService;

	@Autowired
	RendezvousService	rendezvousService;


	//	List--------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Benefit> benefits = this.benefitService.findAll();

		res = new ModelAndView("benefit/list");
		res.addObject("requestURI", "benefit/list.do");
		res.addObject("benefits", benefits);

		return res;
	}

	@RequestMapping(value = "/listRequested", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView res;
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final Collection<Benefit> benefits = this.benefitService.findAllRequestedByRendezvous(rendezvous);

		res = new ModelAndView("benefit/list");
		res.addObject("requestURI", "benefit/listRequested.do");
		res.addObject("benefits", benefits);

		return res;
	}
}
