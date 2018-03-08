
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Reply extends DomainEntity {

	private Date	moment;
	private String	text;


	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	@SafeHtml
	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
