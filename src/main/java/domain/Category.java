
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String	name;


	@NotBlank
	//@Column(unique = true)
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}


	/* Relacion */

	private Collection<Trip>		trips;
	private Category				parent;
	private Collection<Category>	childs;


	@OneToMany(mappedBy = "category")
	@Valid
	@NotNull
	public Collection<Trip> getTrips() {
		return this.trips;
	}
	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}

	@ManyToOne(optional = true)
	@Valid
	public Category getParent() {
		return this.parent;
	}
	public void setParent(final Category parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent")
	@Valid
	@NotNull
	public Collection<Category> getChilds() {
		return this.childs;
	}
	public void setChilds(final Collection<Category> childs) {
		this.childs = childs;
	}

}
