package services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import repositories.RequestRepository;
import domain.CreditCard;
import domain.Request;
import domain.User;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;

	@Autowired
	private UserService				userService;


	public RequestService() {
		super();
	}

	public Request create() {
		CreditCard result;
		result = new CreditCard();

		return result;
	}
	public Request findOne(final int creditCardId) {
		Assert.isTrue(creditCardId != 0);

		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public Request save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreditCard result;
		Assert.isTrue(this.checkCCNumber(creditCard.getNumber()));
		Assert.isTrue(this.expirationDate(creditCard));
		final User principal = this.userService.findByPrincipal();
		principal.setCreditCard(creditCard);
		this.userService.save(principal);
		result = this.creditCardRepository.save(creditCard);

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