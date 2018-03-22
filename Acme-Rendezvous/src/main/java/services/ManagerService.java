
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Benefit;
import domain.Manager;
import forms.RegisterManager;

@Service
@Transactional
public class ManagerService {

	//Managed repository------------------------------------------------------
	@Autowired
	private ManagerRepository	managerRepository;
	@Autowired
	private Validator			validator;


	//CRUD methods--------------------------------------------------------
	public Manager create() {
		final Manager res = new Manager();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MANAGER);

		Collection<Authority> authorities;

		authorities = userAccount.getAuthorities();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		res.setUserAccount(userAccount);

		final Collection<Benefit> benefits = new ArrayList<Benefit>();
		res.setBenefits(benefits);

		return res;
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);

		return this.managerRepository.save(manager);

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

	public Manager reconstruct(final RegisterManager registerManager, final BindingResult binding) {
		final Manager result = this.create();

		Assert.isTrue(registerManager.getAccept());

		result.getUserAccount().setUsername(registerManager.getUsername());
		result.getUserAccount().setPassword(registerManager.getPassword());

		result.setName(registerManager.getName());
		result.setSurname(registerManager.getSurname());
		result.setPostalAddress(registerManager.getPostalAddress());
		result.setPhoneNumber(registerManager.getPhoneNumber());
		result.setEmail(registerManager.getEmail());
		result.setVAT(registerManager.getVAT());
		result.setAdult(registerManager.getAdult());

		return result;
	}
	public Manager reconstruct(final Manager manager, final BindingResult binding) {
		Manager res;
		if (manager.getId() == 0)
			res = manager;
		else {
			res = this.managerRepository.findOne(manager.getId());
			res.setName(manager.getName());
			res.setSurname(manager.getSurname());
			res.setPostalAddress(manager.getPostalAddress());
			res.setPhoneNumber(manager.getPhoneNumber());
			res.setEmail(manager.getEmail());
			res.setVAT(manager.getVAT());
			//this.validator.validate(res, binding);

		}
		return res;
	}
	public Collection<Manager> managersWithMoreBenefits() {
		return this.managerRepository.managersWithMoreBenefits();

	}
	public Collection<Manager> managersWithMoreBenefitsCancelled() {
		return this.managerRepository.managersWithMoreBenefits();
	}

	public void flush() {
		this.managerRepository.flush();

	}

}
