
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HasValue extends DomainEntity {

	private String	value;

	@NotBlank
	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}


	//RELATIONSHIPS --------------------------------------------------

	private Trip	trip;
	private Tag		tag;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Tag getTag() {
		return this.tag;
	}

	public void setTag(final Tag tag) {
		this.tag = tag;
	}

}
