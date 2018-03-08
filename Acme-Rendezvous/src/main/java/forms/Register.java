
package forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class Register {

	private String	username;
	private String	password;
	private String	name;
	private String	surname;
	private Integer	postalAddress;
	private String	phoneNumber;
	private String	email;
	private boolean	accept;
	private boolean	adult;


	@NotBlank
	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
	@Size(min = 5, max = 32)
	@NotBlank
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public Integer getPostalAddress() {
		return this.postalAddress;
	}

	public void setPostalAddress(final Integer postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public boolean getAccept() {
		return this.accept;
	}

	public void setAccept(final boolean accept) {
		this.accept = accept;
	}

	public boolean getAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

}
