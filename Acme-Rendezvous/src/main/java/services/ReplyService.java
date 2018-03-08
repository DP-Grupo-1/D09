
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReplyRepository;
import domain.Administrator;
import domain.Comment;
import domain.Reply;
import domain.User;

@Service
@Transactional
public class ReplyService {

	//Managed repository ----------------------------
	@Autowired
	public ReplyRepository			replyRepository;

	// --------SupportingServices----------------------------------
	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;


	//Simple CRUD methods ------------------------

	public Reply create(final Comment comment) {
		final Date moment = new Date();
		final Reply result = new Reply();
		result.setMoment(moment);
		return result;

	}

	public Reply save(final Reply reply) {
		Assert.notNull(reply);
		Reply res;

		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		final Collection<Reply> replies = user.getReplies();

		if (reply.getId() == 0) {
			res = this.replyRepository.save(reply);
			replies.add(res);
			user.setReplies(replies);
			this.userService.save(user);
		} else
			res = this.replyRepository.save(reply);

		return res;
	}

	public void delete(final Reply reply) {
		Assert.notNull(reply);
		
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		User user= userService.findByReplyId(reply.getId());
		Assert.notNull(user);
		
		user.getReplies().remove(reply);
		userService.save(user);
			

		this.replyRepository.delete(reply);
	}

	public Reply findOne(final int replyID) {
		final Reply res = this.replyRepository.findOne(replyID);

		return res;
	}

	public Collection<Reply> findAll() {
		final Collection<Reply> res = this.replyRepository.findAll();
		Assert.notNull(res);
		return res;
	}

}
