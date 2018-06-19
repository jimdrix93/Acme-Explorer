
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	search;
	private Date	minDate;
	private Double	minPrice;
	private Date	maxDate;
	private Double	maxPrice;
	private Date	moment;


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
	public String getSearch() {
		return this.search;
	}
	public void setSearch(final String search) {
		this.search = search;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMinDate() {
		return this.minDate;
	}
	public void setMinDate(final Date minDate) {
		this.minDate = minDate;
	}

	@Min(0)
	public Double getMinPrice() {
		return this.minPrice;
	}
	public void setMinPrice(final Double minPrice) {
		this.minPrice = minPrice;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMaxDate() {
		return this.maxDate;
	}
	public void setMaxDate(final Date maxDate) {
		this.maxDate = maxDate;
	}

	@Min(0)
	public Double getMaxPrice() {
		return this.maxPrice;
	}
	public void setMaxPrice(final Double maxPrice) {
		this.maxPrice = maxPrice;
	}


	/* Relaciones */
	private Collection<Trip>	trips;


	@ManyToMany
	@Valid
	@NotNull
	public Collection<Trip> getTrips() {
		return this.trips;
	}
	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}

}
