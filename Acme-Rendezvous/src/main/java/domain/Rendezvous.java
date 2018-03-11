
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
//@Table(indexes = {
//	@Index(columnList = "creator_id")
//})
public class Rendezvous extends DomainEntity {

	//-------------------------------------Atributos--------------------------------------------

	private String						name, description;
	private Date						moment;
	private String						picture;
	private Double						locationLatitude, locationLongitude;
	private boolean						finalMode, adultOnly;
	private Flag						flag;

	//-------------------------------------Relaciones-------------------------------------------

	private Collection<Rendezvous>		rendezvouses;
	private Collection<Comment>			comments;
	private User						creator;
	private Collection<User>			attendants;
	private Collection<Announcement>	announcements;
	private Collection<Category> categories;


	//-----------------------------Getters y Setters Atributos----------------------------------
	@SafeHtml
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@SafeHtml
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@SafeHtml
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Double getLocationLatitude() {
		return this.locationLatitude;
	}

	public void setLocationLatitude(final Double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public Double getLocationLongitude() {
		return this.locationLongitude;
	}

	public void setLocationLongitude(final Double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public boolean getAdultOnly() {
		return this.adultOnly;
	}

	public void setAdultOnly(final boolean adultOnly) {
		this.adultOnly = adultOnly;
	}

	@NotNull
	public Flag getFlag() {
		return this.flag;
	}

	public void setFlag(final Flag flag) {
		this.flag = flag;
	}

	//-----------------------------Getters y Setters Relaciones---------------------------------

	@ManyToMany
	@NotNull
	@Valid
	public Collection<Rendezvous> getRendezvouses() {
		return this.rendezvouses;
	}

	public void setRendezvouses(final Collection<Rendezvous> rendezvouses) {
		this.rendezvouses = rendezvouses;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@NotNull
	@Valid
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(final User creator) {
		this.creator = creator;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<User> getAttendants() {
		return this.attendants;
	}

	public void setAttendants(final Collection<User> attendants) {
		this.attendants = attendants;
	}

	@Valid
	@OneToMany
	@NotNull
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	
	@NotNull
	@Valid
	@ManyToMany
	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
}
