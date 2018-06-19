
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	private boolean	suspicious;


	public boolean getSuspicious() {
		return this.suspicious;
	}

	public void setSuspicious(final boolean suspicious) {
		this.suspicious = suspicious;
	}


	//Relationships ------------------------------------------------------------------------------------------------------------------

	private Collection<Trip>	trips;


	@OneToMany(mappedBy = "manager")
	@Valid
	@NotNull
	public Collection<Trip> getTrips() {
		return this.trips;
	}
	public void setTrips(final Collection<Trip> trip) {
		this.trips = trip;
	}

}
