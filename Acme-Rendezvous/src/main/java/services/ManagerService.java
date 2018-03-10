package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	//Managed repository------------------------------------------------------
	@Autowired
	private ManagerRepository	managerRepository;


	//CRUD methods--------------------------------------------------------
	//	public Manager create() {
	//		Manager res;
	//		res = new Manager();
	//		return res;
	//	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		final Manager res;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		if (manager.getId() == 0)
			Assert.isTrue(userAccount.getAuthorities().contains(Authority.MANAGER));//Admins son los que pueden crear admins
		else
			Assert.isTrue(userAccount.getId() == manager.getId());//Si se va a modificar, quien lo vaya a hacer tiene que tener el mismo id que su manager
		res = this.managerRepository.save(manager);
		return res;
	}
	public Collection<Manager> findAll() {
		Collection<Manager> res;
		res = this.managerRepository.findAll();
		return res;

	}
	public Manager findOne(final int managerId) {
		Manager res;
		res = this.managerRepository.findOne(managerId);
		return res;
	}

	public Manager findByPrincipal() {
		Manager res;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager res;
		res = this.managerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

}