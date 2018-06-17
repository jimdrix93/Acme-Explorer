
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalRecord extends DomainEntity {

	private String	fullName;
	private String	photo;
	private String	email;
	private String	phone;
	private String	linkedInUrl;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@URL
	@NotBlank
	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "(^\\+\\d{1,3})?\\s?(\\(\\d{1,3}\\))?\\s?(\\d{4,})")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@URL
	@NotBlank
	public String getLinkedInUrl() {
		return this.linkedInUrl;
	}
	public void setLinkedInUrl(final String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

}
