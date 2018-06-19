
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MyMessage extends DomainEntity {

	private Boolean		copy;
	private Date		moment;
	private String		subject;
	private String		body;
	private Priority	priority;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	@Valid
	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(final Priority priority) {
		this.priority = priority;
	}
	@NotNull
	public Boolean getCopy() {
		return this.copy;
	}

	public void setCopy(final Boolean copy) {
		this.copy = copy;
	}


	//Relationships------------------------------------------------------------------------------------------------------------

	//private Folder	folder;
	private Actor	recipient;
	private Actor	sender;


	//	@ManyToOne(optional = false)
	//	@Valid
	//	@NotNull
	//	public Folder getFolder() {
	//		return this.folder;
	//	}
	//
	//	public void setFolder(final Folder folder) {
	//		this.folder = folder;
	//	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Actor getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

}
