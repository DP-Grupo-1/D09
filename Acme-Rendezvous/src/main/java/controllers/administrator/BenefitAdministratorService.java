package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AnnouncementService;
import services.BenefitService;
import services.RendezvousService;

import controllers.AbstractController;
import domain.Announcement;
import domain.Benefit;
import domain.Rendezvous;

@Controller
@RequestMapping("/benefit/administrator")
public class BenefitAdministratorService extends AbstractController{

	//Services ----------------------------------------------------------
		@Autowired
		private BenefitService		benefitService;

		@Autowired
		private RendezvousService		rendezvousService;
		
		@Autowired
		private AdministratorService		adminService;
		
		//Cancel ------------------------------------------------
		@RequestMapping(value = "/cancel", method = RequestMethod.GET)
		public ModelAndView cancel(@RequestParam final int benefitId) {
			ModelAndView result;

			final Benefit b = this.benefitService.findOne(benefitId);

			this.benefitService.cancelBenefit(b);

			result = new ModelAndView("redirect:../../benefit/list.do");

			return result;
		}

}
