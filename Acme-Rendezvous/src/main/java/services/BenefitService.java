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
import domain.Benefit;
import domain.CreditCard;
import domain.Manager;
import domain.User;

@Service
@Transactional
public class BenefitService {

	@Autowired
	private BenefitRepository	benefitRepository;

	@Autowired
	private UserService				userService;
	
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
			Collection<Benefit> benefits = principal.getBenefits();
			benefits.add(benefit);
			principal.setBenefits(benefits);
			this.managerService.save(principal);
		}
		Assert.isTrue(principal.getBenefits().contains(benefit));
		Benefit result = this.benefitRepository.save(benefit);

		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));

		this.creditCardRepository.delete(creditCard);
	}

	// Auxiliary Methods

	public void flush() {
		this.creditCardRepository.flush();
	}
}
