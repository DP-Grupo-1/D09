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

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}
	
	private Benefit	service;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Benefit getService() {
		return this.service;
	}

	public void setService(final Benefit service) {
		this.service = service;
	}

}
