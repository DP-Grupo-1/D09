
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)

	

public class Answer extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	written;

	@SafeHtml
	@NotBlank
	public String getWritten() {
		return this.written;
	}
	public void setWritten(final String written) {
		this.written = written;
	}


	// Relationships ----------------------------------------------------------

	private User	answerer;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getAnswerer() {
		return this.answerer;
	}
	public void setAnswerer(final User answerer) {
		this.answerer = answerer;
	}

}
