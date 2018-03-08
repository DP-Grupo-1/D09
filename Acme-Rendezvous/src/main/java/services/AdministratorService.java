
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository------------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;


	//CRUD methods--------------------------------------------------------
	//	public Administrator create() {
	//		Administrator res;
	//		res = new Administrator();
	//		return res;
	//	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		final Administrator res;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		if (administrator.getId() == 0)
			Assert.isTrue(userAccount.getAuthorities().contains(Authority.ADMIN));//Admins son los que pueden crear admins
		else
			Assert.isTrue(userAccount.getId() == administrator.getId());//Si se va a modificar, quien lo vaya a hacer tiene que tener el mismo id que su administrator
		res = this.administratorRepository.save(administrator);
		return res;
	}
	public Collection<Administrator> findAll() {
		Collection<Administrator> res;
		res = this.administratorRepository.findAll();
		return res;

	}
	public Administrator findOne(final int administratorId) {
		Administrator res;
		res = this.administratorRepository.findOne(administratorId);
		return res;
	}

	public Administrator findByPrincipal() {
		Administrator res;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator res;
		res = this.administratorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

}
