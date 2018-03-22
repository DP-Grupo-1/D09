
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BenefitService;
import services.RendezvousService;
import services.RequestService;
import services.UserService;
import controllers.AbstractController;
import domain.Benefit;
import domain.Rendezvous;
import domain.Request;
import domain.User;
import forms.RequestBenefit;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private UserService			userService;

	@Autowired
	private BenefitService		benefitService;


	@RequestMapping(value = "/requestService", method = RequestMethod.GET)
	public ModelAndView requestBenefit(@RequestParam final int benefitId) {

		ModelAndView result;

		final Benefit benefit = this.benefitService.findOne(benefitId);
		final User principal = this.userService.findByPrincipal();
		Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
		if (benefit.getRendezvouses().size() > 0)
			rendezvouses = this.rendezvousService.findByCreatorIdAndRendezvouses(principal.getId(), benefit);
		else
			rendezvouses = this.rendezvousService.findByCreatorId(principal.getId());
		final RequestBenefit requestBenefit = new RequestBenefit();

		requestBenefit.setBenefit(benefit);

		if (!(principal.getCreditCard().equals(null))) {
			requestBenefit.setBrandName(principal.getCreditCard().getBrandName());
			requestBenefit.setCvv(principal.getCreditCard().getCvv());
			requestBenefit.setExpirationMonth(principal.getCreditCard().getExpirationMonth());
			requestBenefit.setExpirationYear(principal.getCreditCard().getExpirationYear());
			requestBenefit.setHolderName(principal.getCreditCard().getHolderName());
			requestBenefit.setNumber(principal.getCreditCard().getNumber());
		}

		result = this.createEditModelAndView(requestBenefit);
		result.addObject("requestBenefit", requestBenefit);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "request/user/requestService.do?benefitId=" + benefitId);

		return result;
	}

	@RequestMapping(value = "/requestService", method = RequestMethod.POST, params = "save")
	public ModelAndView requestBenefit(@Valid final RequestBenefit requestBenefit, final BindingResult binding) {

		ModelAndView result = null;

		if (binding.hasErrors()) {
			final Benefit benefit = this.benefitService.findOne(requestBenefit.getBenefit().getId());
			final User principal = this.userService.findByPrincipal();
			Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
			if (benefit.getRendezvouses().size() > 0)
				rendezvouses = this.rendezvousService.findByCreatorIdAndRendezvouses(principal.getId(), benefit);
			else
				rendezvouses = this.rendezvousService.findByCreatorId(principal.getId());
			result = this.createEditModelAndView(requestBenefit);
			result.addObject("rendezvouses", rendezvouses);
			result.addObject("requestURI", "request/user/requestService.do?benefitId=" + benefit.getId());
		} else
			try {
				final Request request = this.requestService.create();
				this.requestService.save(request, requestBenefit.getBenefit(), requestBenefit.getRendezvous());
				result = new ModelAndView("redirect:/welcome/index.do");
			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(requestBenefit, "request.comit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final RequestBenefit requestBenefit) {
		ModelAndView result;

		result = this.createEditModelAndView(requestBenefit, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final RequestBenefit requestBenefit, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("request/user/requestService");
		result.addObject("requestBenefit", requestBenefit);
		result.addObject("message", messageCode);
		return result;
	}
}
