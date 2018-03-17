
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
//@Table(uniqueConstraints = {
//	@UniqueConstraint(columnNames = {
//		"userAccount_id"
//	})
//})
public class User extends Actor {

	//Relationships-----------------------------------------------------------

	private Collection<Reply>		replies;
	private Collection<Comment>		comments;

	private Collection<Rendezvous>	attendances;


	@OneToMany
	@NotNull
	public Collection<Reply> getReplies() {
		return this.replies;
	}

	public void setReplies(final Collection<Reply> replies) {
		this.replies = replies;
	}

	@OneToMany
	@Valid
	@NotNull
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@Valid
	@ManyToMany(mappedBy = "attendants")
	@NotNull
	public Collection<Rendezvous> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(final Collection<Rendezvous> attendances) {
		this.attendances = attendances;
	}


	//Relationships---------------------------------------------------------------
	private CreditCard	creditCard;
	private Collection<Request> requests;

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
