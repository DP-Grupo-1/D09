
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CommentRepository;
import domain.Administrator;
import domain.Comment;
import domain.Rendezvous;
import domain.Reply;
import domain.User;

@Service
@Transactional
public class CommentService {

	//Managed repository ----------------------------
	@Autowired
	public CommentRepository		commentRepository;

	// --------SupportingServices----------------------------------
	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RendezvousService		rendezvousService;

	@Autowired
	private ReplyService			replyService;


	//Simple CRUD methods ------------------------

	public Comment create(final Rendezvous rendezvous) {
		final Collection<Reply> replies = new ArrayList<Reply>();
		final Date moment = new Date();
		final Comment result = new Comment();

		result.setReplies(replies);
		result.setMoment(moment);
		return result;

	}

	public Comment onlySave(final Comment comment) {
		Comment saved;
		saved = this.commentRepository.save(comment);
		return saved;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Comment res;
		final User logged = this.userService.findByPrincipal();
		Assert.notNull(logged);

		final Collection<Comment> comments = logged.getComments();

		if (comment.getId() == 0) {

			res = this.commentRepository.save(comment);
			comments.add(res);
			logged.setComments(comments);
			this.userService.save(logged);
		} else
			Assert.isTrue(logged.getComments().contains(comment));
		res = this.commentRepository.save(comment);

		return res;
	}

	public void delete(final Comment comment) {

		Assert.notNull(comment);
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		this.quitarCommentReply(comment);
		comment.setReplies(new ArrayList<Reply>());
		final Rendezvous rendezvous = this.rendezvousService.findByCommentId(comment.getId());
		final Collection<Comment> comments = rendezvous.getComments();
		comments.remove(comment);
		rendezvous.setComments(comments);
		this.rendezvousService.onlySave(rendezvous);
		final User commenter = this.userService.findByCommentId(comment.getId());
		final Collection<Comment> commentsUser = commenter.getComments();
		commentsUser.remove(comment);
		commenter.setComments(commentsUser);
		this.userService.onlySave(commenter);

		this.commentRepository.delete(comment);
	}

	public void quitarCommentReply(final Comment comment) {
		final Collection<Reply> replies = comment.getReplies();
		final Collection<Reply> repliesABorrar = new ArrayList<Reply>();
		for (final Reply r : replies) {
			final User user = this.replyService.findUserByReply(r);
			final Collection<Reply> userReplies = user.getReplies();
			userReplies.remove(r);
			user.setReplies(userReplies);
			this.userService.onlySave(user);
			repliesABorrar.add(r);
		}
		this.replyService.deleteAll(repliesABorrar);
		//this.rendezvousService.findByCommentId(comment.getId()).getComments().remove(comment);
	}
	public Comment findOne(final int commentID) {
		final Comment res = this.commentRepository.findOne(commentID);

		return res;
	}

	public Collection<Comment> findAll() {
		final Collection<Comment> res = this.commentRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Collection<Reply> findByCommentId(final Integer commentId) {
		final Collection<Reply> replies = new ArrayList<Reply>();
		final Comment comment = this.commentRepository.findOne(commentId);
		Assert.notNull(comment);
		replies.addAll(comment.getReplies());
		return replies;
	}

	//DASHBOARD
	public Double avgRepliesPerComment() {
		Double res;
		res = this.commentRepository.avgRepliesPerComment();
		return res;
	}
	public Double stdevRepliesPerComment() {
		Double res;
		res = this.commentRepository.stdevRepliesPerComment();
		return res;
	}

	public Comment reconstruct(final Comment comment, final BindingResult binding) {
		Comment res;
		if (comment.getId() == 0)
			res = comment;
		else {
			res = this.commentRepository.findOne(comment.getId());
			res.setText(comment.getText());
			res.setPicture(comment.getPicture());
			res.setMoment(comment.getMoment());
			//Se supone que hace falta
			//res.setReplies(comment.getReplies());

		}
		return res;
	}
	public void flush() {
		this.commentRepository.flush();
	}

}
