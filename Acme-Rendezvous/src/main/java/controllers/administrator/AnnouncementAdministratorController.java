/* CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvous;

@Controller
@RequestMapping("/announcement/administrator")
public class AnnouncementAdministratorController extends AbstractController {


	//Services ----------------------------------------------------------
	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private RendezvousService		rendezvousService;




	//Delete ------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int announcementId) {
		ModelAndView result;

		final Rendezvous r = this.rendezvousService.findByAnnouncementId(announcementId);
		final Announcement a = this.announcementService.findOne(announcementId);


		r.getAnnouncements().remove(a);
		this.rendezvousService.onlySave(r);

		this.announcementService.delete(a);

		result = new ModelAndView("redirect:../../rendezvous/list.do");

		return result;
	}

}
