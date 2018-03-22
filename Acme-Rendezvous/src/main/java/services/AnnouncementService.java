
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Announcement;
import domain.Rendezvous;

@Service
@Transactional
public class AnnouncementService {

	//Managed Repository-----------------------------------------------
	@Autowired
	AnnouncementRepository	announcementRepository;

	//Suporting services-----------------------------------------------
	@Autowired
	UserService				userService;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	RendezvousService		rendezvousService;


	//CRUD methods
	public Announcement create(final int rendezvousId) {

		final Rendezvous r = this.rendezvousService.findOne(rendezvousId);
		Assert.isTrue(this.userService.findByPrincipal().equals(r.getCreator()));

		final Date moment = new Date();
		final Announcement result = new Announcement();
		result.setMoment(moment);
		return result;

	}

	public Announcement save(final Announcement announcement) {
		final UserAccount userAcc = LoginService.getPrincipal();
		Assert.notNull(userAcc);
		Assert.notNull(this.userService.findByUserAccount(userAcc));
		Assert.notNull(announcement);

		final Announcement res = this.announcementRepository.save(announcement);

		return res;
	}

	public void delete(final Announcement announcement) {

		Assert.notNull(announcement);

		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		this.announcementRepository.delete(announcement);
	}

	public Collection<Announcement> findAll() {
		Collection<Announcement> res;
		res = this.announcementRepository.findAll();
		return res;
	}

	public Announcement findOne(final int announcementId) {
		Assert.notNull(announcementId);
		Announcement res;
		res = this.announcementRepository.findOne(announcementId);
		Assert.notNull(res);
		return res;
	}

	//3.1
	public Double avgOfAnnouncementsPerRendezvous() {
		return this.announcementRepository.avgOfAnnouncementsPerRendezvous();
	}

	//3.2
	public Double stddAnnouncementsPerRendezvous() {
		return this.announcementRepository.stddAnnouncementsPerRendezvous();
	}

	public void flush() {
		this.announcementRepository.flush();
	}

	//Prune domain object--------------------------------------------------------
	//	public Announcement reconstruct(Announcement announcement,BindingResult binding){
	//		Announcement res;
	//		if(announcement.getId()==0){
	//			res=announcement;
	//		}else{
	//			res=announcementRepository.findOne(announcement.getId());
	//			res.setTitle(announcement.getTitle());
	//			res.setDescription(announcement)
	//		}
	//		
	//		return res;
	//	}
}
