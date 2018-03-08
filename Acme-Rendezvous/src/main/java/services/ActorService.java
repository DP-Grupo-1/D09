
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.User;

@Service
@Transactional
public class ActorService {

	//Managed Repository-----------------------------------------------
	@Autowired
	ActorRepository			actorRepository;
	//Suporting services-----------------------------------------------
	@Autowired
	AdministratorService	administratorService;

	@Autowired
	UserService				userService;


	//CRUD methods
	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor res = actor;
		final UserAccount userAccount = res.getUserAccount();
		final List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();

		switch (authorities.get(0).getAuthority()) {
		//		case Authority.ADMIN:
		//			res = this.administratorService.save((Administrator) actor);
		//			break;
		case Authority.USER:
			res = this.userService.save((User) actor);
			break;

		}
		return res;
	}
	public Collection<Actor> findAll() {
		Collection<Actor> res;
		res = this.actorRepository.findAll();
		return res;
	}
	public Actor findOne(final int actorId) {
		Assert.notNull(actorId);
		Actor res;
		res = this.actorRepository.findOne(actorId);
		Assert.notNull(res);
		return res;
	}
	public Actor findByPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final List<Authority> authorities = (List<Authority>) userAccount.getAuthorities();
		switch (authorities.get(0).getAuthority()) {

		case Authority.ADMIN:
			return this.administratorService.findByPrincipal();

		case Authority.USER:
			return this.userService.findByPrincipal();
		}
		return null;

	}
}
