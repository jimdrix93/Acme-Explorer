
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	private String	title;
	private String	body;
	private int		numberApLaws;
	private Date	moment;
	private boolean	finalMode;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	public int getNumberApLaws() {
		return this.numberApLaws;
	}
	public void setNumberApLaws(final int numberApLaws) {
		this.numberApLaws = numberApLaws;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}
	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	/* Relaciones */
	//	private Collection<Trip>	trips;
	//
	//
	//	@OneToMany
	//	//(mappedBy = "legalText")
	//	@Valid
	//	@NotNull
	//	public Collection<Trip> getTrips() {
	//		return this.trips;
	//	}
	//	public void setTrips(final Collection<Trip> trips) {
	//		this.trips = trips;
	//	}

}
