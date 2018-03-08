
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import services.CommentService;
import services.RendezvousService;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	
	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private CommentService commentService;


	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer rendezvousId) {
		ModelAndView res;
		Collection<Comment> comments;
		comments = this.rendezvousService.findByRendezvous(rendezvousId);

		res = new ModelAndView("comment/list");
		res.addObject("comments", comments);
		res.addObject("requestURI", "comment/list.do");
		return res;
	}
	
	//Display
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView Display(@RequestParam(required = false) final Integer commentId) {
			ModelAndView res;
			Comment comment;
			comment = this.commentService.findOne(commentId);


			res = new ModelAndView("comment/display");
			res.addObject("comment", comment);
			res.addObject("requestURI", "comment/display.do");
			return res;
		}


	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("comment/user/edit");
		result.addObject("comment", comment);
		result.addObject("message", messageCode);
		return result;
	}

}
