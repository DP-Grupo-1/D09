
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
//@Table(indexes = {
//	@Index(columnList = "creator_id,rendezvous_id")
//})
public class Question extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	questionToAnswer;

	@SafeHtml
	@NotBlank
	public String getQuestionToAnswer() {
		return this.questionToAnswer;
	}
	public void setQuestionToAnswer(final String questionToAnswer) {
		this.questionToAnswer = questionToAnswer;
	}


	// Relationships ----------------------------------------------------------

	private User				creator;
	private Rendezvous			rendezvous;
	private Collection<Answer>	answers;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getCreator() {
		return this.creator;
	}
	public void setCreator(final User creator) {
		this.creator = creator;
	}

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Rendezvous getRendezvous() {
		return this.rendezvous;
	}
	public void setRendezvous(final Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	@NotNull
	public Collection<Answer> getAnswers() {
		return this.answers;
	}
	public void setAnswers(final Collection<Answer> answers) {
		this.answers = answers;
	}

}
