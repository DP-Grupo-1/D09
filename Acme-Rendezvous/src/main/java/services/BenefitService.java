package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BenefitRepository;
import repositories.CreditCardRepository;
import domain.Administrator;
import domain.Benefit;
import domain.CreditCard;
import domain.Manager;
import domain.Request;
import domain.User;

@Service
@Transactional
public class BenefitService {

	@Autowired
	private BenefitRepository	benefitRepository;

	@Autowired
	private UserService				userService;
	
	@Autowired
	private AdministratorService				administratorService;
	
	@Autowired
	private RequestService				requestService;
	
	@Autowired
	private ManagerService				managerService;


	public BenefitService() {
		super();
	}

	public Benefit create() {
		Benefit result;
		result = new Benefit();
		
		return result;
	}
	public Benefit findOne(final int benefitId) {
		Assert.isTrue(benefitId != 0);
		
		Benefit result;

		result = this.benefitRepository.findOne(benefitId);
		Manager principal = this.managerService.findByPrincipal();
		Assert.isTrue(principal.getBenefits().contains(result));
		Assert.notNull(result);

		return result;
	}

	public Benefit save(final Benefit benefit) {
		Assert.notNull(benefit);
		Manager principal = this.managerService.findByPrincipal();
		
		if(benefit.getId()==0){
			benefit.setFlag("ACTIVE");
			Collection<Benefit> benefits = principal.getBenefits();
			benefits.add(benefit);
			principal.setBenefits(benefits);
			this.managerService.save(principal);
		}
		Assert.isTrue(principal.getBenefits().contains(benefit));
		Benefit result = this.benefitRepository.save(benefit);

		return result;
	}
	
	public Benefit saveByAdmin(final Benefit benefit) {
		Assert.notNull(benefit);
		Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Collection<Request> requests = this.requestService.findAllByBenefit(benefit.getId());
		Assert.isTrue(requests.isEmpty());
 
		Benefit result = this.benefitRepository.save(benefit);
		return result;
	}

	public void delete(final Benefit benefit) {
		Manager principal = this.managerService.findByPrincipal();
		Collection<Request> requests = this.requestService.findAllByBenefit(benefit.getId());
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
	
	public void cancelBenefit(Benefit benefit){
		Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(benefit);
		Assert.isTrue(benefit.getFlag().equals("ACTIVE"));
		benefit.setFlag("CANCELLED");
		this.saveByAdmin(benefit);
		
	}
}
