
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RendezvousRepository;
import domain.Administrator;
import domain.Announcement;
import domain.Benefit;
import domain.Category;
import domain.Comment;
import domain.Flag;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class RendezvousService {

	// Managed repository -----------------------------------------

	@Autowired
	private RendezvousRepository	rendezvousRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods ------------------------------------------

	public Rendezvous create() {

		final Rendezvous result = new Rendezvous();
		final User user = this.userService.findByPrincipal();
		final Collection<User> attendants = new ArrayList<User>();
		final Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
		final Collection<Comment> comments = new ArrayList<Comment>();
		final Collection<Announcement> announcements = new ArrayList<Announcement>();
		final Collection<Category> categories = new ArrayList<>();

		result.setAnnouncements(announcements);
		result.setAttendants(attendants);
		result.setRendezvouses(rendezvouses);
		result.setComments(comments);
		result.setFinalMode(false);
		result.setAdultOnly(false);
		result.setCreator(user);
		result.setFlag(Flag.ACTIVE);
		result.setCategories(categories);
		return result;
	}

	public Rendezvous save(final Rendezvous rendezvous) {

		Assert.notNull(rendezvous);
		Rendezvous result;

		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);

		Assert.isTrue(rendezvous.getFlag() != Flag.DELETED);
		//	Assert.isTrue(rendezvous.getFinalMode() == false);

		if (rendezvous.getId() == 0) {

			result = this.rendezvousRepository.save(rendezvous);

			//			result.setCreator(user);
			result.getAttendants().add(user);

			this.findByUserId(user.getId()).add(result);

		} else
			result = this.rendezvousRepository.save(rendezvous);
		return result;
	}

	public Rendezvous onlySave(final Rendezvous rendezvous) {
		Rendezvous saved;
		saved = this.rendezvousRepository.save(rendezvous);
		return saved;
	}

	public Rendezvous rsvp(final Rendezvous rendezvous) {
		final Collection<User> attendants = rendezvous.getAttendants();
		final User principal = this.userService.findByPrincipal();
		attendants.add(principal);
		rendezvous.setAttendants(attendants);
		Rendezvous saved;
		saved = this.rendezvousRepository.save(rendezvous);
		return saved;
	}

	public void onlyDelete(final Rendezvous rendezvous) {

		this.rendezvousRepository.delete(rendezvous);
	}

	public void deleteByUser(final Rendezvous rendezvous) {

		Assert.notNull(rendezvous);
		Assert.notNull(this.findOne(rendezvous.getId()));

		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);

		Assert.isTrue(rendezvous.getFinalMode() == false);
		Assert.isTrue(rendezvous.getFlag() != Flag.DELETED);
		rendezvous.setFlag(Flag.DELETED);
		this.onlySave(rendezvous);
	}

	public void deleteByAdmin(final Rendezvous rendezvous) {

		Assert.notNull(rendezvous);

		final Administrator admin = this.administratorService.findByPrincipal();

		Assert.notNull(admin);

		try {
			Assert.isTrue(rendezvous.getFlag() != Flag.DELETED);

			rendezvous.setFlag(Flag.DELETED);
			this.onlySave(rendezvous);

		} catch (final Exception oops) {
			System.out.println(oops.getMessage());
		}
	}

	public Collection<Rendezvous> findAll() {
		final Collection<Rendezvous> result = this.rendezvousRepository.findAll();

		for (final Rendezvous r : result)
			if (r.getMoment().before(new Date()) && r.getFlag() == Flag.ACTIVE) {
				r.setFlag(Flag.PASSED);
				this.onlySave(r);

			}
		return result;
	}

	public Rendezvous findOne(final int rendezvousId) {
		final Rendezvous res = this.findOneOnly(rendezvousId);
		if (res.getMoment().before(new Date()) && res.getFlag() == Flag.ACTIVE) {
			res.setFlag(Flag.PASSED);
			this.onlySave(res);
		}
		return res;
	}

	public Rendezvous findOneOnly(final int rendezvousId) {
		final Rendezvous res = this.rendezvousRepository.findOne(rendezvousId);
		return res;
	}

	// Other business methods ----------------------------------

	public Collection<Rendezvous> findByUserId(final int userId) {
		Collection<Rendezvous> result;
		result = this.rendezvousRepository.findByUserId(userId);

		for (final Rendezvous r : result)
			if (r.getMoment().before(new Date()) && r.getFlag() == Flag.ACTIVE) {
				r.setFlag(Flag.PASSED);
				this.onlySave(r);

			}
		return result;
	}
	public Collection<Rendezvous> findByCreatorIdAndRendezvouses(final int userId, final Benefit benefit) {
		final Collection<Rendezvous> res = this.rendezvousRepository.findByCreatorIdAndRendezvouses(userId, benefit.getRendezvouses());
		for (final Rendezvous r : res)
			if (r.getMoment().before(new Date()) && r.getFlag() == Flag.ACTIVE) {
				r.setFlag(Flag.PASSED);
				this.onlySave(r);

			}
		return res;
	}

	public Collection<Rendezvous> findByCreatorId(final int creatorId) {
		return this.rendezvousRepository.findByCreatorId(creatorId);
	}

	//--------------------------------------------- DASHBOARD ---------------------------------------------------------

	//1
	public Double avgRendezvousPerUser() {
		final Double result = this.rendezvousRepository.avgRendezvousPerUser();
		return result;
	}

	public Double ratioCreators() {
		final Double result = this.rendezvousRepository.ratioCreators();
		return result;
	}

	//3.1
	public Double avgUsersPerRendezvous() {
		final Double result = this.rendezvousRepository.avgUsersPerRendezvous();
		return result;
	}

	//3.2
	public Double stddevUsersPerRendezvous() {
		final Double result = this.rendezvousRepository.stddevUsersPerRendezvous();
		return result;
	}

	//4.1
	public Double avgRSVPsPerUser() {
		final Double result = this.rendezvousRepository.avgRSVPsPerUser();
		return result;
	}

	//4.2
	public Double stddevRSVPsPerUser() {
		final Double result = this.rendezvousRepository.stddevRSVPsPerUser();
		return result;
	}

	//5
	public Collection<Rendezvous> top10RendezvousesByRSVPs() {
		final Collection<Rendezvous> top10RendezvousesByRSVPs = this.rendezvousRepository.top10RendezvousesByRSVPs();

		final Collection<Rendezvous> finalTop10RendezvousesByRSVPs = new ArrayList<Rendezvous>();

		for (final Rendezvous r : top10RendezvousesByRSVPs) {
			finalTop10RendezvousesByRSVPs.add(r);
			if (finalTop10RendezvousesByRSVPs.size() >= 10)
				break;
		}

		return finalTop10RendezvousesByRSVPs;
	}

	public Collection<Rendezvous> above75AverageOfAnnouncementsPerRendezvous() {
		final Collection<Rendezvous> result;
		result = this.rendezvousRepository.above75AverageOfAnnouncementsPerRendezvous();
		return result;
	}

	public Collection<Rendezvous> LinkedGreaterAveragePlus10() {
		final Collection<Rendezvous> result;
		result = this.rendezvousRepository.linkedGreaterAveragePlus10();
		return result;
	}

	//COMMENT

	public Collection<Comment> findByRendezvous(final Integer rendezvousId) {
		final Collection<Comment> comments = new ArrayList<Comment>();
		final Rendezvous rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		Assert.notNull(rendezvous);
		comments.addAll(rendezvous.getComments());
		return comments;
	}

	public Rendezvous findByCommentId(final Integer commentId) {
		Assert.notNull(commentId);
		final Rendezvous res = this.rendezvousRepository.findByCommentId(commentId);
		return res;
	}

	public Rendezvous findByAnnouncementId(final Integer announcementId) {
		Assert.notNull(announcementId);
		final Rendezvous res = this.rendezvousRepository.findByAnnouncementId(announcementId);
		return res;
	}

	//Requisito 6.3 punto 2
	public Double ratioUsersSinRendezvous() {
		final Double ratio = 1 - this.ratioCreators();
		return ratio;
	}
	//Requisito 6.3 punto 1: la desviación estándar de reuniones creadas por usuario.
	public Double stddevRendezvousPerUser() {
		Double stddev = 0.0;

		stddev = Math.sqrt(this.sumRendezvouses() / this.numRendezvouses() - this.avgRendezvousPerUser() * this.avgRendezvousPerUser());

		return stddev;
	}

	private Integer numRendezvouses() {
		Integer numRendezvouses = 0;
		for (final User u1 : this.userService.findAll()) {
			final Collection<Rendezvous> rendezvouses = this.findByUserId(u1.getId());
			numRendezvouses = numRendezvouses + rendezvouses.size();
		}
		return numRendezvouses;
	}

	private Integer sumRendezvouses() {
		Integer sumRendezvouses = 0;
		for (final User u2 : this.userService.findAll()) {
			final Collection<Rendezvous> rendezvouses = this.findByUserId(u2.getId());
			sumRendezvouses = sumRendezvouses + rendezvouses.size() * rendezvouses.size();
		}
		return sumRendezvouses;
	}

	public Rendezvous reconstruct(final Rendezvous rendezvous, final BindingResult binding) {
		Rendezvous res;
		if (rendezvous.getId() == 0)
			res = rendezvous;
		else {
			res = this.rendezvousRepository.findOne(rendezvous.getId());
			res.setName(rendezvous.getName());
			res.setDescription(rendezvous.getDescription());
			res.setMoment(rendezvous.getMoment());
			res.setPicture(rendezvous.getPicture());
			res.setLocationLatitude(rendezvous.getLocationLatitude());
			res.setLocationLongitude(rendezvous.getLocationLongitude());
			res.setFinalMode(rendezvous.getFinalMode());
			res.setAdultOnly(rendezvous.getAdultOnly());
			//             cosas necesarias
			//            res.setCreator(rendezvous.getCreator());
			//            res.setFlag(rendezvous.getFlag());
			//            res.setRendezvouses(rendezvous.getRendezvouses());
			//            res.setComments(rendezvous.getComments());
			//            res.setAttendants(rendezvous.getAttendants());
			//            res.setAnnouncements(rendezvous.getAnnouncements());
			this.validator.validate(res, binding);
		}

		return res;
	}

	public Collection<Rendezvous> findByCategory(final int categoryId) {

		return this.rendezvousRepository.findByCategoryId(categoryId);
	}

}
