
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BenefitService;
import services.ManagerService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Benefit;

@Controller
@RequestMapping("/benefit/manager")
public class BenefitManagerController extends AbstractController {

	@Autowired
	private BenefitService		benefitService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private RendezvousService	rendezvousService;


	//Creation--------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Benefit benefit;
		benefit = this.benefitService.create();

		result = this.createEditModelAndView(benefit);
		result.addObject("requestURI", "benefit/manager/edit.do?benefitId=" + benefit.getId());

		return result;
	}

	//Edit----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int benefitId) {
		final ModelAndView res;
		final Benefit benefit = this.benefitService.findOne(benefitId);
		res = this.createEditModelAndView(benefit);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Benefit benefit, final BindingResult binding) {

		ModelAndView result;
		benefit = this.benefitService.reconstruct(benefit, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(benefit);
		else

			try {

				this.benefitService.save(benefit);
				result = new ModelAndView("redirect:/welcome/index.do");

			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(benefit, "benefit.comit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Benefit benefit, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(benefit);
		else
			try {
				this.benefitService.delete(benefit);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(benefit, "benefit.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Benefit benefit) {
		ModelAndView result;

		result = this.createEditModelAndView(benefit, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Benefit benefit, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("benefit/edit");
		result.addObject("benefit", benefit);
		result.addObject("message", messageCode);
		return result;
	}

}
