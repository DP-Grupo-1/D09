
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Rendezvous;
import domain.Reply;
import domain.Request;
import domain.User;
import forms.Register;

@Service
@Transactional
public class UserService {

	//Managed repository----------------------------------------------
	@Autowired
	private UserRepository	userRepository;

	//Suporting services---------------------------------------------
	//	@Autowired
	//	private ReplyService	replyService;
	@Autowired
	private Validator		validator;


	//CRUD methods-------------------------------------------------------
	public User create() {
		final User res = new User();
		final Collection<Comment> comments = new ArrayList<>();
		final Collection<Rendezvous> attendances = new ArrayList<>();
		final Collection<Reply> replies = new ArrayList<>();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		final Collection<Request> requests = new ArrayList<Request>();
		authority.setAuthority(Authority.USER);

		Collection<Authority> authorities;

		authorities = userAccount.getAuthorities();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		res.setComments(comments);
		res.setAttendances(attendances);
		res.setReplies(replies);
		res.setUserAccount(userAccount);
		res.setRequests(requests);
		return res;
	}
	public User save(final User user) {
		Assert.notNull(user);
		final User res;

		if (user.getId() != 0) {
			final User logged = this.findByPrincipal();
			Assert.isTrue(logged.getId() == user.getId()); //Si se va a modificar, quien lo vaya a hacer tiene que tener el mismo id que su explorer
		}
		res = this.userRepository.save(user);
		return res;

	}

	public User onlySave(final User user) {
		User saved;

		saved = this.userRepository.save(user);
		return saved;
	}
	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		final User res;
		res = this.userRepository.findByUserAccountId(userAccount.getId());
		return res;
	}
	public User findByPrincipal() {
		final User res;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}
	public Collection<User> findAll() {
		Collection<User> res;
		res = this.userRepository.findAll();
		return res;
	}
	public User findOne(final int userId) {
		Assert.notNull(userId);
		User res;
		res = this.userRepository.findOne(userId);
		Assert.notNull(res);
		return res;

	}

	public User findByReplyId(final Integer replyId) {
		Assert.notNull(replyId);
		User res;
		res = this.userRepository.findByReplyId(replyId);
		return res;
	}
	public User findByRequestId(final Integer requestId) {
		Assert.notNull(requestId);
		final User res = this.userRepository.findByRequestId(requestId);
		return res;
	}
	public User findByCommentId(final Integer commentId) {
		Assert.notNull(commentId);
		final User res = this.userRepository.findByCommentId(commentId);
		return res;
	}

	public User reconstruct(final Register registerUser, final BindingResult binding) {
		User result;
		Assert.isTrue(registerUser.getAccept());
		result = this.create();
		result.getUserAccount().setUsername(registerUser.getUsername());
		result.getUserAccount().setPassword(registerUser.getPassword());

		result.setName(registerUser.getName());
		result.setSurname(registerUser.getSurname());
		result.setPostalAddress(registerUser.getPostalAddress());
		result.setPhoneNumber(registerUser.getPhoneNumber());
		result.setEmail(registerUser.getEmail());
		result.setAdult(registerUser.getAdult());

		return result;
	}
	public User reconstruct(final User user, final BindingResult binding) {
		User res;
		if (user.getId() == 0)
			res = user;
		else {
			res = this.userRepository.findOne(user.getId());
			res.setName(user.getName());
			res.setSurname(user.getSurname());
			res.setPostalAddress(user.getPostalAddress());
			res.setPhoneNumber(user.getPhoneNumber());
			res.setEmail(user.getEmail());
			this.validator.validate(res, binding);

		}
		return res;
	}

	public void flush() {
		this.userRepository.flush();
	}

}
