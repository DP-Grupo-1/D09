
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;
import domain.Reply;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	//Service under test--------------------------------------------------------------
	@Autowired
	private CommentService	commentService;
	@Autowired
	private ReplyService	replyService;
	@Autowired
	private UserService		userService;


	//Tests------------------------------------------------------------------------
	@Test
	public void testDelete() {
		super.authenticate("admin");
		final List<Comment> comments = (List<Comment>) this.commentService.findAll();
		final Comment comment = comments.get(0);
		System.out.println(comments);
		final Collection<Reply> replies = this.replyService.findAll();
		System.out.println(replies);

		this.commentService.delete(comment);
		final Collection<Reply> replies2 = this.replyService.findAll();

		System.out.println(replies2);
		final List<Comment> comments2 = (List<Comment>) this.commentService.findAll();

		System.out.println(comments2);
		Assert.isTrue(!this.commentService.findAll().contains(comment));
		super.authenticate(null);
	}
	@Test
	public void testFindAll() {
		final List<Comment> comments = (List<Comment>) this.commentService.findAll();
		final Comment comment = comments.get(0);
		System.out.println(comment.getText());

	}
}
