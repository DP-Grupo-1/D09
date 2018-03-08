
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
//@Table(indexes = {
//	@Index(columnList = "moment")
//})
public class Comment extends DomainEntity {

	private Date				moment;
	private String				text;
	private String				picture;

	//RELATIONSHIPS

	private Collection<Reply>	replies;


	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@SafeHtml
	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}
	@SafeHtml
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Reply> getReplies() {
		return this.replies;
	}
	public void setReplies(final Collection<Reply> replies) {
		this.replies = replies;
	}

}
