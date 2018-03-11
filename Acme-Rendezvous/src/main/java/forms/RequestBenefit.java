package forms;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Benefit;
import domain.Rendezvous;

public class RequestBenefit {

	private String	holderName;
	private String	brandName;
	private String	number;
	private int		expirationMonth;
	private int		expirationYear;
	private int		cvv;
	private String	comment;
	private Rendezvous rendezvous;
	private Benefit benefit;
	
	@NotNull
	public Benefit getBenefit() {
		return benefit;
	}
	public void setBenefit(Benefit benefit) {
		this.benefit = benefit;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@NotNull
	public Rendezvous getRendezvous() {
		return rendezvous;
	}
	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHolderName() {
		return this.holderName;
	}
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 12, max = 16)
	@Digits(fraction = 0, integer = 16)
	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}
	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	public int getExpirationMonth() {
		return this.expirationMonth;
	}
	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public int getExpirationYear() {
		return this.expirationYear;
	}
	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}

	@Range(min = 100, max = 999)
	public int getCvv() {
		return this.cvv;
	}
	public void setCvv(final int cvv) {
		this.cvv = cvv;
	}
}
