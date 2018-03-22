
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Flag;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	//Listing ----------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final Date actualMoment = new Date(System.currentTimeMillis() - 1000);
		ModelAndView result;
		Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
		final User logged = this.userService.findByPrincipal();
		//	if(logged.id)
		rendezvouses = this.rendezvousService.findByCreatorId(logged.getId());

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/user/list.do");
		result.addObject("actualMoment", actualMoment);

		return result;
	}

	//Listing ----------------------------------------------------
	@RequestMapping(value = "/listRsvps", method = RequestMethod.GET)
	public ModelAndView list2() {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
		final User logged = this.userService.findByPrincipal();

		rendezvouses = logged.getAttendances();

		result = new ModelAndView("rendezvous/user/list2");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/user/listRsvps.do");

		return result;
	}

	//Create----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = null;

		try {
			final User user = this.userService.findByPrincipal();
			Assert.notNull(user);
			final Rendezvous rendezvous = this.rendezvousService.create();

			result = this.createEditModelAndView(rendezvous);
			result.addObject("rendezvous", rendezvous);
			result.addObject("adult", user.getAdult());
			result.addObject("finalMode", rendezvous.getFinalMode());
			result.addObject("requestURI", "rendezvous/user/edit.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			System.out.println(oops);
		}

		return result;
	}

	//Edition----------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvousId) {

		ModelAndView result;
		Rendezvous rendezvous;
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getCreator().equals(principal));
		try {

			Assert.notNull(rendezvous);
			Assert.isTrue(rendezvous.getFinalMode() == false);

			result = this.createEditModelAndView(rendezvous);
			result.addObject("rendezvous", rendezvous);
			result.addObject("finalMode", rendezvous.getFinalMode());
			result.addObject("requestURI", "rendezvous/user/edit.do");

		} catch (final Throwable error) {
			System.out.println(error);
			result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;
		//rendezvous = this.rendezvousService.reconstruct(rendezvous, binding);
		if (binding.hasErrors()) {
			final User user = this.userService.findByPrincipal();
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(rendezvous);
			result.addObject("finalMode", rendezvous.getFinalMode());
			result.addObject("adult", user.getAdult());
		} else
			try {

				final Rendezvous saved = this.rendezvousService.save(rendezvous);
				result = new ModelAndView("redirect:../display.do?rendezvousId=" + saved.getId());
			} catch (final Throwable error) {
				System.out.println(error);
				result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Rendezvous rendezvous, final BindingResult binding) {

		ModelAndView result;
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getCreator().equals(principal));
		try {
			this.rendezvousService.deleteByUser(rendezvous);
			result = new ModelAndView("redirect:../display.do?rendezvousId=" + rendezvous.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
		}
		return result;
	}

	//Attend ------------------------------------------------------------

	@RequestMapping(value = "/attend", method = RequestMethod.GET)
	public ModelAndView attend(@RequestParam final int rendezvousId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			final User user = this.userService.findByPrincipal();
			final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
			Assert.notNull(user);
			Assert.notNull(rendezvous);

			this.rendezvousService.rsvp(rendezvous);

			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");

		} catch (final Throwable oops) {
			System.out.println(oops.getLocalizedMessage());
			System.out.println(oops.getMessage());
			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:/rendezvous/list.do");

		return result;
	}

	@RequestMapping(value = "/noAttend", method = RequestMethod.GET)
	public ModelAndView noAttend(@RequestParam final int rendezvousId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {

			final User user = this.userService.findByPrincipal();
			final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);

			Assert.notNull(user);
			Assert.notNull(rendezvous);

			Collection<Rendezvous> attendances = new ArrayList<Rendezvous>();
			attendances = user.getAttendances();
			attendances.remove(rendezvous);
			user.setAttendances(attendances);
			this.userService.onlySave(user);

			Collection<User> attendants = new ArrayList<User>();
			attendants = rendezvous.getAttendants();
			attendants.remove(user);
			rendezvous.setAttendants(attendants);
			this.rendezvousService.onlySave(rendezvous);

			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (final Throwable oops) {

			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:/rendezvous/list.do");

		return result;
	}

	// Link ------------------------------------------------------

	@RequestMapping(value = "/rendezvouses", method = RequestMethod.GET)
	public ModelAndView rendezvouses(@RequestParam final int rendezvousId, final RedirectAttributes redirectAttrs) {
		ModelAndView result = null;

		try {
			Rendezvous rendezvous;
			Collection<Rendezvous> rendezvouses;
			User u;
			rendezvous = this.rendezvousService.findOne(rendezvousId);
			Assert.notNull(rendezvous);
			Assert.isTrue(!(rendezvous.getFlag().equals(Flag.DELETED)), "The rendezvous is deleted");
			u = this.userService.findByPrincipal();
			Assert.notNull(u);
			rendezvouses = this.rendezvousService.findAll();
			rendezvouses.remove(rendezvous);
			Assert.notNull(rendezvouses);

			result = new ModelAndView("rendezvous/user/rendezvouses");
			result.addObject("rendezvous", rendezvous);
			result.addObject("rendezvousId", rendezvousId);
			result.addObject("userId", u.getId());
			result.addObject("rendezvouses", rendezvouses);

			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getLocalizedMessage());
			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		return result;
	}

	@RequestMapping(value = "/link", method = RequestMethod.GET)
	public ModelAndView link(@RequestParam final int rendezvousId, @RequestParam final int rendezvousLinkId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {

			final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
			Assert.notNull(rendezvous);
			Assert.isTrue(!(rendezvous.getFlag().equals(Flag.DELETED)), "The rendezvous is deleted");
			final Rendezvous rendezvousLink = this.rendezvousService.findOne(rendezvousLinkId);
			Assert.notNull(rendezvousLink);
			Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
			rendezvouses = rendezvous.getRendezvouses();
			rendezvouses.add(rendezvousLink);
			rendezvous.setRendezvouses(rendezvouses);
			this.rendezvousService.onlySave(rendezvous);

			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getLocalizedMessage());
			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:list.do");
		return result;
	}

	@RequestMapping(value = "/removeLink", method = RequestMethod.GET)
	public ModelAndView removeLink(@RequestParam final int rendezvousId, @RequestParam final int rendezvousLinkedId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
			Assert.notNull(rendezvous);
			final Rendezvous rendezvousLink = this.rendezvousService.findOne(rendezvousLinkedId);
			Assert.notNull(rendezvousLink);
			Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
			rendezvouses = rendezvous.getRendezvouses();
			rendezvouses.remove(rendezvousLink);
			rendezvous.setRendezvouses(rendezvouses);
			this.rendezvousService.onlySave(rendezvous);

			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getLocalizedMessage());
			redirectAttrs.addFlashAttribute("message", "rendezvous.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:list.do");
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

		result = new ModelAndView("rendezvous/user/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("message", message);
		return result;
	}
}
