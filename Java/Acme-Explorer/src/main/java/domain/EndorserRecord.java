
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
public class EndorserRecord extends DomainEntity {

	private String fullName;
	private String email;
	private String phone;
	private String linkedInUrl;
	private String comments;

	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@Email
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
	public String getLinkedInUrl() {
		return this.linkedInUrl;
	}

	public void setLinkedInUrl(final String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
