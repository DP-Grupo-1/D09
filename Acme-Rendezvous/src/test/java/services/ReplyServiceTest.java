
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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
	"classpath:spring/junit.xml"
})
@Transactional
public class ReplyServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private ReplyService	replyService;
	@Autowired
	private CommentService	commentService;


	//Test
	@Test
	public void testCreate() {
		final Comment comment = this.commentService.findOne(super.getEntityId("comment2"));
		final Reply reply = this.replyService.create(comment);
		Assert.notNull(reply.getMoment());
		Assert.isNull(reply.getText());
		System.out.println(reply.getMoment());
	}
	@Test
	public void testSave() { //normal
		super.authenticate("user1");
		final Comment comment = this.commentService.findOne(super.getEntityId("comment2"));
		final Reply reply = this.replyService.create(comment);
		reply.setText("asdfasdf");
		final Reply save = this.replyService.save(reply);
		Assert.isTrue(this.replyService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSave2() {//un admin intentando hacer un reply
		super.authenticate("admin");
		final Comment comment = this.commentService.findOne(super.getEntityId("comment1"));
		final Reply reply = this.replyService.create(comment);
		reply.setText("asdfasdf");
		final Reply save = this.replyService.save(reply);
		Assert.isTrue(this.replyService.findAll().contains(save));
		super.authenticate(null);

	}
	@Test(expected = ConstraintViolationException.class)
	public void testSave3() {//se guarda un reply con un texto vacio
		super.authenticate("user1");
		final Comment comment = this.commentService.findOne(super.getEntityId("comment1"));
		final Reply reply = this.replyService.create(comment);
		reply.setText("");
		final Reply save = this.replyService.save(reply);
		Assert.isTrue(this.replyService.findAll().contains(save));
		super.authenticate(null);

	}

}
