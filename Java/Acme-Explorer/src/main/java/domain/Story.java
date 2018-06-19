
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Story extends DomainEntity {

	private String				title;
	private String				text;
	private Collection<String>	attachments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}


	/* Relaciones */

	private Trip		trip;
	private Explorer	writer;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Trip getTrip() {
		return this.trip;
	}
	public void setTrip(final Trip t) {
		this.trip = t;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Explorer getWriter() {
		return this.writer;
	}

	public void setWriter(final Explorer writer) {
		this.writer = writer;
	}
}
