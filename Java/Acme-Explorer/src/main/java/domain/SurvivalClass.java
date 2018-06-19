
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SurvivalClass extends DomainEntity {

	private String					title;
	private String					description;
	private Date					moment;
	private Location				location;
	private Collection<Explorer>	explorers;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String t) {
		this.title = t;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String d) {
		this.description = d;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date m) {
		this.moment = m;
	}

	@NotNull
	@Valid
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location l) {
		this.location = l;
	}
	
	@Valid
	@ManyToMany
	public Collection<Explorer> getExplorers() {
		return this.explorers;
	}
	public void setExplorers(final Collection<Explorer> explorers) {
		this.explorers = explorers;
	}


	/* Relaciones */

	private Trip	trip;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip t) {
		this.trip = t;
	}

}
