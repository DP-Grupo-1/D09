
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Attributes
	private String	comment;
	private Benefit	benefit;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Benefit getBenefit() {
		return this.benefit;
	}

	public void setBenefit(final Benefit benefit) {
		this.benefit = benefit;
	}

}
