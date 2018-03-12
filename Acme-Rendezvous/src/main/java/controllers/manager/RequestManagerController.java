
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Benefit;
import domain.Request;

@Controller
@RequestMapping("/request/manager")
public class RequestManagerController extends AbstractController {

	@Autowired
	private RequestService	requestService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Benefit benefit) {
		ModelAndView res;
		final Collection<Request> requests = this.requestService.findAllByBenefit(benefit.getId());

		res = new ModelAndView("requests/list");
		res.addObject("requestURI", "requests/list.do");
		res.addObject("requests", requests);

		return res;
	}
}
