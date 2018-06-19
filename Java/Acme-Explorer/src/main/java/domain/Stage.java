
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Stage extends DomainEntity {

	private String	title;
	private String	description;
	private double	price;
	private boolean	used;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	@Min(0)
	public double getPrice() {
		return this.price;
	}
	public void setPrice(final double price) {
		this.price = price;
	}

	@NotNull
	public boolean getUsed() {
		return this.used;
	}
	public void setUsed(final boolean used) {
		this.used = used;
	}

	//RELATIONSHIPS ------------------------------

	//	private Trip	trip;
	//
	//
	//	@Valid
	//	@NotNull
	//	@ManyToOne(optional = false)
	//	public Trip getTrip() {
	//		return this.trip;
	//	}
	//
	//	public void setTrip(final Trip trip) {
	//		this.trip = trip;
	//	}

}
