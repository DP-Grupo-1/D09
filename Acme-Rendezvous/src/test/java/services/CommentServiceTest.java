
package services;

import java.util.Collection;
import java.util.List;

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
import domain.Rendezvous;
import domain.Reply;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	//Service under test--------------------------------------------------------------
	@Autowired
	private CommentService		commentService;
	@Autowired
	private ReplyService		replyService;
	@Autowired
	private UserService			userService;
	@Autowired
	private RendezvousService	rendezvousService;


	//Tests------------------------------------------------------------------------
	@Test
	public void testCreate() {
		final int rendezvousId = super.getEntityId("rendezvous2");
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final Comment comment = this.commentService.create(rendezvous);
		Assert.notNull(comment.getMoment());
		Assert.isNull(comment.getText());
		Assert.isNull(comment.getPicture());
		Assert.notNull(comment.getReplies());
		System.out.println("TEST CREATE================================");
		System.out.println(comment.getMoment());
		System.out.println(comment.getReplies());
	}
	@Test
	public void testSave() {
		final int rendezvousId = super.getEntityId("rendezvous2");
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		super.authenticate("user1");
		final User user = this.userService.findByPrincipal();
		final Comment comment = this.commentService.create(rendezvous);
		comment.setText("asdff");
		comment.setPicture("http://wwww.picture.com");
		System.out.println("TEST SAVE====================================");
		System.out.println("Lista de comentarios del usuario antes: " + user.getComments());
		System.out.println("Lista de comments del sistema antes:" + this.commentService.findAll());
		final Comment save = this.commentService.save(comment);
		System.out.println("Lista de comments del sistema despues:" + this.commentService.findAll());
		System.out.println("Lista de comentarios del usuario despues: " + user.getComments());
		Assert.isTrue(this.commentService.findAll().contains(save));
		super.authenticate(null);
	}

	//	@Test
	//	public void testDelete() {
	//		super.authenticate("admin");
	//		final List<Comment> comments = (List<Comment>) this.commentService.findAll();
	//		//		final Comment comment = comments.get(0);
	//		final Comment comment = this.commentService.findOne(super.getEntityId("comment1"));
	//		System.out.println(comments);
	//		final Collection<Reply> replies = this.replyService.findAll();
	//		System.out.println(replies);
	//
	//		this.commentService.delete(comment);
	//		final Collection<Reply> replies2 = this.replyService.findAll();
	//
	//		System.out.println(replies2);
	//		final List<Comment> comments2 = (List<Comment>) this.commentService.findAll();
	//
	//		System.out.println(comments2);
	//		Assert.isTrue(!this.commentService.findAll().contains(comment));
	//		super.authenticate(null);
	//	}
	@Test
	public void testFindAll() {
		final List<Comment> comments = (List<Comment>) this.commentService.findAll();
		System.out.println(comments);

	}
	//

	@Test
	public void driverUpdate() {
		final Object testingData[][] = {
			//Normal
			{
				"user1", 206, "asdf", "http://www.picture.com", null
			},
			//Normal sin pasarle un picture
			{
				"user1", 206, "asdf", null, null
			},
			//Un usuario no autenticado editando un comment
			{
				null, 206, "asdf", "http://www.picture.com", IllegalArgumentException.class
			},
			//Un admin intentando actualizar un comment
			{
				"admin", 206, "asdf", "http://www.picture.com", IllegalArgumentException.class
			},
			//El texto esta vacio
			{
				"user1", 206, null, "http://www.picture.com", ConstraintViolationException.class
			},
			//El picture no esta en formato url
			{
				"user1", 206, "a", "foto", ConstraintViolationException.class
			},
			//			//Un usuario intentando actualizar el comment de otro usuario
			{
				"user2", 206, "a", "http://www.picture.com", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateUpdate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void templateUpdate(final String username, final int commentId, final String text, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Comment comment = this.commentService.findOne(commentId);
			comment.setText(text);
			comment.setPicture(picture);

			final Comment save = this.commentService.save(comment);
			this.commentService.flush();

			Assert.isTrue(this.commentService.findAll().contains(save));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			//Normal
			{
				"admin", "comment2", null
			},
			//Un user intentando eliminar un comment
			{
				"user2", "comment2", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);

		{
		}
	}
	protected void templateDelete(final String username, final int commentId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Comment comment = this.commentService.findOne(commentId);
			this.commentService.delete(comment);
			Assert.isTrue(!this.commentService.findAll().contains(comment));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void testFindByCommentId() {
		final Collection<Reply> replies = this.commentService.findByCommentId(super.getEntityId("comment1"));
		Assert.notNull(replies);
		System.out.println("Replies del comment1: " + replies);
	}
	@Test
	public void testAvgRepliesPerComment() {
		final Double avg = this.commentService.avgRepliesPerComment();
		Assert.notNull(avg);
		System.out.println("Media de replies por comentario: " + avg);
	}
	@Test
	public void testStdevRepliesPerComment() {
		final Double stdev = this.commentService.stdevRepliesPerComment();
		Assert.notNull(stdev);
		System.out.println("Desviacion estandar de replies por comentario: " + stdev);
	}
}
