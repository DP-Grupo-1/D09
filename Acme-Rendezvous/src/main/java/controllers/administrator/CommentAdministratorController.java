
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import domain.Comment;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController {

	@Autowired
	private CommentService	commentService;


	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int commentId) {
		ModelAndView res;
		Assert.notNull(commentId);
		final Comment comment = this.commentService.findOne(commentId);
				try {
		this.commentService.quitarCommentReply(comment);
		this.commentService.delete(comment);
		res = new ModelAndView("redirect:../../welcome/index.do");

				} catch (final Throwable error) {
					res = new ModelAndView("redirect:../../welcome/index.do");
		
				}

		return res;
	}
}
