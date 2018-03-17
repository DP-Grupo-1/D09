
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BenefitRepository;
import controllers.AbstractController;
import domain.Administrator;
import domain.Benefit;
import domain.Manager;
import domain.Rendezvous;
import domain.Request;

@Service
@Transactional
public class BenefitService extends AbstractController {

	@Autowired
	private BenefitRepository		benefitRepository;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private ManagerService			managerService;
	@Autowired
	private Validator				validator;


	public BenefitService() {
		super();
	}

	public Benefit create() {
		Benefit result;
		result = new Benefit();
		final Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
		result.setRendezvouses(rendezvouses);

		return result;
	}
	public Benefit findOne(final int benefitId) {
		Assert.isTrue(benefitId != 0);

		Benefit result;

		result = this.benefitRepository.findOne(benefitId);
		Assert.notNull(result);

		return result;
	}

	public Benefit save(final Benefit benefit) {
		Assert.notNull(benefit);
		final Manager principal = this.managerService.findByPrincipal();

		if (benefit.getId() == 0) {
			benefit.setFlag("ACTIVE");
			final Collection<Benefit> benefits = principal.getBenefits();
			benefits.add(benefit);
			principal.setBenefits(benefits);
			this.managerService.save(principal);
		}
		Assert.isTrue(principal.getBenefits().contains(benefit));
		final Benefit result = this.benefitRepository.save(benefit);

		return result;
	}

	public Benefit saveByAdmin(final Benefit benefit) {
		Assert.notNull(benefit);
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		final Collection<Request> requests = this.requestService.findAllByBenefit(benefit.getId());
		Assert.isTrue(requests.isEmpty());

		final Benefit result = this.benefitRepository.save(benefit);
		return result;
	}

	public void delete(final Benefit benefit) {
		final Manager principal = this.managerService.findByPrincipal();
		final Collection<Request> requests = this.requestService.findAllByBenefit(benefit.getId());
		Assert.isTrue(requests.isEmpty());
		Assert.isTrue(principal.getBenefits().contains(benefit));
		Assert.isTrue(principal.getBenefits().contains(benefit));
		Assert.notNull(benefit);
		Assert.isTrue(benefit.getId() != 0);
		Assert.isTrue(this.benefitRepository.exists(benefit.getId()));

		this.benefitRepository.delete(benefit);
	}

	// Auxiliary Methods

	public void flush() {
		this.benefitRepository.flush();
	}

	public void cancelBenefit(final Benefit benefit) {
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(benefit);
		Assert.isTrue(benefit.getFlag().equals("ACTIVE"));
		benefit.setFlag("CANCELLED");
		this.saveByAdmin(benefit);

	}

	public Collection<Benefit> findAll() {
		// TODO Auto-generated method stub
		return this.benefitRepository.findAll();
	}

	public Collection<Benefit> findAllRequestedByRendezvous(final Rendezvous rendezvous) {
		// TODO Auto-generated method stub
		return this.benefitRepository.findAllRequestedByRendezvous(rendezvous);
	}
	public Collection<Benefit> bestSellings() {
		return this.benefitRepository.bestSellings();
	}
	public Benefit reconstruct(final Benefit benefit, final BindingResult binding) {
		Benefit res;
		if (benefit.getId() == 0)
			res = benefit;
		else {
			res = this.benefitRepository.findOne(benefit.getId());
			res.setName(benefit.getName());
			res.setDescription(benefit.getDescription());
			res.setPicture(benefit.getPicture());
			res.setFlag(benefit.getFlag());
		}
		return res;
	}
}
