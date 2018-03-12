
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
//@Table(uniqueConstraints = {
//	@UniqueConstraint(columnNames = {
//		"userAccount_id"
//	})
//})
public class Manager extends Actor {

	private String				VAT;

	private Collection<Benefit>	benefits;


	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Benefit> getBenefits() {
		return this.benefits;
	}

	public void setBenefits(final Collection<Benefit> benefits) {
		this.benefits = benefits;
	}

	@NotBlank
	public String getVAT() {
		return this.VAT;
	}

	public void setVAT(final String VAT) {
		this.VAT = VAT;
	}
}
