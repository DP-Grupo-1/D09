
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Comment;
import domain.Flag;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {

	@Autowired
	private CommentService		commentService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	// Creation--------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer rendezvousId) {
		ModelAndView result = null;
		Comment comment;
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getAttendants().contains(principal));
		Assert.isTrue(!(rendezvous.getFlag().equals(Flag.DELETED)));
		try {
			comment = this.commentService.create(rendezvous);

			result = this.createEditModelAndView(comment);
			result.addObject("rendezvousId", rendezvousId);
		}

		catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			System.out.println(oops);
		}
		return result;
	}

	// Edit----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int commentId) {
		final ModelAndView res;
		final Comment comment = this.commentService.findOne(commentId);
		if (commentId != 0) {
			final User user = this.userService.findByPrincipal();

			Assert.isTrue(user.getComments().contains(comment));
		}

		res = this.createEditModelAndView(comment);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final Integer rendezvousId, @Valid final Comment comment, final BindingResult binding) {

		ModelAndView result;
		Collection<Comment> comments = new ArrayList<Comment>();
		final User principal = this.userService.findByPrincipal();
		//comment = this.commentService.reconstruct(comment, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(comment);
			result.addObject("rendezvousId", rendezvousId);
			result.addObject("requestURI", "comment/user/edit.do?rendezvousId=" + rendezvousId);
		} else
			try {
				if (comment.getId() != 0)
					Assert.isTrue(principal.getComments().contains(comment));
				final Comment com = this.commentService.save(comment);

				final Rendezvous r = this.rendezvousService.findOne(rendezvousId);
				if (!(r.getComments().contains(com))) {
					comments = r.getComments();

					comments.add(com);

					r.setComments(comments);

					this.rendezvousService.onlySave(r);
				}

				result = new ModelAndView("redirect:/rendezvous/user/listRsvps.do");

			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return result;
	}

	// DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Comment comment, final BindingResult binding) {

		ModelAndView result;
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(principal.getComments().contains(comment));
		try {

			this.commentService.delete(comment);
			result = new ModelAndView("redirect:/comment/user/list.do");

		}

		catch (final Throwable oops) {
			result = this.createEditModelAndView(comment, "comment.commit.error");

		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/user/edit");
		result.addObject("comment", comment);
		result.addObject("message", message);
		return result;
	}

}
