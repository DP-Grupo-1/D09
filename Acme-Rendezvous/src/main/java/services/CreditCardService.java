
package services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.User;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private UserService				userService;


	public CreditCardService() {
		super();
	}

	public CreditCard create() {
		CreditCard result;
		result = new CreditCard();

		return result;
	}
	public CreditCard findOne(final int creditCardId) {
		Assert.isTrue(creditCardId != 0);

		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
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

	public String trimCreditNumber(final CreditCard creditCard) {
		String result;
		String last4;

		last4 = creditCard.getNumber().substring(12);
		result = "************";
		result += last4;

		return result;
	}

	//Luhn's Algorithm
	public boolean checkCCNumber(final String ccNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9)
					n = (n % 10) + 1;
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}

	public boolean expirationDate(final CreditCard creditCard) {
		boolean res = false;
		final Calendar moment = new GregorianCalendar();
		if ((2000 + creditCard.getExpirationYear()) == moment.get(Calendar.YEAR)) {
			if (creditCard.getExpirationMonth() > moment.get(Calendar.MONTH))
				res = true;
			else if (creditCard.getExpirationMonth() == moment.get(Calendar.MONTH))
				if (moment.get(Calendar.DAY_OF_MONTH) < 21)
					res = true;
		} else if ((2000 + creditCard.getExpirationYear()) > moment.get(Calendar.YEAR))
			res = true;
		return res;
	}

	public CreditCard findByPrincipal() {
		CreditCard result;
		User user;
		user = this.userService.findByPrincipal();
		result = this.creditCardRepository.findByUserId(user.getId());
		return result;
	}

	public void flush() {
		this.creditCardRepository.flush();
	}

}
