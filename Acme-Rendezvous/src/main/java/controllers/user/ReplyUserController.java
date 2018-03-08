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

import security.LoginService;
import security.UserAccount;
import services.CommentService;
import services.ReplyService;
import services.UserService;
import controllers.AbstractController;
import domain.Comment;
import domain.Reply;
import domain.User;

@Controller
@RequestMapping("/reply/user")
public class ReplyUserController extends AbstractController {

	@Autowired
	private ReplyService replyService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	// Creation--------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer commentId) {
		ModelAndView result;
		Reply reply;
		final Comment comment = this.commentService.findOne(commentId);
		reply = this.replyService.create(comment);

		result = this.createEditModelAndView(reply);

		result.addObject("commentId", commentId);
		return result;
	}

	// Edit----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int replyId) {
		final ModelAndView res;
		final Reply reply = this.replyService.findOne(replyId);
		res = this.createEditModelAndView(reply);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final Integer commentId, @Valid final Reply reply, final BindingResult binding) {

		ModelAndView result;
		Collection<Reply> replies = new ArrayList<Reply>();
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(reply);
			result.addObject("commentId", commentId);
		} else {
			try {
				
				

				 this.replyService.save(reply);

				Comment c = commentService.findOne(commentId);
					
				replies.addAll(c.getReplies());
				
				replies.add(reply);
				
				c.setReplies(replies);
				
				commentService.save(c);
					
					

					result = new ModelAndView("redirect:/rendezvous/user/listRsvps.do");
				}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(reply, "reply.comit.error");
			}
		}

		return result;
	}

	// DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Reply reply,
			final BindingResult binding) {

		ModelAndView result;

		try {

			this.replyService.delete(reply);
			result = new ModelAndView("redirect:/reply/user/list.do");

		}

		catch (final Throwable oops) {
			result = this.createEditModelAndView(reply, "reply.comit.error");

		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Reply reply) {
		ModelAndView result;

		result = this.createEditModelAndView(reply, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Reply reply,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("reply/user/edit");
		result.addObject("reply", reply);
		result.addObject("message", messageCode);
		return result;
	}

}
