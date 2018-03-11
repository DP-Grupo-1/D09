package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	
	private String name;
	private String description;
	
	
	//Relationships
	
	private Category parent;
	private Collection<Category> childrens;
	private Collection<Benefit> benefits;
	
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Valid
	@ManyToOne(optional = true)
	@NotNull
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "parent")
	public Collection<Category> getChildrens() {
		return childrens;
	}
	public void setChildrens(Collection<Category> childrens) {
		this.childrens = childrens;
	}
	
	@Valid
	@ManyToMany
	@NotNull
	public Collection<Benefit> getBenefits() {
		return benefits;
	}
	public void setBenefits(Collection<Benefit> benefits) {
		this.benefits = benefits;
	}
}





